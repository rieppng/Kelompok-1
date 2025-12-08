package com.mycompany.aplikasiklinik.view;

import javax.swing.*;
import java.awt.*;

/**
 * Class MainDashboard
 * * Frame utama aplikasi yang menampung semua panel fitur (Admin, Resepsionis, Dokter, dll).
 * Menggunakan CardLayout untuk berpindah antar tampilan berdasarkan Role pengguna yang login.
 */
public class MainDashboard extends JFrame {
    
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);
    
    // Instance Panel Fitur
    private final AdminPanel adminPanel = new AdminPanel();
    private final ReceptionistPanel receptionistPanel = new ReceptionistPanel();
    private final DoctorPanel doctorPanel = new DoctorPanel();
    private final InventoryPanel inventoryPanel = new InventoryPanel();
    private final CashierPanel cashierPanel = new CashierPanel();

    private final JButton btnLogout = new JButton("Logout");

    /**
     * Konstruktor MainDashboard.
     * Mengatur layout utama, header, dan mendaftarkan panel-panel fitur ke dalam CardLayout.
     */
    public MainDashboard() {
        setTitle("Sistem Informasi Klinik - Dashboard");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Header Panel ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.DARK_GRAY);
        
        JLabel title = new JLabel("  Klinik Sehat Sejahtera");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        
        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(btnLogout, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // --- Menambahkan Panel ke CardLayout ---
        // String kunci (Key) digunakan untuk memanggil panel tersebut nantinya
        mainPanel.add(adminPanel, "ADMIN");
        mainPanel.add(receptionistPanel, "RECEPTIONIST");
        mainPanel.add(doctorPanel, "DOCTOR");
        mainPanel.add(inventoryPanel, "PHARMACIST");
        mainPanel.add(cashierPanel, "CASHIER");
                
        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Mengganti tampilan panel aktif berdasarkan Role pengguna.
     * Melakukan mapping dari nama Role di database ke Key CardLayout.
     * * @param roleName Nama role (Contoh: "DOCTOR", "ADMIN").
     */
    public void showPanel(String roleName) {
        switch(roleName) {
            case "SUPERADMIN": cardLayout.show(mainPanel, "ADMIN"); break;
            case "RECEPTIONIST": cardLayout.show(mainPanel, "RECEPTIONIST"); break;
            case "DOCTOR": cardLayout.show(mainPanel, "DOCTOR"); break;
            case "PHARMACIST": cardLayout.show(mainPanel, "PHARMACIST"); break;
            case "CASHIER": cardLayout.show(mainPanel, "CASHIER"); break;
            default: JOptionPane.showMessageDialog(this, "Role tidak dikenali!");
        }
    }

    // --- GETTERS ---

    /** @return Tombol Logout untuk ditangani Controller. */
    public JButton getBtnLogout() { return btnLogout; }
    
    // Getter Panel untuk akses Controller spesifik
    public AdminPanel getAdminPanel() { return adminPanel; }
    public ReceptionistPanel getReceptionistPanel() { return receptionistPanel; }
    public DoctorPanel getDoctorPanel() { return doctorPanel; }
    public InventoryPanel getInventoryPanel() { return inventoryPanel; }
    public CashierPanel getCashierPanel() { return cashierPanel; }

    public CardLayout getCardLayout() { return cardLayout; }
    public JPanel getMainPanel() { return mainPanel; }
}