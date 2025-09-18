package com.mycompany.praktikum_5;

/**
 *
 * @author arief
 */

public class Mobil {
    // Atribut
    String merk;
    String warna;
    int tahunKeluar;
        
    // Constructor tanpa parameter
    public Mobil() {
        this.merk = "Unknown";
        this.warna = "Putih";
        this.tahunKeluar = 2010;
    }

    // Constructor tanpa parameter
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
