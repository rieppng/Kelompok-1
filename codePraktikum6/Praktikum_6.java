package com.mycompany.praktikum_6;

/**
 *
 * @author Varel
 */

public class Praktikum_6 {

    public static void main(String[] args) {
        Produk produk1 = new Produk("Laptop", 12000000, 5);
        Produk produk2 = new Produk("HP", 3500000, 10);

        System.out.println(produk1.nama);
        System.out.println(produk1.stok);
//      System.out.println(produk1.harga);   <<-- Ini akan error
        
//      produk1.namaSupplier();         <<-- Ini akan error
        produk1.namaSupplierFix();
        produk2.namaSupplierFix();
        produk1.tampilkanInfo();
        produk2.tampilkanInfo();

        System.out.println("Harga : " + produk1.getHarga());

        produk1.setHarga(7000000);
        System.out.println("Harga : " + produk1.getHarga());

        Produk.infoJumlahProduk();
    }
}
