package com.mycompany.praktikum_5;

/**
 *
 * @author Varel
 */
public class Praktikum_5 {
    public static void main(String[] args) {
        System.out.println("");
        
        Mobil mobilSaya = new Mobil("Toyota", "Merah", 2025);;
        Mobil mobilKedua = new Mobil ("Alpard", "Hitam", 2020);
        
        Mobil mobilKetiga = new Mobil();
        
        Kalkulator cal = new Kalkulator ();
        System.out.println ("1 + 10 = " + cal.tambah(1, 10));
        System.out.println ("1 + 10 = " + cal.tambah(2.4, 3.3));
    }
}
