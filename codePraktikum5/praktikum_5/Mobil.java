package com.mycompany.praktikum_5;
/**
 *
 * @author nasywa
 */
public class Mobil {
    // Attributes
    String merk;
    String warna;
    int tahunKeluar;

    // Constructor
    public Mobil() {
        this.merk = "Unknown";
        this.warna = "Putih";
        this.tahunKeluar = 2010;
    }

    public Mobil(String merk, String warna, int tahunKeluar) {
        this.merk = merk;
        this.warna = warna;
        this.tahunKeluar = tahunKeluar;
    }

    // Method
    public void panaskanMobil() {
        System.out.println("Mobil sedang dipanaskan");


    }
}
