package com.mycompany.aplikasiklinik.controller;

import com.mycompany.aplikasiklinik.model.dao.*;
import com.mycompany.aplikasiklinik.model.entity.*;
import com.mycompany.aplikasiklinik.util.SessionManager;
import com.mycompany.aplikasiklinik.view.DoctorPanel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 * Controller untuk menangani aktivitas Dokter.
 * Termasuk: Melihat antrian, memeriksa pasien, meresepkan obat, input diagnosa,
 * dan menyimpan rekam medis.
 */
public class DoctorController {

    private final DoctorPanel doctorPanel;
    
    // DAOs
    private final AppointmentDAO appointmentDAO;
    private final MedicalRecordDAO medicalRecordDAO;
    private final BillDAO billDAO;
    private final PrescriptionItemDAO prescriptionItemDAO;
    private final AppointmentServiceDAO appointmentServiceDAO;
    private final PatientDAO patientDAO;
    private final MedicationDAO medicationDAO;
    private final ServiceFeeDAO serviceFeeDAO;
    private final UserDAO userDAO;

    private final int doctorId;
    private Appointment selectedAppointment;
    
    // Caches & Temp Lists
    private final List<Appointment> waitingListCache;
    private final List<PrescriptionItem> tempPrescriptionList;
    private final List<AppointmentService> tempServiceList;

    private Timer refreshTimer;
    private static final int REFRESH_INTERVAL_MS = 5000;

    public DoctorController(DoctorPanel doctorPanel, UserDAO userDAO, int doctorId) {
        this.doctorPanel = doctorPanel;
        this.userDAO = userDAO;
        this.doctorId = doctorId;

        // Inisialisasi DAO
        this.appointmentDAO = new AppointmentDAO();
        this.medicalRecordDAO = new MedicalRecordDAO();
        this.billDAO = new BillDAO();
        this.prescriptionItemDAO = new PrescriptionItemDAO();
        this.appointmentServiceDAO = new AppointmentServiceDAO();
        this.patientDAO = new PatientDAO();
        this.medicationDAO = new MedicationDAO();
        this.serviceFeeDAO = new ServiceFeeDAO();

        // Inisialisasi List
        this.waitingListCache = new ArrayList<>();
        this.tempPrescriptionList = new ArrayList<>();
        this.tempServiceList = new ArrayList<>();

        // Setup Awal
        loadTreatmentServiceList();
        loadWaitingList();
        setupActionListeners();
        startAutoRefresh();
        updateBtnStartConsultState();
        setupWindowClosingListener();
    }

    /**
     * Mendaftarkan semua event listener untuk tombol dan interaksi tabel.
     */
    private void setupActionListeners() {
        doctorPanel.getBtnStartConsult().addActionListener(e -> startConsultation());
        doctorPanel.getBtnSaveRecord().addActionListener(e -> saveMedicalRecord());
        doctorPanel.getBtnAddMedication().addActionListener(e -> showAddMedicationDialog());
        doctorPanel.getComboTreatmentService().addActionListener(e -> handleServiceSelection());
        doctorPanel.getBtnRemoveSelectedPrescription().addActionListener(e -> removeSelectedPrescriptionItem());
        doctorPanel.getTableWaitingList().getSelectionModel().addListSelectionListener(this::tableSelectionChanged);
    }
    
