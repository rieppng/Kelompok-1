package com.mycompany.praktikum_2;

/**
 * 
 * @author arief
 */
public class Praktikum_2 {
    public static void main(String[] args) {
        //Menampilkan Hello World
        System.out.println("Hello World!");

        //Menampilkan nama
        System.out.println("Nama saya Arief");
        
        int umur;
        umur = 20;
        String nama = "Aulia Arief";

        System.out.println("Nama saya : " + nama);
        System.err.println("Umur : " + umur);

        // Deklarasi multiple variable
        int x = 3, y = 3, z = 5;
        System.out.println("x = " + x + ", y = " + y + ", z + " + z);
        
        // Array
        double[] ipSemester = {3.9, 3.8, 4.0, 4.0};
        System.out.println("Ip semester 2 saya adalah = " + ipSemester[1]);
        
        // Konversi variable
        int intSemester1 = (int) ipSemester[0];
        System.out.println("Nilai  IP semester 1 integer : " + intSemester1);

        long nilaiSangatBesar = 3000000000L;
        int intNilaiSangatBesar = (int) nilaiSangatBesar;
        System.out.println("Nilai sangat besar: " + intNilaiSangatBesar);
        
        // Konstanta = yaitu nilai tetep sepanjang program
        final double PI = 3.14;
        // PI = 3.5;
        System.out.println("Nilai PI = " + PI);
    }
}