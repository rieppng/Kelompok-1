package com.mycompany.praktikum_5;

/**
 *
 * @author Varel
 */
public class Mobil {
        String merk;
        String warna;
        int tahunKeluar;
        
        public Mobil(){
            this.merk = "Unknown";
            this.warna = "Putih";
            this.tahunKeluar = 2010;
        }
        
        
        // Constructor
        public Mobil(String merk, String warna, int tahunKeluar){
            this.merk = merk;
            this.warna = warna;
            this.tahunKeluar = tahunKeluar;
        }
        
        public void panaskanMobil(){
            System.out.println("Mobil sedang dipanaskan");
        }
        
}
