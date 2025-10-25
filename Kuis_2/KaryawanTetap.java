/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kuis_2;

/**
 *
 * @author User
 */
public class KaryawanTetap extends Karyawan implements IDapatGaji {
    private double gajiBulanan;
    public KaryawanTetap (String nama, String nip, double gajiBulanan){
        super(nama, nip);
        this.gajiBulanan = gajiBulanan;
    }

    public double getGajiBulanan() {
        return gajiBulanan;
    }

    public void setGajiBulanan(double gajiBulanan) {
        this.gajiBulanan = gajiBulanan;
    }
    
    
    @Override
    public void tampilkanSlipGaji(){
        System.out.println("Nama: " + getNama());
        System.out.println("NIP : " + getNip());
        System.out.println("Status: Karyawan Tetap");
        System.out.println("Gaji Bulanan: " + this.gajiBulanan);
    }
}
