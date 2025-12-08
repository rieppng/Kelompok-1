package com.mycompany.aplikasiklinik.view;

import com.mycompany.aplikasiklinik.model.entity.ServiceFee;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

/**
 * Class DoctorPanel
 * * Panel utama untuk Dokter.
 * Terbagi menjadi dua area utama:
 * 1. Kiri: Daftar Tunggu Pasien (Card QUEUE) dan Info Pasien (Card INFO).
 * 2. Kanan: Form Pemeriksaan (Diagnosa, Resep, Tindakan).
 */
public class DoctorPanel extends JPanel {
    
    // Layout Card untuk panel kiri (Switch antara Antrian dan Info Pasien)
    private final CardLayout leftCardLayout = new CardLayout();
    private final JPanel leftMainPanel = new JPanel(leftCardLayout); 

    // --- Panel Kiri: Antrian (QUEUE) ---
    private final JPanel pnlQueue = new JPanel(new BorderLayout());
    private final JTable tableWaitingList = new JTable();
    private DefaultTableModel waitingListModel;
    private final JButton btnStartConsult = new JButton("Mulai Periksa");

    // --- Panel Kiri: Info Pasien (INFO) ---
    private final JPanel pnlPatientInfo = new JPanel(new GridBagLayout());
    private final JLabel lblInfoName = new JLabel("-");
    private final JLabel lblInfoNik = new JLabel("-");
    private final JLabel lblInfoGender = new JLabel("-");
    private final JLabel lblInfoDob = new JLabel("-");
    private final JLabel lblInfoPhone = new JLabel("-");
    private final JTextArea txtInfoAddress = new JTextArea(3, 20);
    private final JLabel lblInfoWeight = new JLabel("-");
    private final JLabel lblInfoHeight = new JLabel("-");
    private final JLabel lblInfoBloodPressure = new JLabel("-");
    
    // --- Panel Kanan: Form Medis ---
    private final JTextArea txtSymptoms = new JTextArea(3, 20);
    private final JTextArea txtDiagnosis = new JTextArea(3, 20);
    private final JTextArea txtTreatment = new JTextArea(3, 20);
    private final JComboBox<ServiceFee> comboTreatmentService = new JComboBox<>(); 
    private final JTable tablePrescription = new JTable(); 
    private DefaultTableModel prescriptionModel;
    
    private final JButton btnRemoveSelectedPrescription = new JButton("Hapus Item Dipilih");
    private final JButton btnSaveRecord = new JButton("Simpan Rekam Medis");
    private final JButton btnAddMedication = new JButton("Tambah Obat");

    /**
     * Konstruktor DoctorPanel.
     * Mengatur grid layout 2 kolom dan menginisialisasi komponen kiri dan kanan.
     */
    public DoctorPanel() {
        setLayout(new GridLayout(1, 2));

        // 1. Setup Panel Kiri (Antrian & Info Pasien)
        setupLeftPanel();
        
        // 2. Setup Panel Kanan (Form Medis)
        setupRightPanel();

        // Tampilan awal: Daftar Antrian
        showQueueView();
    }
    
