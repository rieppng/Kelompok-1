package com.mycompany.aplikasiklinik.controller;

import com.mycompany.aplikasiklinik.model.dao.*;
import com.mycompany.aplikasiklinik.model.entity.*;
import com.mycompany.aplikasiklinik.view.ReceptionistPanel;
import java.awt.Component;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;

/**
 * Controller untuk menangani fitur Resepsionis.
 * Termasuk: Pendaftaran pasien baru/lama, pencarian NIK, 
 * dan manajemen antrian ke dokter.
 */
public class ReceptionistController {
    private final ReceptionistPanel receptionistPanel;
    private final UserDAO userDAO;
    private final PatientDAO patientDAO;
    private final AppointmentDAO appointmentDAO;
    private final MedicalSpecialtyDAO specialtyDAO; 

    private Timer refreshTimer;
    private final int REFRESH_INTERVAL_MS = 6000;

    public ReceptionistController(ReceptionistPanel receptionistPanel) {
        this.receptionistPanel = receptionistPanel;
        
        // Inisialisasi DAO
        this.userDAO = new UserDAO();
        this.patientDAO = new PatientDAO();
        this.appointmentDAO = new AppointmentDAO();
        this.specialtyDAO = new MedicalSpecialtyDAO();

        // Setup Renderer untuk menampilkan nama dokter di ComboBox
        setupDoctorComboBoxRenderer();

        // Load Data Awal
        loadSpecialtiesList(); 
        loadQueueList();

        // Setup Listener
        receptionistPanel.getBtnAddToQueue().addActionListener(e -> handleAddToQueue());
        receptionistPanel.getComboSpecialty().addActionListener(e -> loadActiveDoctorsForSelectedSpecialty());
        receptionistPanel.getBtnRefreshDoctorList().addActionListener(e -> loadActiveDoctorsForSelectedSpecialty());
        receptionistPanel.getBtnSearchNik().addActionListener(e -> handleSearchByNik());
        
        startAutoRefresh();
    }

    private void startAutoRefresh() {
        refreshTimer = new Timer(REFRESH_INTERVAL_MS, e -> loadQueueList());
        refreshTimer.start();
    }

    public void stopAutoRefresh() {
        if (refreshTimer != null) refreshTimer.stop();
    }

