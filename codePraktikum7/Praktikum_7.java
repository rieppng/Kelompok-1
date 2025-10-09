/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.praktikum_7;

/**
 *
 * @author arief
 */

public class Praktikum_7 {

    public static void main(String[] args) {
        Produk produk1 = new Produk("Buku", 150000);
        BarangElektronik produk2 = new BarangElektronik("Mouse", 150000, 12);

        produk1.tampilkanInfo();
        //produk1.tampilkanGaransi();
        System.out.println("Pajak Produk Umum : " + produk1.hitungPajak());
        System.out.println("Harga akhir produk : " + produk1.hitungHarga());
        System.out.println("----------------------------------");
        produk2.tampilkanInfo();
        produk2.tampilkanGaransi();
        System.out.println("Pajak Barang Elektronik : " + produk2.hitungPajak());
        System.out.println("Harga Akhir Produk : " + produk2.hitungHarga());
    }
}
