/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum_6;

/**
 *
 * @author User
 */
public class Tugas_Praktikum_6 {
    public static void main (String[] args){
        Bank.setNamaBank("BRI");
        Bank cabangPekanbaru = new Bank("Pekanbaru");
        Rekening rekening1 = new Rekening(cabangPekanbaru, "Aulia", "1500101", "221205", 1000000);
        System.out.println("Nama Bank: " + Bank.getNamaBank());
        System.out.println("Lokasi Cabang: " + rekening1.getCabang().getLokasiCabang());
        System.out.println("Nama Nasabah: " + rekening1.getNamaNasabah());
        System.out.println("Nomor Rekening: " + rekening1.getNomorRekening());
        System.out.println("Pin Rekening: " + rekening1.getPinRekening());
        System.out.println("Jumlah Saldo: Rp" + rekening1.getSaldo());
        
        //ubah pin
        rekening1.setPinRekening("1234");
        rekening1.setPinRekening("123456");
        
        //transaksi
        rekening1.setor(200000);
        rekening1.tarik(100000);
        System.out.println("Saldo akhir: Rp" + rekening1.getSaldo());
        
        System.out.println("Jumlah nasabah dari bank " + Bank.getNamaBank() + 
        ": " + Bank.getJumlahNasabah() + " Nasabah");
    }
}
