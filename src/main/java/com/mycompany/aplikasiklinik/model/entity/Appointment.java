package com.mycompany.aplikasiklinik.model.entity;

import java.time.LocalDate;

/**
 * Entitas yang merepresentasikan data antrian (appointment) pasien di klinik.
 * Setiap appointment mencakup informasi pasien, dokter, tanggal, nomor antrian,
 * tanda vital (berat, tinggi, tekanan darah), status, dan referensi ke biaya konsultasi.
 */
public class Appointment {

    private int id;
    private int patientId;    // Foreign key ke tabel patients
    private int doctorId;     // Foreign key ke tabel users (role = DOCTOR)
    private LocalDate date;
    private int queueNumber;  // Nomor urut antrian pada hari tersebut

    // Data tanda vital yang diisi saat pendaftaran oleh resepsionis
    private double weight;
    private double height;
    private String bloodPressure; // Bisa null atau berisi nilai seperti "120/80"

    // Status alur pemeriksaan: WAITING, IN_CONSULTATION, COMPLETED, CANCELLED, dll.
    private String status;

    // Mengacu ke entitas ServiceFee untuk biaya konsultasi (konsistensi historis harga)
    private int consultationServiceFeeId;

    /**
     * Konstruktor default (tanpa parameter).
     */
    public Appointment() {}

    /**
     * Konstruktor untuk membuat objek Appointment dengan data inti (tanpa tekanan darah).
     *
     * @param id ID unik antrian.
     * @param patientId ID pasien.
     * @param doctorId ID dokter.
     * @param date tanggal kunjungan.
     * @param queueNumber nomor antrian.
     * @param weight berat badan pasien (kg).
     * @param height tinggi badan pasien (cm).
     * @param status status alur pemeriksaan.
     */
    public Appointment(int id, int patientId, int doctorId, LocalDate date, int queueNumber, double weight, double height, String status) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.queueNumber = queueNumber;
        this.weight = weight;
        this.height = height;
        this.status = status;
    }

    // --- Getter dan Setter ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getQueueNumber() {
        return queueNumber;
    }

    public void setQueueNumber(int queueNumber) {
        this.queueNumber = queueNumber;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getConsultationServiceFeeId() {
        return consultationServiceFeeId;
    }

    public void setConsultationServiceFeeId(int consultationServiceFeeId) {
        this.consultationServiceFeeId = consultationServiceFeeId;
    }
}