package com.mycompany.praktikum_2;

/**
 *
 * @author varel
 */
public class Latihan {
    public static void main(String[] args) {
        /*
            *menampilkan tipe data
        */
        // Inisiasi dan deklarasi variabel
        String nama = "Raja Muhammad Varel Ananda Putra", alamat = "jalan dr.susilo";
        int umur = 19;
        char golDarah = 'B';
        double[] ipsemester = {3.6, 3.7, 4.0};
        final long nim = 2407113635L;
        byte byteipSemester1 = (byte) ipsemester[0];

        System.out.println("Nama saya " + nama + " dengan NIM: " + nim + ".");
        System.out.println("Saya tinggal di " + alamat + ".");
        System.out.println("Saya berumur " + umur + " tahun.");
        System.out.println("Saya memiliki golongan darah : " + golDarah);
        System.out.println("IP Semester saya adalah : ");

        for (int i = 0; i < 3; i++) {
            System.out.println("IP semester " + (i + 1) + ": " + ipsemester[i]);
        }

        System.out.println("IP semester 1 dalam byte: " + byteipSemester1);
    }
}
