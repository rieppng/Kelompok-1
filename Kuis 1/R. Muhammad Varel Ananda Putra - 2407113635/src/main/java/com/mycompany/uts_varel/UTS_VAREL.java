package com.mycompany.uts_varel;
import java.util.Scanner;
/**
 *
 * @author BlueBird
 */
public class UTS_VAREL {
    
    public static void main(String[] args) {
        //NO 1
        
        //Input Data
        Scanner scanner = new Scanner (System.in);
        
        System.out.print("Nama Lengkap : ");
        String nama = scanner.nextLine();
        
        System.out.print("NIM : ");
        String nim = scanner.nextLine();
        
        System.out.print("Program Studi : ");
        String prodi = scanner.nextLine();
        
        System.out.print("Tahun Masuk : ");
        int tahunMasuk = scanner.nextInt();
        
        //Tampilkan Menu
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Biodata Mahasiswa");
        System.out.println("Nama mahasiswa adalah " + nama + ", dengan NIM " + nim + " berasal dari Prodi " + prodi + " angkatan " + tahunMasuk);
        
        
        Mhs1.tampilkanInpo = Mhs1
        Mhs2.tampilkanInpo = Mhs2;

        
        // Enumerasi 
        enum Prodi { 
            S1 TI, S1 TE, D3 TE;
        }
        Prodi prodiSaya = Prodi.S1 TI;
        if (prodiSaya == Prodi.S1 TI) {
            System.out.println("Saya dari Prodi S1 TI");
        }
        System.out.println("Saya dari Prodi " + Prodi.D3 TE);
       
    // NO 2
        System.out.println("========================================================================");
        System.out.println("List Bangun Ruang ");
        System.out.println("1. Kubus ");
        System.out.println("2. Bola ");
        System.out.println("3. Limas Segiempat ");
        System.out.println("4. Keluar ");
    
        System.out.print("Pilih Menu : ");
        int angka = scanner.nextInt();
        
                switch(angka) {
            case "1":
                System.out.println("Anda memilih Volume Kubus");
                break;
            case "2":
                System.out.println("Anda memilih Volume Bola");
                break;
            case "3":
                System.out.println("Anda memilih Volume Segiempat");
                break;
            default:
                System.out.println("Bangun yang anda pilih tidak tersedia");
        }
        
        //Method Overloading
        
    
    }
}


