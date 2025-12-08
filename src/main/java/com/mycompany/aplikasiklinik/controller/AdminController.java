package com.mycompany.aplikasiklinik.controller;

import com.mycompany.aplikasiklinik.model.dao.*;
import com.mycompany.aplikasiklinik.model.entity.*;
import com.mycompany.aplikasiklinik.view.AdminPanel;
import com.mycompany.aplikasiklinik.view.MainDashboard;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;

/**
 * Controller untuk menangani fitur Administrator.
 * Termasuk: Manajemen User (Staff), Poli, Layanan (Biaya), Dokter,
 * dan Melihat Riwayat Rekam Medis.
 */
public class AdminController {
    private MainDashboard dashboard;
    private AdminPanel adminPanel;
    private UserDAO userDAO;
    private MedicalSpecialtyDAO specialtyDAO;
    private ServiceFeeDAO serviceFeeDAO;
    private MedicalRecordDAO medicalRecordDAO;

    public AdminController(MainDashboard dashboard, AdminPanel adminPanel, UserDAO userDAO) {
        this.dashboard = dashboard;
        this.adminPanel = adminPanel;
        this.userDAO = userDAO;
        this.specialtyDAO = new MedicalSpecialtyDAO();
        this.serviceFeeDAO = new ServiceFeeDAO();
        this.medicalRecordDAO = new MedicalRecordDAO();

        // Load Data Awal
        loadAllUsers();
        loadAllSpecialties();
        loadAllServiceFees(); 
        loadSpecialtiesToDoctorCombo();
        loadSpecialtiesToUpdateDoctorCombo();
        loadMedicalRecordHistory(); 

        // Listener: Manajemen User
        this.adminPanel.getBtnAddUser().addActionListener(e -> addUser());

        // Listener: Manajemen Poli
        this.adminPanel.getBtnAddSpecialty().addActionListener(e -> addSpecialty());
        this.adminPanel.getBtnUpdateSpecialty().addActionListener(e -> updateSpecialty());
        this.adminPanel.getBtnDeleteSpecialty().addActionListener(e -> deleteSpecialty());
        this.adminPanel.getTableSpecialties().getSelectionModel().addListSelectionListener(this::specialtyTableSelectionChanged);

        // Listener: Biaya Layanan
        this.adminPanel.getBtnAddFee().addActionListener(e -> addServiceFee());
        this.adminPanel.getBtnUpdateFee().addActionListener(e -> updateServiceFee());
        this.adminPanel.getBtnDeleteFee().addActionListener(e -> deleteServiceFee());
        this.adminPanel.getTableServiceFees().getSelectionModel().addListSelectionListener(this::serviceFeeTableSelectionChanged);

        // Listener: Dokter
        this.adminPanel.getBtnAddDoctor().addActionListener(e -> addDoctor());
        this.adminPanel.getBtnSearchDoctor().addActionListener(e -> searchDoctorToUpdate());
        this.adminPanel.getBtnUpdateDoctor().addActionListener(e -> updateDoctor());
        
        // Listener: Riwayat Medis
        this.adminPanel.getBtnRefreshMedicalRecords().addActionListener(e -> loadMedicalRecordHistory());
        this.adminPanel.getBtnDetailMedicalRecord().addActionListener(e -> showMedicalRecordDetail());
    }

    // ========================================================================
    // 1. MANAJEMEN USER (STAFF)
    // ========================================================================
    
