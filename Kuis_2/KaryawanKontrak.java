/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kuis_2;

/**
 *
 * @author User
 */
public class KaryawanKontrak extends Karyawan implements IDapatGaji {
    private double upahHarian;
    private int jumlahHariMasuk;
    public KaryawanKontrak (String nama, String nip, double upahHarian, int jumlahHariMasuk){
        super(nama, nip);
        this.upahHarian = upahHarian;
        this.jumlahHariMasuk = jumlahHariMasuk;
    }

    public double getUpahHarian() {
        return upahHarian;
    }

    public int getJumlahHariMasuk() {
        return jumlahHariMasuk;
    }

    public void setUpahHarian(double upahHarian) {
        this.upahHarian = upahHarian;
    }

    public void setJumlahHariMasuk(int jumlahHariMasuk) {
        this.jumlahHariMasuk = jumlahHariMasuk;
    }
    
    
    @Override
    public void tampilkanSlipGaji(){
        System.out.println("Nama: " + getNama());
        System.out.println("NIP : " + getNip());
        System.out.println("Status: Karyawan Kontrak");
        System.out.println("Total Gaji: " + (getUpahHarian() * getJumlahHariMasuk()));
    }
}
