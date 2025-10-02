/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum_6;

/**
 *
 * @author User
 */
public class Produk {
    public String nama;
    private int harga;
    protected int stok;
    private final String namaSupplier = "Chris";
    
    //Static variabel
    static int jumlahProduk = 0;
    
    public Produk (String nama, int harga, int stok){
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
        jumlahProduk++;
    }
    
    //method statis
    public static void infoJumlahProduk(){
        System.out.println("Total produk yang dibuat adalah: " + jumlahProduk);
    }
    
    public void tampilkanInfo(){
        System.out.println("Nama: " + nama);
        System.out.println("Harga: " + harga);
        System.out.println("Stok: " + stok);
    }
    
    private void namaSupplier(){
        System.out.println("Nama supplier: " + namaSupplier);
    }
    
    public void namaSupplierFix (){
        namaSupplier();
    }
    
    public int getHarga(){
        return harga;
    }
    
    public void setHarga(int hargaBaru){
        if(hargaBaru > 0){
            this.harga = hargaBaru;
        }
        else if(hargaBaru == 0){
            this.harga = hargaBaru;
            System.out.println("Produk ini gratis");
        }
        else {
            System.out.println("Harga tidak boleh negatif");
        }
    }
}
