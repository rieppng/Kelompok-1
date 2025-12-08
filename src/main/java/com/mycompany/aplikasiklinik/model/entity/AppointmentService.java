package com.mycompany.aplikasiklinik.model.entity;

/**
 * Entitas yang merepresentasikan satu jenis layanan tambahan yang diberikan
 * dalam sebuah appointment (antrian). Contoh: vaksinasi, tes laboratorium, dll.
 * 
 * Setiap layanan dicatat bersama kuantitas dan harga pada saat pemberian,
 * agar historis tetap akurat meskipun harga layanan berubah di masa depan.
 */
public class AppointmentService {

    private int id;
    private int appointmentId;   // Menghubungkan ke antrian spesifik
    private int serviceFeeId;    // Mengacu ke entitas ServiceFee
    private int quantity;        // Jumlah layanan yang diberikan
    private double priceAtTime;  // Harga per unit saat layanan diberikan

    /**
     * Konstruktor default (tanpa parameter).
     */
    public AppointmentService() {}

    /**
     * Konstruktor untuk membuat objek layanan berdasarkan data inti.
     *
     * @param appointmentId ID antrian terkait.
     * @param serviceFeeId ID layanan dari tabel service_fees.
     * @param quantity jumlah layanan yang diberikan.
     * @param priceAtTime harga per unit pada saat transaksi.
     */
    public AppointmentService(int appointmentId, int serviceFeeId, int quantity, double priceAtTime) {
        this.appointmentId = appointmentId;
        this.serviceFeeId = serviceFeeId;
        this.quantity = quantity;
        this.priceAtTime = priceAtTime;
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

    public int getServiceFeeId() {
        return serviceFeeId;
    }

    public void setServiceFeeId(int serviceFeeId) {
        this.serviceFeeId = serviceFeeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceAtTime() {
        return priceAtTime;
    }

    public void setPriceAtTime(double priceAtTime) {
        this.priceAtTime = priceAtTime;
    }

    /**
     * Menghitung subtotal biaya untuk layanan ini.
     *
     * @return subtotal = quantity × priceAtTime.
     */
    public double getSubTotal() {
        return quantity * priceAtTime;
    }
}