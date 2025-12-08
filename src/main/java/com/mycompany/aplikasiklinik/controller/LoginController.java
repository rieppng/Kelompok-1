package com.mycompany.aplikasiklinik.controller;

import com.mycompany.aplikasiklinik.model.dao.AppointmentDAO;
import com.mycompany.aplikasiklinik.model.dao.UserDAO;
import com.mycompany.aplikasiklinik.model.entity.User;
import com.mycompany.aplikasiklinik.model.entity.UserRole;
import com.mycompany.aplikasiklinik.util.SessionManager;
import com.mycompany.aplikasiklinik.view.LoginView;
import com.mycompany.aplikasiklinik.view.MainDashboard;
import javax.swing.JOptionPane;

/**
 * Controller untuk menangani proses autentikasi pengguna (Login & Logout).
 * Mengatur validasi input, manajemen sesi, dan pengarahan ke dashboard berdasarkan Role.
 */
public class LoginController {

    private final LoginView view;
    private final UserDAO userDAO;

    public LoginController(LoginView view) {
        this.view = view;
        this.userDAO = new UserDAO();
        this.view.getBtnLogin().addActionListener(e -> handleLogin());
    }

    /**
     * Menangani logika utama login.
     * Melakukan validasi input, pengecekan kredensial ke database, pengaturan sesi,
     * dan inisialisasi Dashboard jika login berhasil.
     */
    private void handleLogin() {
        String username = view.getUsernameInput();
        String password = view.getPasswordInput();

        // 1. Validasi Input Dasar
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Username dan Password tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Cek Kredensial ke Database
        User user = userDAO.login(username, password);

        if (user != null) {
            // Set User ke Session Manager (Singleton)
            SessionManager.getInstance().setCurrentUser(user);

            // 3. Penanganan Khusus untuk Dokter (Status Aktif)
            if (user.getRole() == UserRole.DOCTOR) {
                boolean statusUpdated = userDAO.updateUserActiveStatus(user.getId(), true);
                if (!statusUpdated) {
                    JOptionPane.showMessageDialog(view, "Gagal memperbarui status login.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Reset status pasien jika sebelumnya aplikasi crash saat konsultasi
                AppointmentDAO appDAO = new AppointmentDAO();
                appDAO.resetDoctorConsultationStatus(user.getId());
            }

            // 4. Buka Dashboard Utama
            view.dispose();
            MainDashboard dashboard = new MainDashboard();
            setupDashboardLogout(dashboard); // Konfigurasi tombol logout
            initializeSubControllers(dashboard, user); // Inisialisasi controller panel lain

            dashboard.setVisible(true);
            dashboard.showPanel(user.getRole().name());
        } else {
            JOptionPane.showMessageDialog(view, "Username atau Password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Mengatur logika tombol Logout pada dashboard.
     * Menghapus sesi, menonaktifkan status dokter, dan kembali ke layar login.
     * * @param dashboard Instance dari MainDashboard yang sedang aktif.
     */
    private void setupDashboardLogout(MainDashboard dashboard) {
        dashboard.getBtnLogout().addActionListener(e -> {
            User currentUser = SessionManager.getInstance().getCurrentUser();

            if (currentUser != null && currentUser.getRole() == UserRole.DOCTOR) {
                // Set status dokter menjadi tidak aktif
                userDAO.updateUserActiveStatus(currentUser.getId(), false);

                // Reset status pasien jika logout dilakukan saat sedang memeriksa
                AppointmentDAO appointmentDAO = new AppointmentDAO();
                appointmentDAO.resetDoctorConsultationStatus(currentUser.getId());
            }

            // Hapus sesi dan tutup dashboard
            SessionManager.getInstance().setCurrentUser(null);
            dashboard.dispose();

            // Buka kembali layar Login
            LoginView newLoginView = new LoginView();
            new LoginController(newLoginView);
            newLoginView.setVisible(true);
        });
    }

    /**
     * Menginisialisasi semua controller untuk panel-panel yang ada di dashboard.
     * * @param dashboard Instance MainDashboard.
     * @param user User yang sedang login (untuk keperluan passing data ke controller lain).
     */
    private void initializeSubControllers(MainDashboard dashboard, User user) {
        new AdminController(dashboard, dashboard.getAdminPanel(), userDAO);
        new ReceptionistController(dashboard.getReceptionistPanel());
        new InventoryController(dashboard.getInventoryPanel());
        new CashierController(dashboard.getCashierPanel());

        if (user.getRole() == UserRole.DOCTOR) {
            new DoctorController(dashboard.getDoctorPanel(), userDAO, user.getId());
        }
    }
}