package com.mycompany.aplikasiklinik.model.entity;

/**
 * Enum yang merepresentasikan peran pengguna dalam sistem klinik.
 * Setiap nilai enum memiliki nama tampilan yang ramah pengguna,
 * yang digunakan terutama di antarmuka grafis (GUI).
 */
public enum UserRole {
    SUPERADMIN("Super Admin"),
    RECEPTIONIST("Resepsionis"),
    DOCTOR("Dokter"),
    PHARMACIST("Apoteker"),
    CASHIER("Kasir");

    private final String displayName;

    /**
     * Konstruktor enum untuk menginisialisasi nama tampilan yang mudah dibaca.
     *
     * @param displayName nama yang akan ditampilkan di antarmuka pengguna.
     */
    UserRole(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Mengembalikan representasi teks yang ramah pengguna dari peran ini.
     * Digunakan secara otomatis saat enum di-convert ke String (misalnya di JComboBox).
     *
     * @return nama tampilan peran, seperti "Dokter" atau "Resepsionis".
     */
    @Override
    public String toString() {
        return displayName;
    }
}