package com.mycompany.praktikum_4;
import java.util.Scanner;

/**
 *
 * @author Wawa
 */
public class Latihan_4 {
    public static void main (String[] args){
        Scanner input = new Scanner(System.in);
        char opsi, opsiCase;
        do {
            System.out.println("===Menu Kalkulator Luas bangun Datar===");
            System.out.println("1. Persegi Panjang");
            System.out.println("2. Persegi");
            System.out.println("3. Lingkaran");
            System.out.println("4. Segitiga");
            System.out.println("5. Keluar");
            System.out.println("______________________________________");
            System.out.print("Masukkan pilihan: ");
            opsi = input.next().charAt(0);
            switch (opsi){
                case '1':
                    do {
                        System.out.print("Masukkan panjang(cm): ");
                        double panjang = input.nextDouble();
                        System.out.print("Masukkan lebar(cm): ");
                        double lebar = input.nextDouble();
                        double luasPersegiPanjang = panjang * lebar;
                        System.out.println("Luas persegi panjang adalah: " + luasPersegiPanjang + " cm^2");
                        System.out.print("Hitung ulang? (y/t): ");
                        opsiCase = input.next().charAt(0);
                    }while (opsiCase == 'y' || opsiCase == 'Y');
                    break;
                case '2':
                    do{
                        System.out.print("Masukkan sisi(cm): ");
                        double sisi = input.nextDouble();
                        double luasPersegi = sisi * sisi;
                        System.out.println("Luas persegi adalah " + luasPersegi + " cm^2");
                        System.out.print("Hitung ulang? (y/t): ");
                        opsiCase = input.next().charAt(0);
                    }while (opsiCase == 'y' || opsiCase == 'Y');
                    break;
                case '3':
                    do{
                        final double PI = 3.14;
                        System.out.print("Masukkan jari-jari(cm): ");
                        double r = input.nextDouble();
                        double luasLingkaran = PI * r * r;
                        System.out.println("Luas Lingkaran adalah " + luasLingkaran + " cm^2");
                        System.out.print("Hitung ulang? (y/t): ");
                        opsiCase = input.next().charAt(0);
                    }while (opsiCase == 'y'|| opsiCase == 'Y');
                    break;
                case '4':
                    do{
                        System.out.print("Masukkan alas(cm): ");
                        double alas = input.nextDouble();
                        System.out.print("Masukkan tinggi (cm): ");
                        double tinggi = input.nextDouble();
                        double luasSegitiga = alas * tinggi / 2;
                        System.out.println("Luas Segitiga adalah " + luasSegitiga + " cm^2");
                        System.out.print("Hitung ulang? (y/t): ");
                        opsiCase = input.next().charAt(0);
                    }while (opsiCase == 'y' || opsiCase == 'Y' );
                    break;
                case '5':
                    System.out.println("Program selesai, terima kasih");
                    break;
                default:
                    System.out.println("Pilihan tidak tersedia, silahkan input kembali");
            }
        }while (opsi != '5');
    }
}
