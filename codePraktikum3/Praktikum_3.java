package com.mycompany.praktikum_3;
import java.util.Scanner;

/**
 *
 * @author tyo
 */

public class Praktikum_3 {
    public static void main(String args[]) {
        int a = 10, b = 2;
        
        // Operator aritmatika.
        System.out.println("Tambah (+) : " + (a+b));
        System.out.println("Kurang (-) : " + (a-b));
        System.out.println("Kali (*) : " + (a*b));
        System.out.println("Bagi (/) : " + (a/b));
        System.out.println("Modulus (%) : " + (a%b));
        
        // Operator perbandingan.
        System.out.println("a == b : " + (a==b));
        System.out.println("a != b : " + (a!=b));
        System.out.println("a > b : " + (a>b));
        System.out.println("a < b : " + (a<b));
        System.out.println("a >= b : " + (a>=b));
        System.out.println("a <= b : " + (a<=b));
        
        // Operator logika.
        boolean x = true, y = false;
        System.out.println("x && y : " + (x&&y));
        System.out.println("x || y : " + (x||y));
        System.out.println("!x : " + (!x));
        
        // Operator assignment.
        int  i = 10;
        i += 2;
        System.out.println("i += 2 : " + i);
        
        // Operator ternary.
        int nilai = 40;
        char grade = (nilai >= 80) ? 'A' : (nilai >= 75) ? 'B' : (nilai >= 60) ? 'C' : 'D';
        System.out.println("Grade : " + grade);
        
        // Operator bitwise.
        System.out.println("a & b : " + (a & b));
        System.out.println("a | b : " + (a | b));
        
        // Output
        String nama = "wawa";
        int umur = 19;
        double tinggi = 158.5F;

        System.out.printf("Halo nama saya %s, umur %d, tinggi %1f cm", nama, umur, tinggi);
        System.out.println();
        
        // Input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan NIM anda : ");
        String nim = scanner.nextLine();
        System.out.println("NIM anda adalah : " + nim);
        
        System.out.print("Masukkan umur anda : ");
        int umur1 = scanner.nextInt();
        System.out.println("Umur anda adalah : " + umur1);
        
        // Class Wrapper
        String str = "19";
        int primitifInt = 100;
        int anotherWrapper = 200;

        int intUmur = Integer.parseInt(str);
        System.out.print("string : " + intUmur);
        Integer wrapperInt = primitifInt; // Autoboxing
        int anotherPrimitif = anotherWrapper; // Unboxing
        
        // Enumerasi 
        enum Hari { 
            SENIN, SELASA, RABU, KAMIS, JUMAT, SABTU, MINGGU;
        }
        Hari hariIni = Hari.SENIN;
        if (hariIni == Hari.SENIN) {
            System.out.println("Hari ini adalah Senin!");
        }
        System.out.println("Besok adalah " + Hari.SELASA);
    }   
}
