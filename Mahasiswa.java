package com.mycompany.praktikum_5;

/**
 *
 * @author Varel
 */
public class Mahasiswa {
    String nama;
    int umur;
    String nim;
    
    public Mahasiswa() {
        this.nama = "No name";
        this.umur = 0;
        this.nim = "-";
    }
    
    public Mahasiswa(String nama) {
        this.nama = nama;
        this.umur = 0;
        this.nim = "-";
    }
    
    public Mahasiswa(String nama, int umur, String nim){
        this.nama = nama;
        this.umur = umur;
        this.nim = nim;
    }
    
    public void tampilkanInpo() {
        System.out.println("Nama: " + this.nama + ", Umur: " + this.umur + " tahun, NIM: " + this.nim);
    }
    
    
    public void tampilkanInpo(String nama) {
        System.out.println("Nama: " + nama);
    }
    
    public void tampilkanInpo(String nama, int umur) {
        System.out.println("Nama: " + nama + ", Umur: " + umur + " tahun");
    }


    public void tampilkanInpo(String nama, int umur, String nim) {
        System.out.println("Nama: " + nama + ", Umur: " + umur + " tahun, NIM: " + nim);
    }

    public static void main(String[] args) {
        Mahasiswa m1 = new Mahasiswa();                  
        Mahasiswa m2 = new Mahasiswa("Varel");             
        Mahasiswa m3 = new Mahasiswa("Jepri", 20, "2407113635");
        
        m1.tampilkanInpo();
        m2.tampilkanInpo();
        m3.tampilkanInpo();


        m1.tampilkanInpo("Aurel");
        m1.tampilkanInpo("Widya", 20);
        m1.tampilkanInpo("Puput", 19, "2407113876");
    }
}
