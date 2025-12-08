package com.mycompany.aplikasiklinik.view;

import com.mycompany.aplikasiklinik.model.entity.MedicalSpecialty;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Class AdminPanel
 * * Panel kompleks untuk Administrator.
 * Menggunakan JTabbedPane untuk memisahkan manajemen Staff, Poli, Dokter, Layanan, dan Rekam Medis.
 */
public class AdminPanel extends JPanel {

    // --- Komponen Tab 1: Manajemen Staff ---
    private final JTextField txtNewUsername = new JTextField(20);
    private final JTextField txtNewPassword = new JTextField(20);
    private final JTextField txtFullname = new JTextField(20);
    private final JComboBox<String> comboRole = new JComboBox<>(new String[]{"RECEPTIONIST", "DOCTOR", "PHARMACIST", "CASHIER", "SUPERADMIN"});
    private final JButton btnAddUser = new JButton("Tambah User");
    private final JTable tableUsers = new JTable();
    private DefaultTableModel userTableModel;

    // --- Komponen Tab 2: Manajemen Poli ---
    private final JTextField txtSpecialtyName = new JTextField(20);
    private final JTable tableSpecialties = new JTable();
    private DefaultTableModel specialtyTableModel;
    private final JButton btnAddSpecialty = new JButton("Tambah Poli");
    private final JButton btnUpdateSpecialty = new JButton("Update Poli");
    private final JButton btnDeleteSpecialty = new JButton("Hapus Poli");

    // --- Komponen Tab 3: Biaya Layanan ---
    private final JTextField txtFeeName = new JTextField(20);
    private final JTextField txtFeePrice = new JTextField(20);
    private final JTable tableServiceFees = new JTable();
    private DefaultTableModel feeTableModel;
    private final JButton btnAddFee = new JButton("Tambah Layanan");
    private final JButton btnUpdateFee = new JButton("Update Layanan");
    private final JButton btnDeleteFee = new JButton("Hapus Layanan");

    // --- Komponen Tab 4: Tambah Dokter ---
    private final JTextField txtDoctorUsername = new JTextField(20);
    private final JTextField txtDoctorPassword = new JTextField(20);
    private final JTextField txtDoctorFullname = new JTextField(20);
    private final JComboBox<MedicalSpecialty> comboDoctorSpecialty = new JComboBox<>();
    private final JTextField txtDoctorConsultationFee = new JTextField(20);
    private final JButton btnAddDoctor = new JButton("Simpan Dokter Baru");

    // --- Komponen Tab 5: Update Dokter ---
    private final JTextField txtSearchDoctorId = new JTextField(15);
    private final JButton btnSearchDoctor = new JButton("Cari Username");
    private final JTextField txtUpdateUsername = new JTextField(20);
    private final JTextField txtUpdateFullname = new JTextField(20);
    private final JComboBox<MedicalSpecialty> comboUpdateSpecialty = new JComboBox<>();
    private final JTextField txtUpdateConsultationFee = new JTextField(20);
    private final JButton btnUpdateDoctor = new JButton("Simpan Perubahan Dokter");
    
    // --- Komponen Tab 6: Riwayat Medis ---
    private final JTable tableMedicalRecords = new JTable();
    private DefaultTableModel medicalRecordTableModel;
    private final JButton btnRefreshMedicalRecords = new JButton("Refresh Data");
    private final JButton btnDetailMedicalRecord = new JButton("Lihat Detail Rekam Medis"); 

    /**
     * Konstruktor AdminPanel.
     * Menginisialisasi komponen tabel, layout tab, dan header panel.
     */
    public AdminPanel() {
        setLayout(new BorderLayout());
        initializeTableModels();
        setupMainLayout();
    }

