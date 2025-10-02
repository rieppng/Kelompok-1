package com.mycompany.praktikum_6;

/**
 *
 * @author Varel
 */

public class Produk {
    public String nama;
    private double harga;
    protected int stok;
    private String namaSupplier = "Andi";
    static int jumlahProduk = 0;

    public Produk(String nama, double harga, int stok){
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
        jumlahProduk++;
    }
    
    public void tampilkanInfo(){
        System.out.println("Nama Barang : " + nama);
        System.out.println("Harga : " + harga);
        System.out.println("Stok : " + stok);
    }
    
    private void namaSupplier(){
        System.out.println("Nama supplier : " + namaSupplier);
    }
    
    public void namaSupplierFix(){
        namaSupplier();
    }
    
    public double getHarga(){
        return harga;
    }
    
    public void setHarga(double hargaBaru){
        if(hargaBaru > 0){
            this.harga = hargaBaru;
        }
        else if(hargaBaru == 0){
            this.harga = hargaBaru;
            System.out.println("Produk ini gratis.");
        }
        else{
            System.out.println("Harga produk tidak boleh negatif.");
        }
    }
    
    public static void infoJumlahProduk(){
        System.out.println("Total produk yang telah dibuat : " + jumlahProduk);
    }
}
