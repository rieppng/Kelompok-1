/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.praktikum_5;

/**
 *
 * @author User
 */
public class Praktikum_5 {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        Mobil mobilSaya = new Mobil("Toyota", "Merah", 2025);
        Mobil mobilKedua = new Mobil ("Alphard", "Hitam", 2020);
        
        Mobil mobilKetiga = new Mobil();
        
        Kalkulator cal = new Kalkulator();
        System.out.println("1 + 20 = " + cal.tambah(1, 20));
        System.out.println("2,5 + 2,7 = " + cal.tambah(2.5, 2.7));
    }
}