    /**
     * Mengonfigurasi panel kiri yang menggunakan CardLayout.
     * Card 1: Tabel Antrian.
     * Card 2: Detail Informasi Pasien.
     */
    private void setupLeftPanel() {
        // --- Card 1: Queue Panel ---
        pnlQueue.setBorder(BorderFactory.createTitledBorder("Daftar Tunggu Pasien"));
        
        waitingListModel = new DefaultTableModel(new Object[]{"No", "Nama Pasien", "Status"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableWaitingList.setModel(waitingListModel);
        tableWaitingList.getTableHeader().setReorderingAllowed(false);
        
        TableColumnModel cmWait = tableWaitingList.getColumnModel();
        cmWait.getColumn(0).setPreferredWidth(30);  // No
        cmWait.getColumn(0).setMaxWidth(50);
        cmWait.getColumn(1).setPreferredWidth(150); // Nama
        cmWait.getColumn(2).setPreferredWidth(80);  // Status
        
        pnlQueue.add(new JScrollPane(tableWaitingList), BorderLayout.CENTER);
        pnlQueue.add(btnStartConsult, BorderLayout.SOUTH);

        // --- Card 2: Patient Info Panel ---
        pnlPatientInfo.setBorder(BorderFactory.createTitledBorder("Informasi Pasien"));
        setupPatientInfoLayout(); 

        leftMainPanel.add(pnlQueue, "QUEUE");
        leftMainPanel.add(pnlPatientInfo, "INFO");
        add(leftMainPanel);
    }
    
    /**
     * Mengonfigurasi panel kanan (Form Diagnosa dan Resep).
     */
    private void setupRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Form Pemeriksaan"));
        
        // Form Diagnosa
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Data Medis"));
        inputPanel.add(new JLabel("Keluhan/Gejala:")); inputPanel.add(new JScrollPane(txtSymptoms));
        inputPanel.add(new JLabel("Diagnosa:")); inputPanel.add(new JScrollPane(txtDiagnosis));
        inputPanel.add(new JLabel("Tindakan (Ket):")); inputPanel.add(new JScrollPane(txtTreatment));
        inputPanel.add(new JLabel("Pilih Layanan:")); inputPanel.add(comboTreatmentService);

        rightPanel.add(inputPanel, BorderLayout.NORTH);

        // Tabel Resep
        JPanel prescriptionPanel = new JPanel(new BorderLayout());
        prescriptionPanel.setBorder(BorderFactory.createTitledBorder("Resep & Tindakan"));
        
        prescriptionModel = new DefaultTableModel(new Object[]{"Jenis", "Nama", "Qty", "Harga", "Subtotal", "Aturan Pakai"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablePrescription.setModel(prescriptionModel);
        tablePrescription.getTableHeader().setReorderingAllowed(false);
        
        TableColumnModel cmPres = tablePrescription.getColumnModel();
        cmPres.getColumn(0).setPreferredWidth(80);  // Jenis
        cmPres.getColumn(1).setPreferredWidth(120); // Nama
        cmPres.getColumn(2).setPreferredWidth(30);  // Qty
        cmPres.getColumn(3).setPreferredWidth(70);  // Harga
        cmPres.getColumn(4).setPreferredWidth(70);  // Subtotal
        cmPres.getColumn(5).setPreferredWidth(100); // Aturan Pakai
        
        prescriptionPanel.add(new JScrollPane(tablePrescription), BorderLayout.CENTER);

        JPanel prescriptionButtonPanel = new JPanel(new FlowLayout());
        prescriptionButtonPanel.add(btnRemoveSelectedPrescription);
        prescriptionPanel.add(prescriptionButtonPanel, BorderLayout.SOUTH);

        rightPanel.add(prescriptionPanel, BorderLayout.CENTER); 

        // Tombol Aksi Utama
        JPanel mainActionPanel = new JPanel(new FlowLayout());
        mainActionPanel.add(btnAddMedication);
        mainActionPanel.add(btnSaveRecord);
        rightPanel.add(mainActionPanel, BorderLayout.SOUTH); 

        add(rightPanel);
    }

    /**
     * Mengatur layout GridBag untuk menampilkan detail info pasien dengan rapi.
     */
    private void setupPatientInfoLayout() {
        txtInfoAddress.setEditable(false);
        txtInfoAddress.setBackground(new Color(240, 240, 240)); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 
        gbc.anchor = GridBagConstraints.WEST; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Baris data pasien
        gbc.gridx = 0; gbc.gridy = 0; pnlPatientInfo.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; lblInfoName.setFont(new Font("SansSerif", Font.BOLD, 14)); pnlPatientInfo.add(lblInfoName, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; pnlPatientInfo.add(new JLabel("NIK:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; pnlPatientInfo.add(lblInfoNik, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; pnlPatientInfo.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; pnlPatientInfo.add(lblInfoGender, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; pnlPatientInfo.add(new JLabel("Tgl Lahir:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; pnlPatientInfo.add(lblInfoDob, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; pnlPatientInfo.add(new JLabel("No HP:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; pnlPatientInfo.add(lblInfoPhone, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; pnlPatientInfo.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; pnlPatientInfo.add(new JScrollPane(txtInfoAddress), gbc);
        
        // Separator
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; 
        pnlPatientInfo.add(new JSeparator(), gbc); gbc.gridwidth = 1;
        
        // Data Vital
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; 
        JLabel lblVital = new JLabel("Tanda Vital (Saat Pendaftaran)"); 
        lblVital.setFont(new Font("SansSerif", Font.BOLD, 12)); 
        pnlPatientInfo.add(lblVital, gbc); gbc.gridwidth = 1;
        
        gbc.gridx = 0; gbc.gridy = 8; pnlPatientInfo.add(new JLabel("Berat (kg):"), gbc);
        gbc.gridx = 1; gbc.gridy = 8; pnlPatientInfo.add(lblInfoWeight, gbc);
        
        gbc.gridx = 0; gbc.gridy = 9; pnlPatientInfo.add(new JLabel("Tinggi (cm):"), gbc);
        gbc.gridx = 1; gbc.gridy = 9; pnlPatientInfo.add(lblInfoHeight, gbc);
        
        gbc.gridx = 0; gbc.gridy = 10; pnlPatientInfo.add(new JLabel("Tensi:"), gbc);
        gbc.gridx = 1; gbc.gridy = 10; pnlPatientInfo.add(lblInfoBloodPressure, gbc);
        
        // Filler agar layout rata atas
        gbc.gridy = 11; gbc.weighty = 1.0; 
        pnlPatientInfo.add(new JLabel(), gbc);
    }

    // --- NAVIGASI & UPDATE UI ---

    public void showQueueView() { leftCardLayout.show(leftMainPanel, "QUEUE"); }
    public void showPatientInfoView() { leftCardLayout.show(leftMainPanel, "INFO"); }
    
    /**
     * Memperbarui label informasi pasien di panel kiri.
     * Dipanggil saat dokter mulai memeriksa pasien.
     */
    public void setPatientInfo(String name, String nik, String gender, String dob, String phone, String address, String weight, String height, String bp) {
        lblInfoName.setText(name); 
        lblInfoNik.setText(nik);
        lblInfoGender.setText(gender.equals("L") ? "Laki-laki" : "Perempuan");
        lblInfoDob.setText(dob); 
        lblInfoPhone.setText(phone); 
        txtInfoAddress.setText(address);
        lblInfoWeight.setText(weight); 
        lblInfoHeight.setText(height); 
        lblInfoBloodPressure.setText(bp == null ? "-" : bp);
    }
    
    /**
     * Membersihkan form pemeriksaan dan reset tabel resep.
     */
    public void clearForm() {
        txtSymptoms.setText(""); 
        txtDiagnosis.setText(""); 
        txtTreatment.setText("");
        prescriptionModel.setRowCount(0); 
        comboTreatmentService.setSelectedIndex(-1); 
    }

    // --- GETTERS ---

    public JTable getTableWaitingList() { return tableWaitingList; }
    public DefaultTableModel getWaitingListModel() { return waitingListModel; }
    public JButton getBtnStartConsult() { return btnStartConsult; }
    public String getSymptoms() { return txtSymptoms.getText(); }
    public String getDiagnosis() { return txtDiagnosis.getText(); }
    public String getTreatment() { return txtTreatment.getText(); }
    public JComboBox<ServiceFee> getComboTreatmentService() { return comboTreatmentService; }
    public JButton getBtnRemoveSelectedPrescription() { return btnRemoveSelectedPrescription; }
    public JButton getBtnAddMedication() { return btnAddMedication; }
    public DefaultTableModel getPrescriptionModel() { return prescriptionModel; }
    public JButton getBtnSaveRecord() { return btnSaveRecord; }
    public JTextArea getTxtSymptoms() { return txtSymptoms; }
    public JTextArea getTxtDiagnosis() { return txtDiagnosis; }
    public JTextArea getTxtTreatment() { return txtTreatment; }
    public JTable getTablePrescription() { return tablePrescription; }
}