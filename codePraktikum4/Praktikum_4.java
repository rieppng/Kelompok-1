/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.praktikum_4;

import java.util.Scanner;

/**
 *
 * @author Tyo
 */
public class Praktikum_4 {
    // Loop dan kondisi
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
       
        System.out.print("Masukkan nilai: ");
        int nilai = input.nextInt();
        
        if (nilai>75){
            System.out.println("Anda lulus ujian");
        }
        else if (nilai>65){
            System.out.println("Nilai anda di ambang batas kelulusan");
        }
        else {
            System.out.println("Anda tidak lulus ujian");
        }
        
        // switch case
        System.out.print("Masukkan warna (RGB): ");
        String warna = input.next();
        switch (warna) {
            case "R" :
                System.out.println("Anda memilih warrna Merah");
                break;
            case "G" :
                System.out.println("Anda memilih warrna Hijau");
                break;
            case "B" :
                System.out.println("Anda memilih warrna Biru");
                break;
            default:
                System.out.println("Warna tidak tersedia");
        }
        
        //nested if
        System.out.print("Masukkan angka: ");
        int angka = input.nextInt();
        if (angka >= 0 ){
            System.out.print("Bilangan bernilaii positif ");
            
            if (angka%2==0) {
                System.out.println("dan genap");
            } else {
                System.out.println("dan ganjil");
            }  
        } else if (angka==0){
            System.out.println("Bilangan bernilai netral");
        } else {
            System.out.print("Bilangan bernilai negatif ");
            
            if (angka%2==0) {
                System.out.println("dan genap");
            } else {
                System.out.println("dan ganjil");
            }
        }
        
        // Struktur LOOP
        int a;
        for (a = 1; a <= 5; a++){
            System.out.println("For loop ke-" + a);
        }
        
        int b = 1;
        while (b <= 5){
            System.out.println("While loop ke-" + b);
            b++;
        }
        
        int c = 6;
        do {
            System.out.println("Do While loop ke-" + c);
        } while (c <= 5);
        
        char kursi = 'A';
        for (int i = 0; i < 3; i++) {
            for (int j= 1; j <= 5; j++) {
                System.out.print(kursi + "" + j + " ");
            }
            System.out.println();
            kursi++;
        }
        
        //break
        for (int d = 1; d <=10;d++){
            if (d==5){
                System.out.println("Break di angka " + d);
                break;
            }
            System.out.println("Angka: " + d);
        }
        
        //continue
         for (int e = 1; e <=10; e++){
            if (e %2 == 0){
                continue;
            }
            System.out.println("Bilangan ganjil:  " + e);
        }
    }
}
