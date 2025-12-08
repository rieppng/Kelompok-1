package com.mycompany.aplikasiklinik.model.entity;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) yang digunakan untuk menampilkan riwayat rekam medis
 * secara lengkap di panel admin, termasuk informasi pasien, dokter, poli, dan diagnosis.
 * Objek ini tidak memiliki setter karena hanya digunakan untuk menampilkan data.
 */
public class MedicalRecordHistory {

    private final int id;          // ID rekam medis
    private final LocalDate date;  // Tanggal kunjungan
    private final String patientName;
    private final String doctorName;
    private final String poliName;
    private final String diagnosis;
    private final String treatment;

    /**
     * Konstruktor untuk membuat objek riwayat rekam medis dari data hasil JOIN antar tabel.
     *
     * @param id ID rekam medis.
     * @param date tanggal kunjungan pasien.
     * @param patientName nama pasien.
     * @param doctorName nama dokter yang menangani.
     * @param poliName nama poli/spesialisasi dokter (default "Umum" jika null).
     * @param diagnosis hasil diagnosis.
     * @param treatment penanganan/tindakan yang diberikan.
     */
    public MedicalRecordHistory(int id, LocalDate date, String patientName, String doctorName, String poliName, String diagnosis, String treatment) {
        this.id = id;
        this.date = date;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.poliName = poliName;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    // --- Getter-only (immutable DTO) ---

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getPoliName() {
        return poliName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }
}