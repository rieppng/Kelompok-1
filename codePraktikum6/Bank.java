/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum_6;

/**
 *
 * @author User
 */
public class Bank {
    private static String namaBank;
    private static int jumlahNasabah;
    private final String lokasiCabang;
    
    public Bank(String lokasiCabang) {
        this.lokasiCabang = lokasiCabang;
    }
    
    public static String getNamaBank(){
        return namaBank;
    }
    
    public static void setNamaBank(String namaBankBaru) {
        namaBank = namaBankBaru;
    }
    
    public static void tambahNasabah(){
        jumlahNasabah++;
    }
    
    public static int getJumlahNasabah(){
        return jumlahNasabah;
    }
    
    public String getLokasiCabang() {
        return lokasiCabang;
    }
}