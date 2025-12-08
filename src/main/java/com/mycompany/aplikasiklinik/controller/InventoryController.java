package com.mycompany.aplikasiklinik.controller;

import com.mycompany.aplikasiklinik.model.dao.*;
import com.mycompany.aplikasiklinik.model.entity.*;
import com.mycompany.aplikasiklinik.view.InventoryPanel;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 * Controller untuk menangani inventaris Apotek (Obat) dan Pemrosesan Resep.
 * Mengelola CRUD obat dan validasi resep masuk dari dokter.
 */
public class InventoryController {
    private final InventoryPanel inventoryPanel;
    private final MedicationDAO medicationDAO;
    private final PrescriptionItemDAO prescriptionItemDAO;
    private final AppointmentDAO appointmentDAO;
    private final PatientDAO patientDAO;
    
    private Medication selectedMedication; 
    private Timer refreshTimer;
    private final int REFRESH_INTERVAL_MS = 5000; 

    public InventoryController(InventoryPanel inventoryPanel) {
        this.inventoryPanel = inventoryPanel;
        
        // Inisialisasi DAO
        this.medicationDAO = new MedicationDAO(); 
        this.prescriptionItemDAO = new PrescriptionItemDAO();
        this.appointmentDAO = new AppointmentDAO();
        this.patientDAO = new PatientDAO();

        // Load Data & Setup
        loadMedicationTable();
        loadIncomingPrescriptionTable(); 
        startAutoRefresh();
        
        // Action Listeners
        this.inventoryPanel.getBtnAddMed().addActionListener(e -> addMedication());
        this.inventoryPanel.getBtnUpdateMed().addActionListener(e -> updateMedication());
        this.inventoryPanel.getBtnDeleteMed().addActionListener(e -> deleteMedication());
        this.inventoryPanel.getBtnProcessPrescription().addActionListener(e -> processIncomingPrescriptions());
        this.inventoryPanel.getTableInventory().getSelectionModel().addListSelectionListener(this::tableSelectionChanged);
    }

    private void startAutoRefresh() {
        refreshTimer = new Timer(REFRESH_INTERVAL_MS, e -> loadIncomingPrescriptionTable());
        refreshTimer.start();
    }
    
    public void stopAutoRefresh() {
        if (refreshTimer != null) refreshTimer.stop();
    }

    /**
     * Memuat daftar resep yang masuk (Status: PENDING) ke dalam tabel.
     * Mengambil juga nama pasien terkait untuk ditampilkan.
     */
    public void loadIncomingPrescriptionTable() {
        DefaultTableModel model = inventoryPanel.getIncomingPrescriptionModel();
        model.setRowCount(0);

        List<PrescriptionItem> pendingItems = prescriptionItemDAO.getPendingPrescriptions();

        if (pendingItems != null && !pendingItems.isEmpty()) {
            for (PrescriptionItem item : pendingItems) {
                // Ambil Nama Obat
                Medication med = medicationDAO.getMedicationById(item.getMedicationId());
                String medicationName = (med != null) ? med.getName() : "Obat Tidak Ditemukan";

                // Ambil Nama Pasien melalui rantai relasi (Item -> MR -> Appt -> Patient)
                String patientName = "Pasien Tidak Dikenal";
                int appointmentId = prescriptionItemDAO.getAppointmentIdByMedicalRecordId(item.getMedicalRecordId());
                if (appointmentId != -1) {
                    Appointment app = appointmentDAO.getAppointmentById(appointmentId);
                    if (app != null) {
                        Patient patient = patientDAO.getPatientById(app.getPatientId());
                        if (patient != null) {
                            patientName = patient.getName();
                        }
                    }
                }

                model.addRow(new Object[]{
                    item.getId(),
                    medicationName, 
                    item.getQuantity(), 
                    item.getInstructions(), 
                    patientName, 
                    item.getMedicalRecordId() 
                });
            }
        }
    }

