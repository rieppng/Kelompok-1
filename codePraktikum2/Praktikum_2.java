package com.mycompany.praktikum_2;

/**
 *
 * @author nasywa
 */
public class Praktikum_2 {
    // Menampilkan Hello World
    public static void main(String[] args) {
        System.out.println("Hello World!");

        // Menampilkan nama
        System.out.println("Nama saya Nasywa");

        int umur;
        umur = 20;
        String nama = "Nasywa Razhan Deethia";

        System.out.println("Nama saya : " + nama);
        System.out.println("Umur : " + umur);

        //Deklarasi multiple variable 
        int x = 3, y = 3, z = 5;
        System.out.println("x = " + x + " y = " + y + " z = " + z);

        double[] ipsemester = {3.9, 3.8, 4.0, 4.0};
        System.out.println("IP Semester 2 saya adalah : " + ipsemester[1]);

        //Konversi variable
        int intSem1 = (int) ipsemester[0];
        System.out.println("Nilai IP Semester 1 integer : " + intSem1);

        long nilaiSangatBesar = 30000000L;
        int intNilaiSangatBesar = (int) nilaiSangatBesar;
        System.out.println(intNilaiSangatBesar);

        //Konstanta adalah nilai tetap sepanjang program
        final double PI = 3.14;
        //PI 3,5
        System.out.println("Nilai PI = " + PI);
    }
}



