package com.mycompany.praktikum_4;

import java.util.Scanner;

/**
 *
 * @author BlueBird
 */
public class Quiz_1 {
        
    public static void main(String[] args) {
        //no 7
        try (Scanner input = new Scanner(System.in)) {
                
            //Input Sisi
            System.out.println("Masukkan Panjang Sisi Persegi : ");
            double s = input.nextDouble();

            //Hitung Luas Persegi
            double luas = Math.pow(s, 2);

            //Output Hasil
            System.out.println("Luas Persegi = " + luas);
        }
        
        //no 1
        System.out.println("Nama : R. Muhammad Varel Ananda Putra");
        System.out.println("Nim : 2407113635");

        //no 4

        try (Scanner input = new Scanner(System.in)) {

            System.out.print("Masukkan Nama Lengkap : ");
            String fullName = input.nextLine();

            System.out.print("Masukkan Umur : ");
            String umur = input.nextLine();

            System.out.println("Nama saya adalah " + fullName + " dan saya berumur " + umur + " tahun.");
        }
        
        
        //no 8
        int totalKas = 0;
        try (Scanner input = new Scanner(System.in)) {
            for (int i = 1; i <= 4; i++) {
                System.out.print("Masukkan kas minggu ke-" + i + ": Rp ");
                int kasMinggu = input.nextInt();

                totalKas += kasMinggu;
            }
        }
        System.out.println("\nTotal kas selama 4 minggu adalah: Rp " + totalKas);
    }
}


        

        
    

