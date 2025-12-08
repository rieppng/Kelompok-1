package com.mycompany.aplikasiklinik.view;

import com.mycompany.aplikasiklinik.model.entity.MedicalSpecialty;
import com.mycompany.aplikasiklinik.model.entity.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import com.toedter.calendar.JDateChooser;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Class ReceptionistPanel
 * * Panel untuk Resepsionis.
 * Fitur: Pendaftaran Pasien (Baru/Lama), Pencarian NIK, Pemilihan Dokter, dan Melihat Antrian.
 */
public class ReceptionistPanel extends JPanel {

    // --- Komponen Pemilihan Dokter (Atas) ---
    private final JComboBox<MedicalSpecialty> comboSpecialty = new JComboBox<>();
    private final JComboBox<User> comboActiveDoctor = new JComboBox<>(); 
    private final JButton btnRefreshDoctorList = new JButton("Refresh Dokter");

    // --- Komponen Form Pendaftaran (Kiri) ---
    private final JTextField txtName = new JTextField(15);
    private final JTextField txtNik = new JTextField(15);
    private final JButton btnSearchNik = new JButton("Cari");
    private final JDateChooser dateChooserDob = new JDateChooser();
    private final JRadioButton rbMale = new JRadioButton("Laki-laki");
    private final JRadioButton rbFemale = new JRadioButton("Perempuan");
    private final ButtonGroup genderGroup = new ButtonGroup();
    private final JTextArea txtAddress = new JTextArea(3, 20); 
    private final JTextField txtPhone = new JTextField(15);
    private final JTextField txtWeight = new JTextField(5);
    private final JTextField txtHeight = new JTextField(5);
    private final JTextField txtBloodPressure = new JTextField(10); 
    private final JButton btnAddToQueue = new JButton("Daftarkan & Masuk Antrian");
    private final JLabel lblExistingPatientId = new JLabel("0"); 

    // --- Komponen Tabel Antrian (Kanan) ---
    private final JTable tableQueue = new JTable();
    private DefaultTableModel queueTableModel;

