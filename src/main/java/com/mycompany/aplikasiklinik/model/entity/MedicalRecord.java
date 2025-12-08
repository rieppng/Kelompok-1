package com.mycompany.aplikasiklinik.model.entity;

/**
 * Entitas yang merepresentasikan rekam medis pasien setelah diperiksa oleh dokter.
 * Berisi gejala, diagnosis, dan rencana tindakan.
 * 
 * Catatan: Biaya konsultasi tidak disimpan di sini karena sekarang diambil dari
 * entitas User (dokter) atau Appointment, sesuai dengan kebutuhan sistem.
 */
public class MedicalRecord {

    private int id;
    private int appointmentId; // Menghubungkan ke antrian spesifik
    private String symptoms;   // Gejala yang dikeluhkan pasien
    private String diagnosis;  // Diagnosis medis oleh dokter
    private String treatment;  // Tindakan atau terapi yang diberikan

    // private double consultationFee; // Tidak digunakan — biaya konsultasi diambil dari User/Appointment

    /**
     * Konstruktor default (tanpa parameter).
     */
    public MedicalRecord() {}

    /**
     * Konstruktor utama untuk membuat rekam medis dari data inti.
     *
     * @param id ID unik rekam medis.
     * @param appointmentId ID antrian (appointment) terkait.
     * @param symptoms gejala yang dikeluhkan pasien.
     * @param diagnosis diagnosis medis oleh dokter.
     * @param treatment tindakan atau penanganan yang direkomendasikan.
     */
    public MedicalRecord(int id, int appointmentId, String symptoms, String diagnosis, String treatment) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    /**
     * Konstruktor tambahan (tidak aktif) untuk kompatibilitas historis.
     * Parameter consultationFee diabaikan karena tidak lagi digunakan.
     */
    public MedicalRecord(int id, int appointmentId, String symptoms, String diagnosis, String treatment, double consultationFee) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        // this.consultationFee = consultationFee; // Dinonaktifkan
    }

    // --- Getter dan Setter ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    // public double getConsultationFee() { return consultationFee; }
    // public void setConsultationFee(double consultationFee) { this.consultationFee = consultationFee; }
}