    /**
     * Mengatur listener saat window ditutup untuk memastikan status dokter menjadi offline.
     */
    private void setupWindowClosingListener() {
         javax.swing.SwingUtilities.getWindowAncestor(doctorPanel).addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                userDAO.updateUserActiveStatus(doctorId, false);
            }
        });
    }

    /**
     * Memulai timer untuk me-refresh daftar antrian secara otomatis setiap interval tertentu.
     * Hanya berjalan jika tidak ada pasien yang sedang diperiksa.
     */
    private void startAutoRefresh() {
        refreshTimer = new Timer(REFRESH_INTERVAL_MS, e -> {
            if (selectedAppointment == null) {
                loadWaitingList();
            }
        });
        refreshTimer.start();
    }

    public void stopAutoRefresh() {
        if (refreshTimer != null) refreshTimer.stop();
    }

    /**
     * Memuat daftar pasien yang sedang menunggu (Status: WAITING) untuk dokter yang login.
     */
    private void loadWaitingList() {
        DefaultTableModel model = doctorPanel.getWaitingListModel();
        model.setRowCount(0);

        User currentDoctor = SessionManager.getInstance().getCurrentUser();
        if (currentDoctor == null) return;

        waitingListCache.clear();
        waitingListCache.addAll(appointmentDAO.getWaitingListByDoctor(currentDoctor.getId(), LocalDate.now()));
        
        for (Appointment app : waitingListCache) {
            Patient patient = patientDAO.getPatientById(app.getPatientId());
            String patientName = (patient != null) ? patient.getName() : "Pasien Tidak Ditemukan (ID: " + app.getPatientId() + ")";
            model.addRow(new Object[]{
                app.getQueueNumber(),
                patientName,
                app.getStatus()
            });
        }
    }

    /**
     * Memuat daftar layanan/tindakan medis ke dalam ComboBox.
     */
    private void loadTreatmentServiceList() {
        List<ServiceFee> services = serviceFeeDAO.getAllServiceFees();
        DefaultComboBoxModel<ServiceFee> model = new DefaultComboBoxModel<>();
        if (services != null) {
            for (ServiceFee service : services) {
                model.addElement(service);
            }
        }
        doctorPanel.getComboTreatmentService().setModel(model);
        doctorPanel.getComboTreatmentService().setSelectedIndex(-1);
    }

    /**
     * Menangani perubahan seleksi pada tabel antrian.
     * Mengaktifkan/menonaktifkan tombol konsultasi berdasarkan status pasien.
     */
    private void tableSelectionChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() || selectedAppointment != null) {
            // Jika sedang memeriksa pasien, cegah seleksi baris lain
            if (doctorPanel.getTableWaitingList().getSelectedRow() != -1 && selectedAppointment != null) {
                doctorPanel.getTableWaitingList().clearSelection();
                JOptionPane.showMessageDialog(doctorPanel, "Anda sedang memeriksa pasien. Simpan rekam medis terlebih dahulu.");
            }
            return;
        }

        int row = doctorPanel.getTableWaitingList().getSelectedRow();
        if (row >= 0 && row < waitingListCache.size()) {
            Appointment selected = waitingListCache.get(row);
            if (!"IN_CONSULTATION".equals(selected.getStatus())) {
                doctorPanel.getBtnStartConsult().setEnabled(true);
            } else {
                doctorPanel.getTableWaitingList().clearSelection();
                doctorPanel.getBtnStartConsult().setEnabled(false);
            }
        } else {
            doctorPanel.getBtnStartConsult().setEnabled(false);
        }
    }

    /**
     * Memulai sesi konsultasi dengan pasien yang dipilih.
     * Mengubah status janji temu menjadi 'IN_CONSULTATION', mengunci UI antrian,
     * dan menampilkan detail info pasien.
     */
    private void startConsultation() {
        int selectedRow = doctorPanel.getTableWaitingList().getSelectedRow();
        if (selectedRow == -1 || selectedAppointment != null) {
            JOptionPane.showMessageDialog(doctorPanel, "Pilih pasien yang belum diperiksa.");
            return;
        }

        Appointment appToConsult = waitingListCache.get(selectedRow);
        if ("IN_CONSULTATION".equals(appToConsult.getStatus())) {
            JOptionPane.showMessageDialog(doctorPanel, "Pasien ini sedang diperiksa.");
            return;
        }

        if (appointmentDAO.updateStatus(appToConsult.getId(), "IN_CONSULTATION")) {
            appToConsult.setStatus("IN_CONSULTATION");
            selectedAppointment = appToConsult;
            doctorPanel.getBtnStartConsult().setEnabled(false);
            doctorPanel.getTableWaitingList().clearSelection();
            
            // Tampilkan Data Pasien di Panel Informasi
            Patient patient = patientDAO.getPatientById(appToConsult.getPatientId());
            if (patient != null) {
                doctorPanel.setPatientInfo(
                    patient.getName(),
                    patient.getNik(),
                    patient.getGender(),
                    patient.getBirthDate().toString(),
                    patient.getPhoneNumber(),
                    patient.getAddress(),
                    String.valueOf(appToConsult.getWeight()), 
                    String.valueOf(appToConsult.getHeight()), 
                    appToConsult.getBloodPressure() 
                );
                doctorPanel.showPatientInfoView();
            }
            
            JOptionPane.showMessageDialog(doctorPanel, "Mulai pemeriksaan untuk Antrian No: " + appToConsult.getQueueNumber());
        } else {
            JOptionPane.showMessageDialog(doctorPanel, "Gagal memperbarui status pasien.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Menyimpan seluruh data rekam medis ke database.
     * Proses meliputi:
     * 1. Validasi input.
     * 2. Simpan Rekam Medis (Diagnosa, dll).
     * 3. Simpan item resep obat.
     * 4. Simpan item tindakan layanan tambahan.
     * 5. Buat Tagihan (Bill) mencakup biaya dokter, obat, dan layanan.
     * 6. Update status pasien (ke Farmasi atau Kasir).
     */
    private void saveMedicalRecord() {
        if (selectedAppointment == null) {
            JOptionPane.showMessageDialog(doctorPanel, "Mulai konsultasi terlebih dahulu.");
            return;
        }

        String symptoms = doctorPanel.getSymptoms().trim();
        String diagnosis = doctorPanel.getDiagnosis().trim();
        String treatment = doctorPanel.getTreatment().trim();

        // Validasi User Dokter
        User currentDoctor = SessionManager.getInstance().getCurrentUser();
        if (currentDoctor == null || !"DOCTOR".equals(currentDoctor.getRole().name())) {
            JOptionPane.showMessageDialog(doctorPanel, "Sesi dokter tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        double consultationFeeValue = currentDoctor.getConsultationFee();

        if (symptoms.isEmpty() || diagnosis.isEmpty() || treatment.isEmpty()) {
            JOptionPane.showMessageDialog(doctorPanel, "Semua field gejala, diagnosa, dan tindakan harus diisi.");
            return;
        }

        try {
            // 1. Simpan Rekam Medis
            MedicalRecord mr = new MedicalRecord(0, selectedAppointment.getId(), symptoms, diagnosis, treatment, consultationFeeValue);
            int medicalRecordId = medicalRecordDAO.addMedicalRecord(mr);

            if (medicalRecordId > 0) {
                double totalMedCost = 0, totalServCost = 0;

                // 2. Simpan Resep Obat
                for (PrescriptionItem item : tempPrescriptionList) {
                    item.setMedicalRecordId(medicalRecordId);
                    item.setStatus("PENDING");
                    prescriptionItemDAO.addPrescriptionItem(item);
                    totalMedCost += item.getSubTotal();
                }

                // 3. Simpan Layanan Tambahan
                for (AppointmentService service : tempServiceList) {
                    service.setAppointmentId(selectedAppointment.getId());
                    appointmentServiceDAO.addAppointmentService(service); 
                    totalServCost += service.getSubTotal();
                }

                // 4. Buat Tagihan (Bill)
                Bill newBill = new Bill(0, selectedAppointment.getId(), totalMedCost, totalServCost, consultationFeeValue, false);

                if (billDAO.createBill(newBill)) {
                    // 5. Tentukan Status Selanjutnya
                    String nextStatus = (tempPrescriptionList.isEmpty()) ? "IN_CASHIER" : "IN_PHARMACY";

                    appointmentDAO.updateStatus(selectedAppointment.getId(), nextStatus);
                    selectedAppointment = null;

                    String message = "Rekam medis dan tagihan disimpan. Pasien diarahkan ke: " + nextStatus;
                    JOptionPane.showMessageDialog(doctorPanel, message);
                    
                    // Reset UI
                    doctorPanel.clearForm();
                    tempPrescriptionList.clear();
                    tempServiceList.clear();
                    doctorPanel.getComboTreatmentService().setSelectedIndex(-1);
                    doctorPanel.showQueueView();
                    loadWaitingList();
                    updateBtnStartConsultState();
                } else {
                    JOptionPane.showMessageDialog(doctorPanel, "Gagal menyimpan Tagihan.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(doctorPanel, "Gagal menyimpan Rekam Medis.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(doctorPanel, "Error saat menyimpan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Menampilkan dialog untuk memilih dan menambahkan obat ke daftar resep sementara.
     * Memvalidasi stok obat sebelum menambahkan.
     */
    private void showAddMedicationDialog() {
        if (selectedAppointment == null) {
            JOptionPane.showMessageDialog(doctorPanel, "Mulai konsultasi terlebih dahulu.");
            return;
        }

        List<Medication> meds = medicationDAO.getAllMedications();
        JComboBox<Medication> comboMeds = new JComboBox<>(meds.toArray(new Medication[0]));
        JTextField txtQty = new JTextField("1");
        JTextField txtInstructions = new JTextField();
        
        final JComponent[] inputs = new JComponent[]{
            new JLabel("Pilih Obat:"), comboMeds,
            new JLabel("Jumlah:"), txtQty,
            new JLabel("Aturan Pakai:"), txtInstructions
        };
        
        int result = JOptionPane.showConfirmDialog(null, inputs, "Tambah Obat", JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                Medication med = (Medication) comboMeds.getSelectedItem();
                int qty = Integer.parseInt(txtQty.getText());
                String instructions = txtInstructions.getText();

                if (qty <= 0 || qty > med.getStockQuantity()) {
                    JOptionPane.showMessageDialog(doctorPanel, "Jumlah tidak valid atau stok tidak mencukupi.");
                    return;
                }

                PrescriptionItem item = new PrescriptionItem();
                item.setMedicationId(med.getId());
                item.setQuantity(qty);
                item.setInstructions(instructions);
                item.setPriceAtTime(med.getPrice());
                item.setStatus("PENDING"); 

                tempPrescriptionList.add(item);
                doctorPanel.getPrescriptionModel().addRow(new Object[]{"Obat", med.getName(), qty, med.getPrice(), item.getSubTotal(), instructions});
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(doctorPanel, "Jumlah harus angka.");
            }
        }
    }

    /**
     * Menangani pemilihan layanan/tindakan dari ComboBox dan menambahkannya ke daftar sementara.
     */
    private void handleServiceSelection() {
        ServiceFee selectedService = (ServiceFee) doctorPanel.getComboTreatmentService().getSelectedItem();
        
        if (selectedService == null) return; 

        if (selectedAppointment == null) {
            JOptionPane.showMessageDialog(doctorPanel, "Mulai konsultasi terlebih dahulu.");
            doctorPanel.getComboTreatmentService().setSelectedIndex(-1);
            return;
        }

        int qty = 1;
        AppointmentService service = new AppointmentService(selectedAppointment.getId(), selectedService.getId(), qty, selectedService.getPrice());
        tempServiceList.add(service);
        
        doctorPanel.getPrescriptionModel().addRow(new Object[]{"Tind. Layanan", selectedService.getName(), qty, selectedService.getPrice(), service.getSubTotal(), ""}); 
        
        // Reset combo box di thread UI agar tidak memicu event loop
        javax.swing.SwingUtilities.invokeLater(() -> {
            doctorPanel.getComboTreatmentService().setSelectedIndex(-1);
        });
    }

    /**
     * Menghapus item (obat atau layanan) yang dipilih dari tabel resep sementara.
     */
    private void removeSelectedPrescriptionItem() {
        int selectedRow = doctorPanel.getTablePrescription().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(doctorPanel, "Pilih item yang akan dihapus dari tabel.");
            return;
        }

        DefaultTableModel model = doctorPanel.getPrescriptionModel();
        String jenisItem = (String) model.getValueAt(selectedRow, 0);

        if ("Obat".equals(jenisItem)) {
            tempPrescriptionList.remove(selectedRow);
        } else if ("Tind. Layanan".equals(jenisItem)) {
             String namaItem = (String) model.getValueAt(selectedRow, 1);
             int qtyItem = (Integer) model.getValueAt(selectedRow, 2);
             double hargaItem = (Double) model.getValueAt(selectedRow, 3);
             
             // Hapus item dari list layanan berdasarkan kriteria pencocokan
             tempServiceList.removeIf(s -> 
                 s.getQuantity() == qtyItem && 
                 s.getPriceAtTime() == hargaItem && 
                 serviceFeeDAO.getServiceFeeById(s.getServiceFeeId()).getName().equals(namaItem)
             );
        }

        model.removeRow(selectedRow);
    }

    /**
     * Mengupdate status tombol "Mulai Konsultasi" berdasarkan seleksi tabel dan status antrian.
     */
    private void updateBtnStartConsultState() {
        boolean hasSelection = doctorPanel.getTableWaitingList().getSelectedRow() != -1;
        doctorPanel.getBtnStartConsult().setEnabled(selectedAppointment == null && hasSelection);
    }

    public Appointment getSelectedAppointment() {
        return selectedAppointment;
    }
}