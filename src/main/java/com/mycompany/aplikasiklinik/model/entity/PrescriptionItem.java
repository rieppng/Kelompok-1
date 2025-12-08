package com.mycompany.aplikasiklinik.model.entity;

/**
 * Entitas yang merepresentasikan satu item dalam resep obat.
 * Setiap item terkait dengan satu rekam medis, satu jenis obat, dan memiliki status pemrosesan.
 */
public class PrescriptionItem {

    private int id;
    private int medicalRecordId; // ID rekam medis terkait
    private int appointmentId;   // ID antrian (appointment) — untuk kompatibilitas historis
    private int medicationId;    // ID obat dari tabel medications
    private int quantity;        // Jumlah obat yang diresepkan
    private String instructions; // Petunjuk pemakaian (misal: "2x1 sesudah makan")
    private double priceAtTime;  // Harga obat pada saat resep dibuat (untuk historis)
    private String status;       // Status pemrosesan: PENDING, PROCESSED, CANCELLED

    /**
     * Konstruktor default.
     * Secara otomatis mengatur status awal menjadi "PENDING".
     */
    public PrescriptionItem() {
        this.status = "PENDING";
    }

    /**
     * Konstruktor utama untuk membuat objek PrescriptionItem dari data database.
     * Mengisi semua field termasuk medicalRecordId.
     *
     * @param id ID unik item resep.
     * @param medicalRecordId ID rekam medis yang menghasilkan resep ini.
     * @param appointmentId ID antrian (untuk referensi tambahan).
     * @param medicationId ID obat yang diresepkan.
     * @param quantity jumlah obat yang diresepkan.
     * @param instructions petunjuk pemakaian obat.
     * @param priceAtTime harga obat saat resep dibuat.
     */
    public PrescriptionItem(int id, int medicalRecordId, int appointmentId, int medicationId, int quantity, String instructions, double priceAtTime) {
        this.id = id;
        this.medicalRecordId = medicalRecordId;
        this.appointmentId = appointmentId;
        this.medicationId = medicationId;
        this.quantity = quantity;
        this.instructions = instructions;
        this.priceAtTime = priceAtTime;
        this.status = "PENDING";
    }

    /**
     * Konstruktor kompatibilitas untuk kasus di mana medicalRecordId belum tersedia.
     * Mengatur medicalRecordId ke 0 (nilai dummy).
     *
     * @param id ID unik item resep.
     * @param appointmentId ID antrian.
     * @param medicationId ID obat.
     * @param quantity jumlah obat.
     * @param instructions petunjuk pemakaian.
     * @param priceAtTime harga obat saat resep dibuat.
     */
    public PrescriptionItem(int id, int appointmentId, int medicationId, int quantity, String instructions, double priceAtTime) {
        this(id, 0, appointmentId, medicationId, quantity, instructions, priceAtTime);
    }

    // --- Getter dan Setter ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedicalRecordId() {
        return medicalRecordId;
    }

    public void setMedicalRecordId(int medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(int medicationId) {
        this.medicationId = medicationId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public double getPriceAtTime() {
        return priceAtTime;
    }

    public void setPriceAtTime(double priceAtTime) {
        this.priceAtTime = priceAtTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Menghitung subtotal biaya untuk item resep ini berdasarkan jumlah dan harga saat resep dibuat.
     *
     * @return subtotal = quantity × priceAtTime.
     */
    public double getSubTotal() {
        return quantity * priceAtTime;
    }
}