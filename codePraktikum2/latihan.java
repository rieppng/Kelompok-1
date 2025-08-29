package com.mycompany.praktikum_2;

/**
 *
 * @author arief
 */
public class latihan {
    public static void main(String[] args) {
        String nama = "Aulia Arief", alamat = "Jalan Bakti Sari";
        int umur = 20;
        double[] ipSemester = {3.75, 3.78, 4.0};
        char golDarah = 'O';
        final long nim = 2407112796L;
        byte byteSemester1 = (byte) ipSemester[0];

        System.out.println("Nama saya " + nama + " dengan NIM: " + nim + ".");
        System.out.println("Saya tinggal di " + alamat + ".");
        System.out.println("Saya berumur " + umur + " tahun.");
        System.out.println("Saya memiliki golongan darah : " + golDarah);
        System.out.println("IP semester saya : ");
        for (int i = 0; i < 3; i++) {
            System.out.println("IP semester " + (i + 1) + ":" + ipSemester[i]);
        }
        System.out.println("IP semester 1 dalam byte: " + byteSemester1);
    }
}