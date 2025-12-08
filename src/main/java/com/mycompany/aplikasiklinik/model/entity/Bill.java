package com.mycompany.aplikasiklinik.model.entity;

import java.time.LocalDateTime;

/**
 * Entitas yang merepresentasikan tagihan pasien setelah pemeriksaan selesai.
 * Tagihan mencakup tiga komponen biaya:
 * - Biaya obat (totalMedicationCost)
 * - Biaya tindakan/layanan tambahan (totalServiceCost)
 * - Biaya konsultasi dokter (consultationFee)
 * 
 * Total tagihan (totalAmount) dihitung otomatis setiap kali salah satu komponen berubah.
 */
public class Bill {

    private int id;
    private int appointmentId;          // Menghubungkan ke antrian spesifik
    private double totalMedicationCost; // Total biaya obat dari resep
    private double totalServiceCost;    // Total biaya layanan/tindakan tambahan
    private double consultationFee;     // Biaya konsultasi dokter
    private double totalAmount;         // Total keseluruhan (otomatis dihitung)
    private boolean isPaid;             // Status pembayaran
    private LocalDateTime paymentDate;  // Waktu pembayaran (null jika belum dibayar)

    /**
     * Konstruktor default (tanpa parameter).
     */
    public Bill() {}

    /**
     * Konstruktor lama untuk kompatibilitas (tanpa totalServiceCost).
     * Mengasumsikan totalServiceCost = 0.
     *
     * @param id ID tagihan.
     * @param appointmentId ID antrian terkait.
     * @param totalMedicationCost total biaya obat.
     * @param consultationFee biaya konsultasi dokter.
     * @param isPaid status pembayaran.
     */
    public Bill(int id, int appointmentId, double totalMedicationCost, double consultationFee, boolean isPaid) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.totalMedicationCost = totalMedicationCost;
        this.totalServiceCost = 0.0;
        this.consultationFee = consultationFee;
        this.isPaid = isPaid;
        this.recalculateTotal();
    }

    /**
     * Konstruktor lengkap yang mencakup semua komponen biaya.
     *
     * @param id ID tagihan.
     * @param appointmentId ID antrian terkait.
     * @param totalMedicationCost total biaya obat.
     * @param totalServiceCost total biaya layanan/tindakan.
     * @param consultationFee biaya konsultasi dokter.
     * @param isPaid status pembayaran.
     */
    public Bill(int id, int appointmentId, double totalMedicationCost, double totalServiceCost, double consultationFee, boolean isPaid) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.totalMedicationCost = totalMedicationCost;
        this.totalServiceCost = totalServiceCost;
        this.consultationFee = consultationFee;
        this.isPaid = isPaid;
        this.recalculateTotal();
    }

    // --- Getter ---

    public int getId() {
        return id;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public double getTotalMedicationCost() {
        return totalMedicationCost;
    }

    public double getTotalServiceCost() {
        return totalServiceCost;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    /**
     * Mengembalikan total tagihan (obat + layanan + konsultasi).
     * Nilai ini bisa diisi dari database atau dihitung ulang secara otomatis.
     *
     * @return total tagihan dalam Rupiah.
     */
    public double getTotalAmount() {
        return this.totalAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    // --- Setter (dengan pembaruan otomatis totalAmount) ---

    public void setId(int id) {
        this.id = id;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setTotalMedicationCost(double totalMedicationCost) {
        this.totalMedicationCost = totalMedicationCost;
        this.recalculateTotal();
    }

    public void setTotalServiceCost(double totalServiceCost) {
        this.totalServiceCost = totalServiceCost;
        this.recalculateTotal();
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
        this.recalculateTotal();
    }

    /**
     * Menetapkan totalAmount secara langsung.
     * Digunakan terutama saat membaca data dari database yang sudah memiliki kolom total_amount.
     * 
     * Catatan: Tidak memicu recalculateTotal() karena nilai dianggap sudah final.
     *
     * @param totalAmount total tagihan yang telah dihitung.
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * Menghitung ulang total tagihan berdasarkan tiga komponen biaya.
     * Dipanggil otomatis setiap kali salah satu komponen berubah melalui setter.
     */
    private void recalculateTotal() {
        this.totalAmount = this.totalMedicationCost + this.totalServiceCost + this.consultationFee;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", appointmentId=" + appointmentId +
                ", totalMedicationCost=" + totalMedicationCost +
                ", totalServiceCost=" + totalServiceCost +
                ", consultationFee=" + consultationFee +
                ", totalAmount=" + totalAmount +
                ", isPaid=" + isPaid +
                ", paymentDate=" + paymentDate +
                '}';
    }
}