package com.mycompany.praktikum_6;

/**
 *
 * @author Varel
 */
public class Tugas_Praktikum_6 {
    public static void main (String[] args){
        Bank.setNamaBank("BRI");
        Bank cabangPekanbaru = new Bank("Pekanbaru");
        Rekening rekening1 = new Rekening(cabangPekanbaru, "Varel", "1500101", "221205", 1000000);
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
