package com.mycompany.aplikasiklinik.model.entity;

/**
 * Entitas yang merepresentasikan jenis layanan tambahan beserta harganya.
 * Contoh: "Vaksinasi", "Pemeriksaan Laboratorium", dll.
 */
public class ServiceFee {

    private int id;       // ID unik dari layanan
    private String name;  // Nama layanan (misal: "Konsultasi Gigi")
    private double price; // Harga layanan dalam Rupiah

    /**
     * Konstruktor default (tanpa parameter).
     */
    public ServiceFee() {}

    /**
     * Konstruktor lengkap untuk membuat objek ServiceFee dengan semua properti.
     *
     * @param id   ID unik layanan dari database.
     * @param name nama layanan.
     * @param price harga layanan (dalam Rupiah).
     */
    public ServiceFee(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // --- Getter dan Setter ---

    /**
     * Mengembalikan ID unik layanan.
     *
     * @return ID layanan.
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Mengembalikan nama layanan.
     *
     * @return nama layanan.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Mengembalikan harga layanan.
     *
     * @return harga layanan dalam Rupiah.
     */
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Mengembalikan representasi teks yang informatif untuk tampilan di GUI,
     * berupa nama layanan diikuti harganya.
     *
     * @return string dalam format: "Nama Layanan (Rp harga)".
     */
    @Override
    public String toString() {
        return name + " (Rp " + price + ")";
    }
}