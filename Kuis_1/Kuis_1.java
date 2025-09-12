/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.kuis_1;

import java.util.Scanner;

/**
 *
 * @author User
 */
public class Kuis_1 {

    public static void main(String[] args) {
        //soal 1
        String nama = "Muhamad Radityo Pratama";
        int umur = 19;
        System.out.println("Nama: " + nama + ", Umur: " + umur);
        
        //soal 4
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan nama: ");
        String nama1 = input.nextLine();
        System.out.println("Halo, " + nama1 + ", selamat belajar Java!");
        
        //soal 5
        System.out.print("Masukkan berat badan (kg): ");
        double beratBadan = input.nextDouble();
        System.out.print("Masukkan tinggi badan (m): ");
        double tinggiBadan = input.nextDouble();
        
        double bmi = beratBadan / (tinggiBadan * tinggiBadan);
        System.out.println("BMI adalah: " + bmi);
        
        //soal 8
        int minggu[];
        System.out.print("kas minggu ke-1: ");
        int minggu1 = input.nextInt();
        System.out.print("kas minggu ke-2: ");
        int minggu2 = input.nextInt();
        System.out.print("kas minggu ke-3: ");
        int minggu3 = input.nextInt();
        System.out.print("kas minggu ke-4: ");
        int minggu4 = input.nextInt();
        
        int totalKas = minggu1 + minggu2 + minggu3 + minggu4;
        System.out.println("Total kas dari 4 minggu adalah: " + totalKas);
        
        //soal 2
        int totalBelanja = 50000;
        int diskon = 10;
        float totalBayar = totalBelanja - (totalBelanja * diskon / 100);
        System.out.println("Total belanja Rp" + totalBelanja + ", diskon " + diskon + "%" + ", total bayar Rp" + totalBayar);
        
        //soal 3
        final int sksMaksimal = 24;
        final int sksMinimal = 12;
        System.out.println("SKS maksimal: " + sksMaksimal + ", SKS minimal: " + sksMinimal);
        
        //soal 7
        final double PI = 3.14;
        System.out.println("menu kalkulator luas bangun datar");
        System.out.println("1. persegi (input sisi)");
        System.out.println("2. Lingkaran (input jari-jari)");
        System.out.println("3. segitiga (input alas dan tinggi)");
        System.out.print("Masukkan pilihan: ");
        int opsi = input.nextInt();
        switch (opsi){
            case 1:
                System.out.print("Inputkan sisi: ");
                double sisi = input.nextDouble();
                double luasP = sisi * sisi;
                System.out.println("Luas persegi adalah " + luasP);
                break;
            case 2:
                System.out.print("Inputkan jari-jari: ");
                double r = input.nextDouble();
                double luasL = PI * r * r;
                System.out.println("Luas Lingkaran adalah " + luasL);
                break;
            case 3:
                System.out.print("Inputkan alas: ");
                double alas = input.nextDouble();
                System.out.print("Inputkan tinggi: ");
                double tinggi = input.nextDouble();
                double luasS = alas * tinggi / 2;
                System.out.println("Luas Segitiga adalah " + luasS);
                break;
            default:
                System.out.println("Opsi tidak tersedia!");
        }
    }
}