    /**
     * Memproses resep yang dipilih.
     * 1. Memvalidasi ketersediaan stok obat di gudang.
     * 2. Mengurangi stok jika cukup.
     * 3. Mengubah status item resep menjadi PROCESSED.
     * 4. Jika semua item selesai, update status appointment ke Kasir (IN_CASHIER).
     */
    private void processIncomingPrescriptions() {
        DefaultTableModel incomingModel = inventoryPanel.getIncomingPrescriptionModel();
        if (incomingModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(inventoryPanel, "Tidak ada resep yang perlu diproses.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int medicalRecordId = (int) incomingModel.getValueAt(0, 5); 
        String patientName = (String) incomingModel.getValueAt(0, 4); 

        List<PrescriptionItem> itemsToProcess = prescriptionItemDAO.getPrescriptionItemsByMedicalRecordId(medicalRecordId);

        if (itemsToProcess == null || itemsToProcess.isEmpty()) {
             JOptionPane.showMessageDialog(inventoryPanel, "Error: Tidak ditemukan item resep.", "Error", JOptionPane.ERROR_MESSAGE);
             return;
        }

        int confirm = JOptionPane.showConfirmDialog(inventoryPanel,
                "Proses " + itemsToProcess.size() + " item resep untuk Pasien: " + patientName + "?",
                "Konfirmasi Apoteker", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean allSuccess = true;
            StringBuilder errorLog = new StringBuilder();

            for (PrescriptionItem item : itemsToProcess) {
                if (!"PENDING".equals(item.getStatus())) continue;

                Medication med = medicationDAO.getMedicationById(item.getMedicationId());

                // Validasi Stok
                if (med == null) {
                    errorLog.append("Obat ID ").append(item.getMedicationId()).append(" tidak ditemukan.\n");
                    allSuccess = false;
                    continue;
                }
                if (med.getStockQuantity() < item.getQuantity()) {
                    errorLog.append("Stok '").append(med.getName()).append("' kurang.\n");
                    allSuccess = false;
                    continue;
                }

                // Update Stok dan Status
                boolean stockUpdated = medicationDAO.updateStock(item.getMedicationId(), item.getQuantity());
                if (!stockUpdated) {
                    errorLog.append("Gagal kurangi stok '").append(med.getName()).append("'.\n");
                    allSuccess = false;
                    continue;
                }
                
                boolean statusUpdated = prescriptionItemDAO.updateStatus(item.getId(), "PROCESSED");
                if (!statusUpdated) {
                    errorLog.append("Gagal update status item ID ").append(item.getId()).append(".\n");
                    allSuccess = false;
                }
            }

            if (allSuccess) {
                // Update status alur pasien ke kasir
                int appointmentId = prescriptionItemDAO.getAppointmentIdByMedicalRecordId(medicalRecordId);
                if (appointmentId != -1) {
                    appointmentDAO.updateStatus(appointmentId, "IN_CASHIER");
                }
                JOptionPane.showMessageDialog(inventoryPanel, "Resep untuk " + patientName + " selesai diproses.");
            } else {
                JOptionPane.showMessageDialog(inventoryPanel, "Sebagian item gagal diproses:\n" + errorLog.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            loadMedicationTable();
            loadIncomingPrescriptionTable();
        }
    }

    /**
     * Memuat daftar obat inventaris ke tabel.
     */
    private void loadMedicationTable() {
        DefaultTableModel model = inventoryPanel.getInventoryModel();
        model.setRowCount(0);
        List<Medication> meds = medicationDAO.getAllMedications();
        if (meds != null) { 
            for (Medication med : meds) {
                model.addRow(new Object[]{ med.getId(), med.getName(), med.getCategory(), med.getStockQuantity(), med.getPrice() });
            }
        }
    }

    /**
     * Menangani seleksi tabel inventaris obat untuk keperluan Update/Delete.
     */
    private void tableSelectionChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && inventoryPanel.getTableInventory().getSelectedRow() != -1) {
            int row = inventoryPanel.getTableInventory().getSelectedRow();
            DefaultTableModel model = inventoryPanel.getInventoryModel();
            
            selectedMedication = new Medication(
                    (int) model.getValueAt(row, 0),
                    (String) model.getValueAt(row, 1),
                    (String) model.getValueAt(row, 2),
                    (int) model.getValueAt(row, 3),
                    (double) model.getValueAt(row, 4)
            );
            
            inventoryPanel.getTxtMedName().setText(selectedMedication.getName());
            inventoryPanel.getComboCategory().setSelectedItem(selectedMedication.getCategory());
            inventoryPanel.getSpinStock().setValue(selectedMedication.getStockQuantity());
            inventoryPanel.getTxtPrice().setText(String.valueOf(selectedMedication.getPrice()));

            inventoryPanel.getBtnUpdateMed().setEnabled(true);
            inventoryPanel.getBtnDeleteMed().setEnabled(true);
        } else if (!e.getValueIsAdjusting() && inventoryPanel.getTableInventory().getSelectedRow() == -1) {
            inventoryPanel.getBtnUpdateMed().setEnabled(false);
            inventoryPanel.getBtnDeleteMed().setEnabled(false);
        }
    }

    /**
     * Menambahkan obat baru ke database.
     */
    private void addMedication() {
        try {
            String medName = inventoryPanel.getMedName();
            String category = inventoryPanel.getCategory();
            int stock = inventoryPanel.getStock();
            double price = Double.parseDouble(inventoryPanel.getPrice());

            if (medName.isEmpty() || price <= 0) {
                JOptionPane.showMessageDialog(inventoryPanel, "Nama dan Harga harus valid!", "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Medication newMed = new Medication(0, medName, category, stock, price);
            if (medicationDAO.addMedication(newMed)) {
                JOptionPane.showMessageDialog(inventoryPanel, "Obat berhasil ditambahkan!");
                inventoryPanel.clearForm();
                loadMedicationTable();
            } else {
                JOptionPane.showMessageDialog(inventoryPanel, "Gagal menambahkan obat.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(inventoryPanel, "Harga harus angka!", "Validasi", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Memperbarui data obat yang dipilih.
     */
    private void updateMedication() {
        if (selectedMedication == null) return;
        try {
            Medication updatedMed = new Medication(
                selectedMedication.getId(), 
                inventoryPanel.getMedName(), 
                inventoryPanel.getCategory(), 
                inventoryPanel.getStock(), 
                Double.parseDouble(inventoryPanel.getPrice())
            );

            if (medicationDAO.updateMedication(updatedMed)) {
                JOptionPane.showMessageDialog(inventoryPanel, "Data obat diupdate!");
                inventoryPanel.clearForm();
                loadMedicationTable();
                selectedMedication = null;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(inventoryPanel, "Harga harus angka!", "Validasi", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Menghapus obat yang dipilih dari database.
     */
    private void deleteMedication() {
        if (selectedMedication == null) return;
        int confirm = JOptionPane.showConfirmDialog(inventoryPanel, "Hapus obat " + selectedMedication.getName() + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (medicationDAO.deleteMedication(selectedMedication.getId())) {
                JOptionPane.showMessageDialog(inventoryPanel, "Obat dihapus!");
                inventoryPanel.clearForm();
                loadMedicationTable();
                selectedMedication = null;
            }
        }
    }
}