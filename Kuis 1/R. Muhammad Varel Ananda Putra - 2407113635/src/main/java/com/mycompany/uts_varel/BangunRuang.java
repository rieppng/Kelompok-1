package com.mycompany.uts_varel;

/**
 *
 * @author Varel
 */

public class BangunRuang {
    int sisi;
    String nim;
    String prodi;
    int tahunMasuk;
    
    public Mahasiswa() {
        this.nama = "Tidak diketahui";
        this.nim = "Tidak diketahui";
        this.prodi = "Tidak diketahui";
        this.tahunMasuk = 0;
}
    public Mahasiswa(String nama, String nim, String prodi, int tahunMasuk){
        this.nama = nama;
        this.nim = nim;
        this.prodi = prodi;
        this.tahunMasuk = tahunMasuk;
    }
    
    public void tampilkanBiodata() {
        System.out.println("Nama: " + this.nama + ", NIM: " + this.nim + " dari prodi " + this.prodi + " angkatan " + this.tahunMasuk);
    }
    
    public void tampilkanBiodata(String nama, String nim, String prodi, int tahunMasuk) {
        System.out.println("Nama: " + nama + ", NIM: " + nim + " Prodi " + prodi + " angkatan " + tahunMasuk);
    }
}