    private void setupDoctorComboBoxRenderer() {
        receptionistPanel.getComboActiveDoctor().setRenderer(new DefaultListCellRenderer() { 
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof User) {
                    User doctor = (User) value;
                    setText(doctor.getFullName()); 
                }
                return this;
            }
        });
    }

    /**
     * Memuat daftar spesialisasi medis ke dalam ComboBox.
     */
    private void loadSpecialtiesList() {
        List<MedicalSpecialty> specialties = specialtyDAO.getAllSpecialties();
        DefaultComboBoxModel<MedicalSpecialty> model = new DefaultComboBoxModel<>();
        for (MedicalSpecialty specialty : specialties) {
            model.addElement(specialty);
        }
        receptionistPanel.getComboSpecialty().setModel(model);
        receptionistPanel.getComboActiveDoctor().setModel(new DefaultComboBoxModel<>());
    }

    /**
     * Memuat dokter yang sedang AKTIF (Login) berdasarkan spesialisasi yang dipilih.
     */
    private void loadActiveDoctorsForSelectedSpecialty() {
        MedicalSpecialty selectedSpecialty = (MedicalSpecialty) receptionistPanel.getComboSpecialty().getSelectedItem();
        if (selectedSpecialty == null) {
            receptionistPanel.getComboActiveDoctor().setModel(new DefaultComboBoxModel<>());
            return;
        }

        List<User> activeDoctorsInSpecialty = userDAO.getActiveDoctorsBySpecialtyId(selectedSpecialty.getId());
        DefaultComboBoxModel<User> model = new DefaultComboBoxModel<>();
        for (User doctor : activeDoctorsInSpecialty) {
            model.addElement(doctor);
        }
        receptionistPanel.getComboActiveDoctor().setModel(model);
    }

    /**
     * Memuat daftar antrian hari ini ke tabel.
     */
    private void loadQueueList() {
        List<Appointment> waitingList = appointmentDAO.getAppointmentsByDate(LocalDate.now());
        receptionistPanel.getQueueModel().setRowCount(0);

        for (Appointment app : waitingList) {
            Patient patient = patientDAO.getPatientById(app.getPatientId());
            User doctor = userDAO.getUserById(app.getDoctorId());

            String patientName = (patient != null) ? patient.getName() : "Pasien Error";
            String doctorName = (doctor != null) ? doctor.getFullName() : "Dokter Error"; 

            receptionistPanel.getQueueModel().addRow(new Object[]{
                app.getQueueNumber(),
                patientName,
                doctorName,
                app.getStatus()
            });
        }
    }
    
    /**
     * Mencari data pasien berdasarkan NIK.
     * Jika ditemukan, form akan terisi otomatis.
     */
    private void handleSearchByNik() {
        String nik = receptionistPanel.getNik().trim();
        if (nik.isEmpty()) {
            JOptionPane.showMessageDialog(receptionistPanel, "Masukkan NIK terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Patient existingPatient = patientDAO.getPatientByNik(nik);
        
        if (existingPatient != null) {
            receptionistPanel.getTxtName().setText(existingPatient.getName());
            receptionistPanel.getTxtAddress().setText(existingPatient.getAddress());
            receptionistPanel.getTxtPhone().setText(existingPatient.getPhoneNumber());
            receptionistPanel.setGender(existingPatient.getGender());
            receptionistPanel.getDateChooserDob().setDate(java.sql.Date.valueOf(existingPatient.getBirthDate()));
            
            receptionistPanel.setExistingPatientId(existingPatient.getId());
            
            JOptionPane.showMessageDialog(receptionistPanel, "Data Pasien Lama Ditemukan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } else {
            receptionistPanel.setExistingPatientId(0); 
            JOptionPane.showMessageDialog(receptionistPanel, "Data Pasien tidak ditemukan. Silakan isi form manual.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Menambahkan pasien ke antrian.
     * 1. Validasi input form.
     * 2. Simpan data pasien (jika baru) atau gunakan ID lama.
     * 3. Buat Appointment baru dengan status WAITING.
     */
    private void handleAddToQueue() {
        String doctorIdStr = receptionistPanel.getSelectedDoctorId();
        if (doctorIdStr == null) {
             JOptionPane.showMessageDialog(receptionistPanel, "Pilih dokter aktif dari daftar!", "Peringatan", JOptionPane.WARNING_MESSAGE);
             return;
        }

        String name = receptionistPanel.getPatientName().trim();
        String nik = receptionistPanel.getNik().trim();
        LocalDate birthDate = receptionistPanel.getDob();
        String phone = receptionistPanel.getPhone().trim();
        String address = receptionistPanel.getAddress().trim();
        String weightStr = receptionistPanel.getWeight().trim();
        String heightStr = receptionistPanel.getPatientHeight().trim();
        String bloodPressure = receptionistPanel.getBloodPressure().trim();

        int doctorId = Integer.parseInt(doctorIdStr);

        if (name.isEmpty() || nik.isEmpty() || phone.isEmpty() || address.isEmpty() ||
            weightStr.isEmpty() || heightStr.isEmpty() || birthDate == null) {
            JOptionPane.showMessageDialog(receptionistPanel, "Semua kolom wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double weight = Double.parseDouble(weightStr);
            double height = Double.parseDouble(heightStr);

            Patient newPatient = new Patient(0, name, nik, birthDate, "L", address, phone);
            int newPatientId = patientDAO.addPatient(newPatient);

            if (newPatientId > 0) {
                Appointment newAppt = new Appointment();
                newAppt.setPatientId(newPatientId);
                newAppt.setDoctorId(doctorId);
                newAppt.setDate(LocalDate.now());
                newAppt.setWeight(weight);
                newAppt.setHeight(height);
                newAppt.setBloodPressure(bloodPressure);
                newAppt.setStatus("WAITING");

                int queueNumber = appointmentDAO.getNextQueueNumber(doctorId, LocalDate.now());
                newAppt.setQueueNumber(queueNumber);

                if (appointmentDAO.addAppointment(newAppt)) {
                    JOptionPane.showMessageDialog(receptionistPanel, "Pasien " + name + " terdaftar! No. Antrian: " + queueNumber);
                    receptionistPanel.clearForm();
                    loadQueueList();
                } else {
                    JOptionPane.showMessageDialog(receptionistPanel, "Gagal menambahkan antrian ke database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(receptionistPanel, "Gagal menyimpan data pasien ke database.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(receptionistPanel, "Berat dan Tinggi harus berupa angka!", "Error Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(receptionistPanel, "Gagal menambahkan pasien/antrian: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}