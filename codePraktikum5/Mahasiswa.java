package com.mycompany.praktikum_5;

/**
 *
 * @author Varel
 */
public class Mahasiswa {
    String nama;
    int umur;
    String nim;
    
    // Constructor tanpa parameter
    public Mahasiswa() {
        this.nama = "Tidak diketahui";
        this.umur = 0;
        this.nim = "Tidak diketahui";
    }
    
    // Constructor dengan parameter
    public Mahasiswa(String nama, int umur, String nim){
        this.nama = nama;
        this.umur = umur;
        this.nim = nim;
    }
    
    // Method tanpa parameter
    public void tampilkanInpo() {
        System.out.println("Nama: " + this.nama + ", Umur: " + this.umur + " tahun, NIM: " + this.nim);
    }
       
    // Method dengan parameter
    public void tampilkanInpo(String nama, int umur, String nim) {
        System.out.println("Nama: " + nama + ", Umur: " + umur + " tahun, NIM: " + nim);
    }
}
