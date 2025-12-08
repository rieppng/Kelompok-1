package com.mycompany.aplikasiklinik.model.entity;

/**
 * Entitas yang merepresentasikan data obat di klinik.
 * Berisi informasi nama, kategori, stok, dan harga obat.
 */
public class Medication {

    private int id;
    private String name;           // Nama obat (misal: "Paracetamol 500mg")
    private String category;       // Kategori obat (misal: "Tablet", "Sirup", "Kapsul")
    private int stockQuantity;     // Jumlah stok tersedia
    private double price;          // Harga per unit dalam Rupiah

    /**
     * Konstruktor default (tanpa parameter).
     */
    public Medication() {}

    /**
     * Konstruktor lengkap untuk membuat objek Medication dengan semua atribut.
     *
     * @param id ID unik obat dari database.
     * @param name nama obat.
     * @param category kategori obat (misal: "Tablet").
     * @param stockQuantity jumlah stok yang tersedia.
     * @param price harga per unit obat (dalam Rupiah).
     */
    public Medication(int id, String name, String category, int stockQuantity, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.stockQuantity = stockQuantity;
        this.price = price;
    }

    // --- Getter dan Setter ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Mengembalikan nama obat sebagai representasi teks.
     * Digunakan terutama di komponen GUI seperti daftar obat atau resep.
     *
     * @return nama obat.
     */
    @Override
    public String toString() {
        return name;
    }
}