    /**
     * Menginisialisasi Model Tabel untuk semua fitur (User, Poli, Fee, Rekam Medis).
     * Mengatur nama kolom dan lebar kolom.
     */
    private void initializeTableModels() {
        // 1. Model Tabel User
        userTableModel = new DefaultTableModel(new Object[]{"ID", "Username", "Nama", "Role", "Aktif", "Biaya Jasa"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableUsers.setModel(userTableModel);
        tableUsers.getTableHeader().setReorderingAllowed(false);
        tableUsers.getColumnModel().getColumn(0).setPreferredWidth(30); // ID Kecil
        tableUsers.getColumnModel().getColumn(0).setMaxWidth(50);

        // 2. Model Tabel Poli
        specialtyTableModel = new DefaultTableModel(new Object[]{"ID", "Nama Poli"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableSpecialties.setModel(specialtyTableModel);
        tableSpecialties.getTableHeader().setReorderingAllowed(false);
        tableSpecialties.getColumnModel().getColumn(0).setPreferredWidth(30);
        tableSpecialties.getColumnModel().getColumn(0).setMaxWidth(50);

        // 3. Model Tabel Biaya Layanan
        feeTableModel = new DefaultTableModel(new Object[]{"ID", "Nama Layanan", "Harga (Rp)"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableServiceFees.setModel(feeTableModel);
        tableServiceFees.getTableHeader().setReorderingAllowed(false);
        tableServiceFees.getColumnModel().getColumn(0).setPreferredWidth(30);
        tableServiceFees.getColumnModel().getColumn(0).setMaxWidth(50);
        
        // 4. Model Tabel Riwayat Medis
        medicalRecordTableModel = new DefaultTableModel(new Object[]{"ID RM", "Tanggal", "Nama Pasien", "Dokter", "Poli", "Diagnosa", "Tindakan"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableMedicalRecords.setModel(medicalRecordTableModel);
        tableMedicalRecords.getTableHeader().setReorderingAllowed(false);
        tableMedicalRecords.getColumnModel().getColumn(0).setPreferredWidth(40);
        tableMedicalRecords.getColumnModel().getColumn(0).setMaxWidth(60);
        tableMedicalRecords.getColumnModel().getColumn(1).setPreferredWidth(80);
        
        btnDetailMedicalRecord.setEnabled(false); // Default mati
    }

    /**
     * Menyusun struktur utama panel menggunakan JTabbedPane.
     */
    private void setupMainLayout() {
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Manajemen Staff", createStaffPanel());
        tabbedPane.addTab("Manajemen Poli", createPoliPanel());
        tabbedPane.addTab("Kelola Biaya Layanan", createServiceFeePanel());
        tabbedPane.addTab("Tambah Dokter", createAddDoctorPanel());
        tabbedPane.addTab("Update Dokter", createUpdateDoctorPanel());
        tabbedPane.addTab("Arsip Rekam Medis", createMedicalRecordPanel());

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(new Color(230, 240, 255));
        headerPanel.add(new JLabel("Halaman Administrator Klinik"));
        
        add(headerPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    // --- HELPER METHODS UNTUK MEMBUAT PANEL PER-TAB ---

    /** Membuat panel manajemen user/staff. */
    private JPanel createStaffPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Tambah Staff Baru (Non-Dokter)"));
        
        GridBagConstraints gbc = createGbc();
        addFormRow(formPanel, "Username:", txtNewUsername, 0, gbc);
        addFormRow(formPanel, "Password:", txtNewPassword, 1, gbc);
        addFormRow(formPanel, "Nama Lengkap:", txtFullname, 2, gbc);
        addFormRow(formPanel, "Role:", comboRole, 3, gbc);
        
        gbc.gridx = 1; gbc.gridy = 4; gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(btnAddUser, gbc);
        
        JPanel formWrapper = new JPanel(new BorderLayout());
        formWrapper.add(formPanel, BorderLayout.NORTH);
        
        panel.add(formWrapper, BorderLayout.WEST);
        panel.add(new JScrollPane(tableUsers), BorderLayout.CENTER);
        return panel;
    }

    /** Membuat panel manajemen poli/spesialisasi. */
    private JPanel createPoliPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Poli"));
        GridBagConstraints gbc = createGbc();
        addFormRow(formPanel, "Nama Poli:", txtSpecialtyName, 0, gbc);
        
        JPanel btnPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        btnPanel.add(btnAddSpecialty); 
        btnPanel.add(btnUpdateSpecialty); 
        btnPanel.add(btnDeleteSpecialty);
        
        gbc.gridx = 1; gbc.gridy = 1; 
        formPanel.add(btnPanel, gbc);
        
        JPanel formWrapper = new JPanel(new BorderLayout());
        formWrapper.add(formPanel, BorderLayout.NORTH);
        leftPanel.add(formWrapper, BorderLayout.CENTER);
        
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(new JScrollPane(tableSpecialties), BorderLayout.CENTER);
        return panel;
    }

    /** Membuat panel manajemen biaya layanan tambahan. */
    private JPanel createServiceFeePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Biaya Layanan"));
        
        GridBagConstraints gbc = createGbc();
        addFormRow(formPanel, "Nama Layanan:", txtFeeName, 0, gbc);
        addFormRow(formPanel, "Harga (Rp):", txtFeePrice, 1, gbc);
        
        JPanel btnPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        btnPanel.add(btnAddFee); 
        btnPanel.add(btnUpdateFee); 
        btnPanel.add(btnDeleteFee);
        
        gbc.gridx = 1; gbc.gridy = 2; 
        formPanel.add(btnPanel, gbc);
        
        JPanel formWrapper = new JPanel(new BorderLayout());
        formWrapper.add(formPanel, BorderLayout.NORTH);
        leftPanel.add(formWrapper, BorderLayout.CENTER);
        
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(new JScrollPane(tableServiceFees), BorderLayout.CENTER);
        
        btnUpdateFee.setEnabled(false); 
        btnDeleteFee.setEnabled(false);
        return panel;
    }

    /** Membuat panel khusus registrasi dokter (karena field berbeda dengan staff biasa). */
    private JPanel createAddDoctorPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Registrasi Dokter"));
        
        GridBagConstraints gbc = createGbc();
        addFormRow(formPanel, "Username:", txtDoctorUsername, 0, gbc);
        addFormRow(formPanel, "Password:", txtDoctorPassword, 1, gbc);
        addFormRow(formPanel, "Nama Lengkap:", txtDoctorFullname, 2, gbc);
        addFormRow(formPanel, "Spesialisasi / Poli:", comboDoctorSpecialty, 3, gbc);
        addFormRow(formPanel, "Biaya Jasa (Rp):", txtDoctorConsultationFee, 4, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; 
        gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 5, 5, 5); 
        btnAddDoctor.setPreferredSize(new Dimension(200, 35));
        formPanel.add(btnAddDoctor, gbc);
        
        panel.add(formPanel);
        return panel;
    }

    /** Membuat panel untuk update data dokter (termasuk pencarian user ID). */
    private JPanel createUpdateDoctorPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Langkah 1: Cari Dokter"));
        searchPanel.add(new JLabel("Masukkan Username Dokter:")); 
        searchPanel.add(txtSearchDoctorId); 
        searchPanel.add(btnSearchDoctor);
        
        JPanel editPanel = new JPanel(new GridBagLayout());
        editPanel.setBorder(BorderFactory.createTitledBorder("Langkah 2: Edit Data Dokter"));
        GridBagConstraints gbc = createGbc();
        addFormRow(editPanel, "Username:", txtUpdateUsername, 0, gbc);
        addFormRow(editPanel, "Nama Lengkap:", txtUpdateFullname, 1, gbc);
        addFormRow(editPanel, "Spesialisasi / Poli:", comboUpdateSpecialty, 2, gbc);
        addFormRow(editPanel, "Biaya Jasa (Rp):", txtUpdateConsultationFee, 3, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; 
        gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5); 
        btnUpdateDoctor.setPreferredSize(new Dimension(200, 35));
        editPanel.add(btnUpdateDoctor, gbc);
        
        panel.add(searchPanel, BorderLayout.NORTH); 
        panel.add(editPanel, BorderLayout.CENTER);
        return panel;
    }
    
    /** Membuat panel untuk melihat arsip rekam medis. */
    private JPanel createMedicalRecordPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Listener tabel untuk mengaktifkan tombol detail hanya jika ada baris terpilih
        tableMedicalRecords.getSelectionModel().addListSelectionListener(e -> {
            boolean hasSelection = tableMedicalRecords.getSelectedRow() != -1;
            btnDetailMedicalRecord.setEnabled(hasSelection);
        });
        
        panel.add(new JScrollPane(tableMedicalRecords), BorderLayout.CENTER);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnDetailMedicalRecord); 
        btnPanel.add(btnRefreshMedicalRecords);
        
        panel.add(btnPanel, BorderLayout.SOUTH);
        return panel;
    }

    /** * Helper untuk membuat GridBagConstraints standar. 
     * @return GridBagConstraints yang sudah dikonfigurasi.
     */
    private GridBagConstraints createGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 
        gbc.anchor = GridBagConstraints.WEST;
        return gbc;
    }

    /** * Helper untuk menambahkan baris form (Label + Field) ke GridBagLayout. 
     */
    private void addFormRow(JPanel panel, String labelText, JComponent field, int row, GridBagConstraints gbc) {
        gbc.gridx = 0; gbc.gridy = row; 
        gbc.weightx = 0.0; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel(labelText), gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);
    }

    // ================= GETTERS =================

    // --- Staff Getters ---
    public String getUsername() { return txtNewUsername.getText(); }
    public String getPassword() { return txtNewPassword.getText(); }
    public String getFullname() { return txtFullname.getText(); }
    public String getSelectedRole() { return (String) comboRole.getSelectedItem(); }
    public JButton getBtnAddUser() { return btnAddUser; }
    public DefaultTableModel getUserTableModel() { return userTableModel; }

    // --- Poli Getters ---
    public String getSpecialtyNameInput() { return txtSpecialtyName.getText(); }
    public JTable getTableSpecialties() { return tableSpecialties; }
    public DefaultTableModel getSpecialtyTableModel() { return specialtyTableModel; }
    public JButton getBtnAddSpecialty() { return btnAddSpecialty; }
    public JButton getBtnUpdateSpecialty() { return btnUpdateSpecialty; }
    public JButton getBtnDeleteSpecialty() { return btnDeleteSpecialty; }
    public JTextField getTxtSpecialtyName() { return txtSpecialtyName; }

    // --- Fee Getters ---
    public String getFeeNameInput() { return txtFeeName.getText(); }
    public String getFeePriceInput() { return txtFeePrice.getText(); }
    public JTextField getTxtFeeName() { return txtFeeName; }
    public JTextField getTxtFeePrice() { return txtFeePrice; }
    public JTable getTableServiceFees() { return tableServiceFees; }
    public DefaultTableModel getFeeTableModel() { return feeTableModel; }
    public JButton getBtnAddFee() { return btnAddFee; }
    public JButton getBtnUpdateFee() { return btnUpdateFee; }
    public JButton getBtnDeleteFee() { return btnDeleteFee; }

    // --- Doctor Getters ---
    public String getDoctorUsername() { return txtDoctorUsername.getText(); }
    public String getDoctorPassword() { return txtDoctorPassword.getText(); }
    public String getDoctorFullname() { return txtDoctorFullname.getText(); }
    public MedicalSpecialty getSelectedDoctorSpecialty() { return (MedicalSpecialty) comboDoctorSpecialty.getSelectedItem(); }
    public String getDoctorConsultationFee() { return txtDoctorConsultationFee.getText(); }
    public JButton getBtnAddDoctor() { return btnAddDoctor; }
    public JComboBox<MedicalSpecialty> getComboDoctorSpecialty() { return comboDoctorSpecialty; }

    // --- Update Doctor Getters ---
    public String getSearchDoctorId() { return txtSearchDoctorId.getText(); }
    public JButton getBtnSearchDoctor() { return btnSearchDoctor; }
    public JTextField getTxtUpdateUsername() { return txtUpdateUsername; }
    public JTextField getTxtUpdateFullname() { return txtUpdateFullname; }
    public JComboBox<MedicalSpecialty> getComboUpdateSpecialty() { return comboUpdateSpecialty; }
    public JTextField getTxtUpdateConsultationFee() { return txtUpdateConsultationFee; }
    public JButton getBtnUpdateDoctor() { return btnUpdateDoctor; }
    
    // --- Medical Record Getters ---
    public JButton getBtnRefreshMedicalRecords() { return btnRefreshMedicalRecords; }
    public JButton getBtnDetailMedicalRecord() { return btnDetailMedicalRecord; }
    public DefaultTableModel getMedicalRecordTableModel() { return medicalRecordTableModel; }
    public JTable getTableMedicalRecords() { return tableMedicalRecords; }

    // --- Clear Form Methods ---
    public void clearForm() { txtNewUsername.setText(""); txtNewPassword.setText(""); txtFullname.setText(""); }
    public void clearSpecialtyForm() { txtSpecialtyName.setText(""); tableSpecialties.clearSelection(); btnUpdateSpecialty.setEnabled(false); btnDeleteSpecialty.setEnabled(false); }
    public void clearFeeForm() { txtFeeName.setText(""); txtFeePrice.setText(""); tableServiceFees.clearSelection(); btnUpdateFee.setEnabled(false); btnDeleteFee.setEnabled(false); }
    public void clearAddDoctorForm() { txtDoctorUsername.setText(""); txtDoctorPassword.setText(""); txtDoctorFullname.setText(""); comboDoctorSpecialty.setSelectedIndex(-1); txtDoctorConsultationFee.setText(""); }
    public void clearUpdateDoctorForm() { txtSearchDoctorId.setText(""); txtUpdateUsername.setText(""); txtUpdateFullname.setText(""); comboUpdateSpecialty.setSelectedIndex(-1); txtUpdateConsultationFee.setText(""); }
}