    /**
     * Menambahkan user baru (Non-Dokter) ke database.
     */
    private void addUser() {
        try {
            String username = adminPanel.getUsername();
            String password = adminPanel.getPassword();
            String fullname = adminPanel.getFullname();
            String roleName = adminPanel.getSelectedRole();

            if (username.isEmpty() || password.isEmpty() || fullname.isEmpty()) {
                JOptionPane.showMessageDialog(adminPanel, "Semua field harus diisi!");
                return;
            }
            if (roleName.equals("SUPERADMIN")) {
                JOptionPane.showMessageDialog(adminPanel, "Role SUPERADMIN tidak dapat ditambahkan via menu ini!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            UserRole role = UserRole.valueOf(roleName);
            User newUser = new User(username, password, fullname, role, 0, false, 0.0);

            if (userDAO.addUser(newUser)) {
                JOptionPane.showMessageDialog(adminPanel, "User berhasil ditambahkan!");
                adminPanel.clearForm();
                loadAllUsers();
            } else {
                JOptionPane.showMessageDialog(adminPanel, "Gagal menambahkan user. Username mungkin sudah ada.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(adminPanel, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAllUsers() {
        DefaultTableModel model = adminPanel.getUserTableModel();
        model.setRowCount(0);
        List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            model.addRow(new Object[]{
                user.getId(), user.getUsername(), user.getFullName(),
                user.getRole().toString(), user.isActive() ? "Ya" : "Tidak", user.getConsultationFee()
            });
        }
    }

    // ========================================================================
    // 2. MANAJEMEN POLI (SPECIALTY)
    // ========================================================================
    
    private void addSpecialty() {
        String name = adminPanel.getSpecialtyNameInput().trim();
        if (name.isEmpty()) { JOptionPane.showMessageDialog(adminPanel, "Nama poli tidak boleh kosong!"); return; }
        
        if (specialtyDAO.addSpecialty(new MedicalSpecialty(name))) {
            JOptionPane.showMessageDialog(adminPanel, "Poli berhasil ditambahkan!");
            adminPanel.clearSpecialtyForm();
            refreshSpecialtyData();
        } else { JOptionPane.showMessageDialog(adminPanel, "Gagal menambahkan poli."); }
    }

    private void updateSpecialty() {
        int selectedRow = adminPanel.getTableSpecialties().getSelectedRow();
        if (selectedRow == -1) return;
        
        int id = (int) adminPanel.getSpecialtyTableModel().getValueAt(selectedRow, 0);
        String name = adminPanel.getSpecialtyNameInput().trim();
        if (name.isEmpty()) { JOptionPane.showMessageDialog(adminPanel, "Nama poli tidak boleh kosong!"); return; }
        
        if (specialtyDAO.updateSpecialty(new MedicalSpecialty(id, name))) {
            JOptionPane.showMessageDialog(adminPanel, "Poli berhasil diperbarui!");
            adminPanel.clearSpecialtyForm();
            refreshSpecialtyData();
        } else { JOptionPane.showMessageDialog(adminPanel, "Gagal memperbarui poli."); }
    }

    private void deleteSpecialty() {
        int selectedRow = adminPanel.getTableSpecialties().getSelectedRow();
        if (selectedRow == -1) return;
        int id = (int) adminPanel.getSpecialtyTableModel().getValueAt(selectedRow, 0);
        
        if (JOptionPane.showConfirmDialog(adminPanel, "Yakin hapus poli ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (specialtyDAO.deleteSpecialty(id)) {
                JOptionPane.showMessageDialog(adminPanel, "Poli berhasil dihapus!");
                adminPanel.clearSpecialtyForm();
                refreshSpecialtyData();
            } else { JOptionPane.showMessageDialog(adminPanel, "Gagal menghapus poli. Mungkin sedang digunakan oleh dokter."); }
        }
    }

    private void refreshSpecialtyData() {
        loadAllSpecialties();
        loadSpecialtiesToDoctorCombo();
        loadSpecialtiesToUpdateDoctorCombo();
    }

    private void loadAllSpecialties() {
        DefaultTableModel model = adminPanel.getSpecialtyTableModel();
        model.setRowCount(0);
        List<MedicalSpecialty> specialties = specialtyDAO.getAllSpecialties();
        for (MedicalSpecialty specialty : specialties) { model.addRow(new Object[]{specialty.getId(), specialty.getName()}); }
    }

    private void specialtyTableSelectionChanged(ListSelectionEvent e) {
        boolean hasSelection = adminPanel.getTableSpecialties().getSelectedRow() != -1;
        if (!e.getValueIsAdjusting() && hasSelection) {
            int selectedRow = adminPanel.getTableSpecialties().getSelectedRow();
            String name = adminPanel.getSpecialtyTableModel().getValueAt(selectedRow, 1).toString();
            adminPanel.getTxtSpecialtyName().setText(name);
        }
        adminPanel.getBtnUpdateSpecialty().setEnabled(hasSelection);
        adminPanel.getBtnDeleteSpecialty().setEnabled(hasSelection);
    }

    // ========================================================================
    // 3. MANAJEMEN BIAYA LAYANAN
    // ========================================================================
    
    private void addServiceFee() {
        String name = adminPanel.getFeeNameInput().trim();
        String priceStr = adminPanel.getFeePriceInput().trim();
        if (name.isEmpty() || priceStr.isEmpty()) { JOptionPane.showMessageDialog(adminPanel, "Nama dan Harga harus diisi!"); return; }
        
        try {
            double price = Double.parseDouble(priceStr);
            if (price < 0) { JOptionPane.showMessageDialog(adminPanel, "Harga tidak boleh negatif!"); return; }
            if (serviceFeeDAO.addServiceFee(new ServiceFee(0, name, price))) {
                JOptionPane.showMessageDialog(adminPanel, "Layanan berhasil ditambahkan!");
                adminPanel.clearFeeForm(); loadAllServiceFees();
            } else { JOptionPane.showMessageDialog(adminPanel, "Gagal menambahkan layanan."); }
        } catch (NumberFormatException e) { JOptionPane.showMessageDialog(adminPanel, "Harga harus berupa angka valid!"); }
    }

    private void updateServiceFee() {
        int selectedRow = adminPanel.getTableServiceFees().getSelectedRow();
        if (selectedRow == -1) return;
        
        int id = (int) adminPanel.getFeeTableModel().getValueAt(selectedRow, 0);
        String name = adminPanel.getFeeNameInput().trim();
        String priceStr = adminPanel.getFeePriceInput().trim();
        
        try {
            double price = Double.parseDouble(priceStr);
            if (serviceFeeDAO.updateServiceFee(new ServiceFee(id, name, price))) {
                JOptionPane.showMessageDialog(adminPanel, "Layanan berhasil diperbarui!");
                adminPanel.clearFeeForm(); loadAllServiceFees();
            } else { JOptionPane.showMessageDialog(adminPanel, "Gagal memperbarui layanan."); }
        } catch (NumberFormatException e) { JOptionPane.showMessageDialog(adminPanel, "Harga harus berupa angka valid!"); }
    }

    private void deleteServiceFee() {
        int selectedRow = adminPanel.getTableServiceFees().getSelectedRow();
        if (selectedRow == -1) return;
        int id = (int) adminPanel.getFeeTableModel().getValueAt(selectedRow, 0);
        
        if (JOptionPane.showConfirmDialog(adminPanel, "Yakin hapus layanan ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (serviceFeeDAO.deleteServiceFee(id)) {
                JOptionPane.showMessageDialog(adminPanel, "Layanan berhasil dihapus!");
                adminPanel.clearFeeForm(); loadAllServiceFees();
            } else { JOptionPane.showMessageDialog(adminPanel, "Gagal menghapus layanan."); }
        }
    }

    private void loadAllServiceFees() {
        DefaultTableModel model = adminPanel.getFeeTableModel();
        model.setRowCount(0);
        List<ServiceFee> fees = serviceFeeDAO.getAllServiceFees();
        for (ServiceFee fee : fees) { model.addRow(new Object[]{fee.getId(), fee.getName(), fee.getPrice()}); }
    }

    private void serviceFeeTableSelectionChanged(ListSelectionEvent e) {
        boolean hasSelection = adminPanel.getTableServiceFees().getSelectedRow() != -1;
        if (!e.getValueIsAdjusting() && hasSelection) {
            int selectedRow = adminPanel.getTableServiceFees().getSelectedRow();
            adminPanel.getTxtFeeName().setText(adminPanel.getFeeTableModel().getValueAt(selectedRow, 1).toString());
            adminPanel.getTxtFeePrice().setText(adminPanel.getFeeTableModel().getValueAt(selectedRow, 2).toString());
        }
        adminPanel.getBtnUpdateFee().setEnabled(hasSelection);
        adminPanel.getBtnDeleteFee().setEnabled(hasSelection);
    }

    // ========================================================================
    // 4. MANAJEMEN DOKTER
    // ========================================================================
    
    private void loadSpecialtiesToDoctorCombo() {
        DefaultComboBoxModel<MedicalSpecialty> model = new DefaultComboBoxModel<>();
        specialtyDAO.getAllSpecialties().forEach(model::addElement);
        adminPanel.getComboDoctorSpecialty().setModel(model);
    }
    
    private void loadSpecialtiesToUpdateDoctorCombo() {
        DefaultComboBoxModel<MedicalSpecialty> model = new DefaultComboBoxModel<>();
        specialtyDAO.getAllSpecialties().forEach(model::addElement);
        adminPanel.getComboUpdateSpecialty().setModel(model);
    }
    
    /**
     * Menambahkan user dengan Role DOCTOR beserta spesialisasi dan biaya jasanya.
     */
    private void addDoctor() {
        String username = adminPanel.getDoctorUsername().trim();
        String password = adminPanel.getDoctorPassword().trim();
        String fullname = adminPanel.getDoctorFullname().trim();
        MedicalSpecialty selectedSpecialty = adminPanel.getSelectedDoctorSpecialty();
        String feeStr = adminPanel.getDoctorConsultationFee().trim();

        if (username.isEmpty() || password.isEmpty() || fullname.isEmpty() || selectedSpecialty == null || feeStr.isEmpty()) {
            JOptionPane.showMessageDialog(adminPanel, "Semua field harus diisi!"); return;
        }

        try {
            double fee = Double.parseDouble(feeStr);
            User newDoctor = new User(username, password, fullname, UserRole.DOCTOR, selectedSpecialty.getId(), false, fee);

            if (userDAO.addUser(newDoctor)) {
                JOptionPane.showMessageDialog(adminPanel, "Dokter berhasil ditambahkan!");
                adminPanel.clearAddDoctorForm();
                loadAllUsers();
            } else { JOptionPane.showMessageDialog(adminPanel, "Gagal. Username mungkin sudah ada."); }
        } catch (NumberFormatException e) { JOptionPane.showMessageDialog(adminPanel, "Biaya jasa harus angka!"); }
    }
    
    /**
     * Mencari data dokter berdasarkan username untuk keperluan update.
     */
    private void searchDoctorToUpdate() {
        String username = adminPanel.getSearchDoctorId().trim(); 
        if (username.isEmpty()) { JOptionPane.showMessageDialog(adminPanel, "Masukkan Username dokter!"); return; }

        User doctor = userDAO.getUserByUsername(username);

        if (doctor != null && doctor.getRole() == UserRole.DOCTOR) {
            adminPanel.getTxtUpdateUsername().setText(doctor.getUsername());
            adminPanel.getTxtUpdateFullname().setText(doctor.getFullName());
            
            MedicalSpecialty specialty = specialtyDAO.getSpecialtyById(doctor.getMedicalSpecialtyId());
            adminPanel.getComboUpdateSpecialty().setSelectedItem(specialty);
            adminPanel.getTxtUpdateConsultationFee().setText(String.valueOf(doctor.getConsultationFee()));
        } else {
            JOptionPane.showMessageDialog(adminPanel, "Dokter tidak ditemukan.", "Info", JOptionPane.INFORMATION_MESSAGE);
            adminPanel.clearUpdateDoctorForm();
        }
    }
    
    private void updateDoctor() {
        String searchKey = adminPanel.getSearchDoctorId().trim();
        String newUsername = adminPanel.getTxtUpdateUsername().getText().trim();
        String newFullname = adminPanel.getTxtUpdateFullname().getText().trim();
        MedicalSpecialty specialty = (MedicalSpecialty) adminPanel.getComboUpdateSpecialty().getSelectedItem();
        String feeStr = adminPanel.getTxtUpdateConsultationFee().getText().trim();

        if (searchKey.isEmpty() || newUsername.isEmpty() || newFullname.isEmpty() || specialty == null || feeStr.isEmpty()) {
            JOptionPane.showMessageDialog(adminPanel, "Data belum lengkap!"); return;
        }

        try {
            User original = userDAO.getUserByUsername(searchKey);
            if (original == null) { JOptionPane.showMessageDialog(adminPanel, "Dokter asal tidak ditemukan!"); return; }
            
            User updated = new User(original.getId(), newUsername, "", newFullname, UserRole.DOCTOR, specialty.getId(), false, Double.parseDouble(feeStr));
            if (userDAO.updateUser(updated)) {
                JOptionPane.showMessageDialog(adminPanel, "Data dokter diperbarui!");
                adminPanel.clearUpdateDoctorForm(); loadAllUsers(); 
            } else { JOptionPane.showMessageDialog(adminPanel, "Gagal memperbarui data."); }
        } catch (NumberFormatException e) { JOptionPane.showMessageDialog(adminPanel, "Biaya jasa harus angka!"); }
    }

    // ========================================================================
    // 5. RIWAYAT MEDIS
    // ========================================================================
    
    private void loadMedicalRecordHistory() {
        DefaultTableModel model = adminPanel.getMedicalRecordTableModel();
        model.setRowCount(0);
        List<MedicalRecordHistory> historyList = medicalRecordDAO.getAllHistory();
        for (MedicalRecordHistory rec : historyList) {
            model.addRow(new Object[]{rec.getId(), rec.getDate(), rec.getPatientName(), rec.getDoctorName(), rec.getPoliName(), rec.getDiagnosis(), rec.getTreatment()});
        }
    }

    /**
     * Menampilkan detail lengkap rekam medis dalam dialog terpisah.
     */
    private void showMedicalRecordDetail() {
        int selectedRow = adminPanel.getTableMedicalRecords().getSelectedRow();
        if (selectedRow == -1) { JOptionPane.showMessageDialog(adminPanel, "Pilih baris rekam medis terlebih dahulu."); return; }

        DefaultTableModel model = adminPanel.getMedicalRecordTableModel();
        int medicalRecordId = (int) model.getValueAt(selectedRow, 0); 
        int appointmentId = medicalRecordDAO.getAppointmentIdByMedicalRecord(medicalRecordId);

        String detailText = buildMedicalRecordDetailString(model, selectedRow, medicalRecordId, appointmentId);

        JTextArea txtDetail = new JTextArea(20, 50);
        txtDetail.setEditable(false);
        txtDetail.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtDetail.setText(detailText);
        txtDetail.setCaretPosition(0); 

        JOptionPane.showMessageDialog(adminPanel, new JScrollPane(txtDetail), "Detail Rekam Medis", JOptionPane.PLAIN_MESSAGE);
    }

    private String buildMedicalRecordDetailString(DefaultTableModel model, int row, int mrId, int appId) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== DETAIL REKAM MEDIS =====\n");
        sb.append("ID RM      : ").append(mrId).append("\n");
        sb.append("Tanggal    : ").append(model.getValueAt(row, 1)).append("\n");
        sb.append("Pasien     : ").append(model.getValueAt(row, 2)).append("\n");
        sb.append("Dokter     : ").append(model.getValueAt(row, 3)).append("\n");
        sb.append("------------------------------\n");
        sb.append("DATA FISIK :\n").append(medicalRecordDAO.getPatientVitals(appId)).append("\n");
        sb.append("------------------------------\n");
        sb.append("DIAGNOSA   :\n").append(model.getValueAt(row, 5)).append("\n\n");
        sb.append("TINDAKAN   :\n").append(model.getValueAt(row, 6)).append("\n");
        sb.append("------------------------------\n");
        sb.append("RESEP OBAT :\n").append(medicalRecordDAO.getPrescriptionDetails(mrId)).append("\n");
        sb.append("LAYANAN LAIN:\n").append(medicalRecordDAO.getServiceDetails(appId)).append("\n");
        return sb.toString();
    }
}