package com.mycompany.aplikasiklinik.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

/**
 * Class InventoryPanel
 * * Panel untuk bagian Farmasi/Gudang.
 * Memiliki fitur CRUD Obat dan memproses Resep Masuk dari Dokter.
 */
public class InventoryPanel extends JPanel {
    
    // Komponen Input
    private final JTextField txtMedName = new JTextField();
    private final JComboBox<String> comboCategory = new JComboBox<>(new String[]{"Tablet", "Sirup", "Kapsul", "Salep", "Injeksi"});
    private final JSpinner spinStock = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
    private final JTextField txtPrice = new JTextField();
    
    // Tombol Aksi
    private final JButton btnAddMed = new JButton("Tambah Obat");
    private final JButton btnUpdateMed = new JButton("Update Obat");
    private final JButton btnDeleteMed = new JButton("Hapus Obat");
    private final JButton btnProcessPrescription = new JButton("Proses Resep Masuk");

    // Tabel Inventaris
    private final JTable tableInventory = new JTable();
    private DefaultTableModel inventoryModel;
    
    // Tabel Resep Masuk
    private final JTable tableIncomingPrescription = new JTable();
    private DefaultTableModel incomingPrescriptionModel;
    
    /**
     * Konstruktor InventoryPanel.
     * Mengatur SplitPane untuk membagi layar menjadi Manajemen Obat (Atas) 
     * dan Antrian Resep (Bawah).
     */
    public InventoryPanel() {
        setLayout(new BorderLayout());
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.5); 
        
        // --- BAGIAN ATAS: Form & Tabel Inventaris ---
        JPanel topPanel = new JPanel(new BorderLayout());
        
        // 1. Form Input
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Form Data Obat"));
        inputPanel.add(new JLabel("Nama Obat:")); inputPanel.add(txtMedName);
        inputPanel.add(new JLabel("Kategori:")); inputPanel.add(comboCategory);
        inputPanel.add(new JLabel("Stok:")); inputPanel.add(spinStock);
        inputPanel.add(new JLabel("Harga:")); inputPanel.add(txtPrice);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAddMed); 
        buttonPanel.add(btnUpdateMed); 
        buttonPanel.add(btnDeleteMed);
        inputPanel.add(new JLabel("Aksi:")); inputPanel.add(buttonPanel); 
        
        topPanel.add(inputPanel, BorderLayout.NORTH);
        
        // 2. Tabel Inventaris
        JPanel inventoryTablePanel = new JPanel(new BorderLayout());
        inventoryTablePanel.setBorder(BorderFactory.createTitledBorder("Daftar Inventaris Obat"));
        
        inventoryModel = new DefaultTableModel(new Object[]{"ID", "Nama", "Kategori", "Stok", "Harga"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableInventory.setModel(inventoryModel);
        tableInventory.getTableHeader().setReorderingAllowed(false);
        setupInventoryTableColumns();
        
        inventoryTablePanel.add(new JScrollPane(tableInventory), BorderLayout.CENTER); 
        topPanel.add(inventoryTablePanel, BorderLayout.CENTER);
        
        // --- BAGIAN BAWAH: Tabel Resep Masuk ---
        JPanel incomingPrescriptionPanel = new JPanel(new BorderLayout());
        incomingPrescriptionPanel.setBorder(BorderFactory.createTitledBorder("Antrian Resep Masuk (Menunggu Proses)"));
        
        incomingPrescriptionModel = new DefaultTableModel(new Object[]{"ID Item", "Nama Obat", "Jumlah", "Aturan Pakai", "Nama Pasien", "ID Rekam Medis"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableIncomingPrescription.setModel(incomingPrescriptionModel);
        tableIncomingPrescription.getTableHeader().setReorderingAllowed(false);
        setupIncomingTableColumns();
        
        incomingPrescriptionPanel.add(new JScrollPane(tableIncomingPrescription), BorderLayout.CENTER);
        
        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomButtonPanel.add(btnProcessPrescription);
        incomingPrescriptionPanel.add(bottomButtonPanel, BorderLayout.SOUTH);
        
        // Finalisasi Layout
        splitPane.setTopComponent(topPanel);
        splitPane.setBottomComponent(incomingPrescriptionPanel);
        add(splitPane, BorderLayout.CENTER);

        // Status Awal Tombol
        btnUpdateMed.setEnabled(false); 
        btnDeleteMed.setEnabled(false);
    }

    /**
     * Mengatur lebar kolom tabel inventaris agar proporsional.
     */
    private void setupInventoryTableColumns() {
        TableColumnModel cmInv = tableInventory.getColumnModel();
        cmInv.getColumn(0).setPreferredWidth(30); // ID
        cmInv.getColumn(0).setMaxWidth(50);
        cmInv.getColumn(1).setPreferredWidth(200); // Nama
        cmInv.getColumn(2).setPreferredWidth(100); // Kategori
        cmInv.getColumn(3).setPreferredWidth(50); // Stok
        cmInv.getColumn(4).setPreferredWidth(80); // Harga
    }

    /**
     * Mengatur lebar kolom tabel resep masuk.
     * Kolom ID Rekam Medis disembunyikan (lebar 0) karena hanya untuk referensi logika.
     */
    private void setupIncomingTableColumns() {
        TableColumnModel cmInc = tableIncomingPrescription.getColumnModel();
        cmInc.getColumn(0).setPreferredWidth(50); // ID Item
        cmInc.getColumn(1).setPreferredWidth(150); // Nama Obat
        cmInc.getColumn(2).setPreferredWidth(50); // Jumlah
        cmInc.getColumn(3).setPreferredWidth(200); // Aturan Pakai
        cmInc.getColumn(4).setPreferredWidth(150); // Nama Pasien
        
        // Sembunyikan ID Rekam Medis
        cmInc.getColumn(5).setPreferredWidth(0); 
        cmInc.getColumn(5).setMinWidth(0);
        cmInc.getColumn(5).setMaxWidth(0);
    }
    
    /**
     * Membersihkan form input dan mereset status tombol update/delete.
     */
    public void clearForm() {
        txtMedName.setText(""); 
        comboCategory.setSelectedIndex(0);
        spinStock.setValue(0); 
        txtPrice.setText("");
        tableInventory.clearSelection(); 
        btnUpdateMed.setEnabled(false); 
        btnDeleteMed.setEnabled(false);
    }

    // --- GETTERS ---
    
    public String getMedName() { return txtMedName.getText(); }
    public String getCategory() { return (String) comboCategory.getSelectedItem(); }
    public int getStock() { return (int) spinStock.getValue(); }
    public String getPrice() { return txtPrice.getText(); }
    
    public JTextField getTxtMedName() { return txtMedName; }
    public JComboBox<String> getComboCategory() { return comboCategory; }
    public JSpinner getSpinStock() { return spinStock; }
    public JTextField getTxtPrice() { return txtPrice; }
    
    public JButton getBtnAddMed() { return btnAddMed; }
    public JButton getBtnUpdateMed() { return btnUpdateMed; }
    public JButton getBtnDeleteMed() { return btnDeleteMed; }
    public JButton getBtnProcessPrescription() { return btnProcessPrescription; }
    
    public JTable getTableInventory() { return tableInventory; }
    public DefaultTableModel getInventoryModel() { return inventoryModel; }
    public JTable getTableIncomingPrescription() { return tableIncomingPrescription; }
    public DefaultTableModel getIncomingPrescriptionModel() { return incomingPrescriptionModel; }
}