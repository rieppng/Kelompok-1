package com.mycompany.aplikasiklinik.model.entity;

import java.util.Objects;

/**
 * Entitas yang merepresentasikan spesialisasi medis atau poli klinik.
 * Contoh: "Umum", "Gigi", "Mata", "Kulit", dll.
 */
public class MedicalSpecialty {

    private int id;
    private String name; // Nama poli/spesialisasi medis

    /**
     * Konstruktor default (tanpa parameter).
     */
    public MedicalSpecialty() {}

    /**
     * Konstruktor dengan ID dan nama, biasanya digunakan saat mengambil data dari database.
     *
     * @param id ID unik spesialisasi dari database.
     * @param name nama spesialisasi (misal: "Bedah Umum").
     */
    public MedicalSpecialty(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Konstruktor hanya dengan nama, digunakan saat membuat spesialisasi baru sebelum disimpan ke database.
     *
     * @param name nama spesialisasi.
     */
    public MedicalSpecialty(String name) {
        this.name = name;
    }

    // --- Getter dan Setter ---

    /**
     * Mengembalikan ID unik spesialisasi.
     *
     * @return ID spesialisasi.
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Mengembalikan nama spesialisasi medis.
     *
     * @return nama poli/spesialisasi.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Mengembalikan nama spesialisasi sebagai representasi teks.
     * Digunakan terutama di antarmuka pengguna (misalnya di dropdown daftar poli).
     *
     * @return nama spesialisasi.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Membandingkan dua objek MedicalSpecialty berdasarkan ID dan nama.
     *
     * @param o objek lain yang akan dibandingkan.
     * @return true jika objek identik berdasarkan ID dan nama; false jika tidak.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalSpecialty that = (MedicalSpecialty) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    /**
     * Menghasilkan kode hash berdasarkan ID dan nama spesialisasi.
     * Digunakan untuk struktur data seperti HashSet atau sebagai kunci di HashMap.
     *
     * @return nilai hash unik berdasarkan konten objek.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}