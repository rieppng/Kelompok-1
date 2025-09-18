package com.mycompany.praktikum_5;

import java.util.Scanner;

/**
 *
 * @author arief
 */

public class Praktikum_5 {
    public static void main(String[] args) {
        
        Mobil mobilSaya = new Mobil("Toyota", "Merah", 2025);
        Mobil mobilKedua = new Mobil("Alphard", "Hitam", 2020);
        Mobil mobilKetiga = new Mobil();
        
        Kalkulator cal = new Kalkulator();
        System.out.println("1 + 10 = " + cal.tambah(1, 10));
        System.out.println("2.4 + 3.3 = " + cal.tambah(2.4, 3.3));
        
//      Hewan kucing = new Hewan;
        
        /*Scanner input = new Scanner(System.in);
        
        StrikeAircraft Obj1 = new StrikeAircraft("Me 262", "German", "1943");
        StrikeAircraft Obj2 = new StrikeAircraft("P51D", "uSA");
        
        System.out.println("Press S or s to start engine!");
        String masukan = input.next();
        Obj1.startEngine(masukan);
        Obj2.startEngine(); 
        */  
    }
}
