/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.praktikum_6;

/**
 *
 * @author User
 */
public class Praktikum_6 {

    public static void main(String[] args) {
        Produk p1 = new Produk("Laptop", 700000000, 15);
        Produk p2 = new Produk("Handphone", 40000000, 13);
        
        System.out.println(p1.nama);
        System.out.println(p1.stok);
        //System.out.println(p1.harga);
        p1.tampilkanInfo();
        p2.namaSupplierFix();
        System.out.println("Harga: " + p1.getHarga());
        p1.setHarga(5000000);
        p1.tampilkanInfo();
        
        Produk.infoJumlahProduk();
        
    }
}
