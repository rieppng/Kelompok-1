package com.mycompany.praktikum_4;
import java.util.Scanner;

/**
 *
 * @author LENOVO
 */
public class quiz {
    public static void main(String[] args){
        // Soal 1       
        Scanner input = new Scanner(System.in);
        String nama;
        int umur;
        
        System.out.print("Masukkan nama : ");
        nama = input.nextLine();
        
        System.out.print("Masukkan umur : ");
        umur = input.nextInt();
        System.out.println("Nama saya " + nama + ", dan saya berumur " + umur);
        
        // Soal 2
        long totalBelanja = 50000L, totalBayar = 45000L;
        double diskon = 0.10;
        System.out.println();
        System.out.println("----------Nota----------");
        System.out.println("Total Belanja : " + totalBelanja);
        System.out.println("Diskon : " + diskon);
        System.out.println("Total Bayar : " + totalBayar);
        
        // Soal 4
        System.out.println();
        String siswa;
        System.out.print("Masukkan nama siswa : ");
        siswa = input.nextLine();
        System.out.println("Halo, " + siswa + ", selamat belajar Java!");
        
        // Soal 5
        System.out.println();
        double berat;
        double tinggi;
        System.out.print("Berat : ");
        berat = input.nextInt();
        System.out.print("Tinggi : ");
        tinggi = input.nextInt();
        double Tinggi = tinggi*tinggi;
        double bmi = berat/Tinggi; 
        System.out.println("Indeks massa tubuh anda : " + bmi);
        
        // Soal 6
        System.out.println();
        enum Warna {
            MERAH,
            HIJAU,
            BIRU
        }
        for (Warna myVar : Warna.values()) {
            System.out.println(myVar);
        }
        
        /*
        // Soal 8
        System.out.println();
        long[] dataKas = ;
        long masuk;
        for (int i = 0; i <= 3 ; i++){
            System.out.print("Kas minggu ke - " + i + " : ");
            masuk = input.nextLong();
            dataKas [i] = masuk;
        }
        */
        
    }
}
