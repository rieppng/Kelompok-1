package com.mycompany.praktikum_4;
import java.util.Scanner;
/**
 *
 * @author nasywa
 */
public class Praktikum_4 {
    public static void main(String[] args) {
        //Kondisi if else
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan nilai : ");
        int nilai = input.nextInt();

        if(nilai > 75) {
            System.out.println("Nilai anda lulus");
        }
        else if(nilai > 65){
            System.out.println("Nilai anda berada diambang batas kelulusan");
        }
        else{
            System.out.println("Anda tidak lulus ujian");
        }

        //Kondisi switch case
        System.out.print("Masukkan warna(RGB) : ");
        String warna = input.next();
        switch(warna) {
            case "R":
                System.out.println("Anda memilih warna merah");
                break;
            case "G":
                System.out.println("Anda memilih warna hijau");
                break;
            case "B":
                System.out.println("Anda memilih warna biru");
                break;
            default:
                System.out.println("Warna yang anda pilih tidak ada");
        }

        // Nested if
        System.out.print("Masukkan angka : ");
        int angka = input.nextInt();

        if(angka >= 0) {
            System.out.print("Bilangan bernilai positif");

            if(angka % 2 == 0) {
                System.out.println(" dan genap");
            }
            else {
                System.out.println(" dan ganjil");
            }
        }
        else if(angka == 0) {
            System.out.println("Bilangan bernilai netral");
        }
        else {
            System.out.print("Bilangan bernilai negatif");

            if(angka % 2 == 0) {
                System.out.println(" dan genap");
            }
            else {
                System.out.println(" dan ganjil");
            }
        }

        // Perulangan for
        for(int a = 1; a <= 5; a++) {
            System.out.println("For loop ke - " + a);
        }

        // Perulangan while
        int b = 1;
        while (b <=5) {
            System.out.println("While loop ke - " + b);
            b++;
        }

        // Perulangan do while
        int c = 6;
        do {
            System.out.println("Iterasi ke - " + c);
            c++;
        } while(c <= 5);

        // Nested loop
        char Kursi = 'A';
        for (int i = 0; i <= 5; i++) {
            for(int j = 0; j <= 5; j++) {
                System.out.print(Kursi + "" + j + " ");
            }
            System.out.println();
            Kursi++;
        }

        // Break
        for (int d = 1; d <= 10; d++) {
            if(d == 5) {
                System.out.println("Break di angka : " + d);
                break;
            }
            System.out.println("Angka : " + d);
        }

        // Continue
        for (int e = 1; e <= 10; e++) {
            if(e % 2 == 0) {
                continue;
            }
            System.out.println("Bilangan ganjil : " + e);
        }

    }
}
