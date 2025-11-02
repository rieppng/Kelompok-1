package com.mycompany.praktikum_9;
import java.util.ArrayList;

/**
 *
 * @author BlueBird
 */
public class Mahasiswa {
    private String nama;
    private String nim;
    private String prodi;
    private String jenisKelamin;
    private boolean isActive;
    public Mahasiswa(String nama, String nim, String prodi, String jenisKelamin, boolean isActive) {
        this.nama = nama;
        this.nim = nim;
        this.prodi = prodi;
        this.jenisKelamin = jenisKelamin;
        this.isActive = isActive;
    }
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public String getNim() {
        return nim;
    }
    public void setNim(String nim) {
        this.nim = nim;
    }
    public String getProdi() {
        return prodi;
    }
    public void setProdi(String prodi) {
        this.prodi = prodi;
    }
    public String getJenisKelamin() {
        return jenisKelamin;
    }
    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }
    public boolean isIsActive() {
        return isActive;
    }
    public void setIsActive(boolean active) {
        this.isActive = isActive;
    }
    public static ArrayList<Mahasiswa> mahasiswas = new ArrayList<>(); 
}