    /**
     * Konstruktor ReceptionistPanel.
     * Menyusun layout kompleks: Header (Pilih Dokter), Kiri (Form), Kanan (Tabel).
     */
    public ReceptionistPanel() {
        setLayout(new BorderLayout());

        // --- 1. PANEL ATAS: Pilih Poli & Dokter ---
        JPanel topSelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
        topSelectionPanel.setBorder(BorderFactory.createTitledBorder("Pilih Poli & Dokter"));
        topSelectionPanel.add(new JLabel("Poli:")); 
        topSelectionPanel.add(comboSpecialty);
        topSelectionPanel.add(new JLabel("Dokter:")); 
        topSelectionPanel.add(comboActiveDoctor);
        topSelectionPanel.add(btnRefreshDoctorList);
        
        add(topSelectionPanel, BorderLayout.NORTH);

        // --- 2. PANEL TENGAH: Split Pane (Form vs Tabel) ---
        JSplitPane centerSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        centerSplitPane.setDividerLocation(0.5); 
        centerSplitPane.setResizeWeight(0.5); 

        // --- 2a. FORM PANEL (Kiri) ---
        JPanel formPanel = new JPanel(new BorderLayout()); 
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Pendaftaran Pasien"));
        JPanel inputFieldsPanel = new JPanel(new GridLayout(0, 2, 5, 5)); 
        inputFieldsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 

        // Sub-panel untuk NIK dan Tombol Cari
        JPanel nikPanel = new JPanel(new BorderLayout(5, 0));
        nikPanel.add(txtNik, BorderLayout.CENTER); 
        nikPanel.add(btnSearchNik, BorderLayout.EAST);
        
        inputFieldsPanel.add(new JLabel("NIK/ID:")); inputFieldsPanel.add(nikPanel);
        inputFieldsPanel.add(new JLabel("Nama Pasien:")); inputFieldsPanel.add(txtName);
        
        // Radio Button Gender
        genderGroup.add(rbMale); genderGroup.add(rbFemale);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        genderPanel.add(rbMale); genderPanel.add(rbFemale);
        inputFieldsPanel.add(new JLabel("Jenis Kelamin:")); inputFieldsPanel.add(genderPanel);
        
        inputFieldsPanel.add(new JLabel("Tanggal Lahir:")); inputFieldsPanel.add(dateChooserDob);
        inputFieldsPanel.add(new JLabel("Alamat:")); inputFieldsPanel.add(new JScrollPane(txtAddress)); 
        inputFieldsPanel.add(new JLabel("No HP:")); inputFieldsPanel.add(txtPhone);
        inputFieldsPanel.add(new JLabel("Berat (kg):")); inputFieldsPanel.add(txtWeight);
        inputFieldsPanel.add(new JLabel("Tinggi (cm):")); inputFieldsPanel.add(txtHeight);
        inputFieldsPanel.add(new JLabel("Tekanan Darah:")); inputFieldsPanel.add(txtBloodPressure);
        
        lblExistingPatientId.setVisible(false); // Hidden field ID
        inputFieldsPanel.add(lblExistingPatientId); 

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        buttonPanel.add(btnAddToQueue);
        
        formPanel.add(inputFieldsPanel, BorderLayout.CENTER); 
        formPanel.add(buttonPanel, BorderLayout.SOUTH); 

        // --- 2b. TABEL PANEL (Kanan) ---
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Daftar Antrian"));

        queueTableModel = new DefaultTableModel(new Object[]{"No", "Nama Pasien", "Dokter", "Status"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableQueue.setModel(queueTableModel);
        tableQueue.getTableHeader().setReorderingAllowed(false);
        
        TableColumnModel cm = tableQueue.getColumnModel();
        cm.getColumn(0).setPreferredWidth(40);  // No
        cm.getColumn(0).setMaxWidth(60);
        cm.getColumn(1).setPreferredWidth(150); // Nama Pasien
        cm.getColumn(2).setPreferredWidth(120); // Dokter
        cm.getColumn(3).setPreferredWidth(80);  // Status

        tablePanel.add(new JScrollPane(tableQueue), BorderLayout.CENTER); 

        // Add to Split Pane
        centerSplitPane.setLeftComponent(formPanel);
        centerSplitPane.setRightComponent(tablePanel);
        
        add(centerSplitPane, BorderLayout.CENTER);
    }
    
    /**
     * Membersihkan seluruh field pada form pendaftaran.
     */
    public void clearForm() {
        txtName.setText(""); 
        txtNik.setText(""); 
        genderGroup.clearSelection(); 
        dateChooserDob.setDate(null); 
        txtAddress.setText(""); 
        txtPhone.setText("");
        txtWeight.setText(""); 
        txtHeight.setText(""); 
        txtBloodPressure.setText("");
        lblExistingPatientId.setText("0"); 
        comboActiveDoctor.setSelectedIndex(-1);
    }

    // --- GETTERS & SETTERS ---
    
    public JComboBox<MedicalSpecialty> getComboSpecialty() { return comboSpecialty; }
    public JComboBox<User> getComboActiveDoctor() { return comboActiveDoctor; }
    public JButton getBtnRefreshDoctorList() { return btnRefreshDoctorList; }
    public JButton getBtnSearchNik() { return btnSearchNik; }
    public JButton getBtnAddToQueue() { return btnAddToQueue; }
    public DefaultTableModel getQueueModel() { return queueTableModel; }
    
    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtNik() { return txtNik; }
    public JDateChooser getDateChooserDob() { return dateChooserDob; }
    public JTextArea getTxtAddress() { return txtAddress; }
    public JTextField getTxtPhone() { return txtPhone; }
    public JTextField getTxtWeight() { return txtWeight; }
    public JTextField getTxtHeight() { return txtHeight; }
    public JTextField getTxtBloodPressure() { return txtBloodPressure; }
    
    public String getPatientName() { return txtName.getText(); }
    public String getNik() { return txtNik.getText(); }
    public String getAddress() { return txtAddress.getText(); }
    public String getPhone() { return txtPhone.getText(); }
    public String getWeight() { return txtWeight.getText(); }
    public String getPatientHeight() { return txtHeight.getText(); }
    public String getBloodPressure() { return txtBloodPressure.getText(); }

    /** Mengambil tanggal lahir yang dipilih dan mengkonversinya ke LocalDate. */
    public LocalDate getDob() {
        Date selectedDate = dateChooserDob.getDate();
        if (selectedDate == null) { return null; }
        return selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
    public String getSelectedGender() {
        if (rbMale.isSelected()) return "L";
        if (rbFemale.isSelected()) return "P";
        return null; 
    }
    
    public void setGender(String gender) {
        if ("L".equalsIgnoreCase(gender)) rbMale.setSelected(true);
        else if ("P".equalsIgnoreCase(gender)) rbFemale.setSelected(true);
        else genderGroup.clearSelection();
    }
    
    public String getSelectedDoctorId() {
        User selectedDoctor = (User) comboActiveDoctor.getSelectedItem();
        if (selectedDoctor != null) return String.valueOf(selectedDoctor.getId());
        return null;
    }

    public int getExistingPatientId() {
        try { return Integer.parseInt(lblExistingPatientId.getText()); } 
        catch (NumberFormatException e) { return 0; }
    }
    
    public void setExistingPatientId(int id) { 
        lblExistingPatientId.setText(String.valueOf(id)); 
    }
}