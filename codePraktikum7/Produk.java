package com.mycompany.praktikum_7;

/**
 *
 * @author Varel
 */

public class Produk implements HargaAkhir{
    protected String nama;
    protected int harga;
    
    public Produk(String nama, int harga){
        this.nama = nama;
        this.harga = harga;
    }
    
    public void tampilkanInfo(){
        System.out.println("Nama Produk : " + nama);
        System.out.println("Harga : " + harga);
    }
    
    public double hitungPajak(){
        return harga * 0.05;
    }
    
    @Override
    public double hitungHarga(){
        return harga * 1.05;
    }
}
