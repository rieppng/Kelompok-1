/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pbo;

/**
 *
 * @author User
 */
public class BiodataTyo {

    public static void main(String[] args) {
        String nama = "Muhamad Radityo Pratama", nim = "2407110908", 
             alamat = "Merpati Sakti";
        int umur = 19;
        char golD = 'O';
        double ipSemester[] = {3.8, 3.7, 3.9};
        byte ipByte1 = (byte) ipSemester[0];

        System.out.println("Nama saya " + nama + " dengan NIM: " + nim + ".");
        System.out.println("Saya tinggal di jalan " + alamat + ".");
        System.out.println("Saya berumur " + umur + " tahun" + " dengan Gol darah: " + golD);
        System.out.println("IP semester saya:");
        for (int i = 0; i < 3; i++) {
            System.out.println("IP semester " + (i + 1)+": " + ipSemester[i]);
        }
        System.out.println("IP semester 1 dalam byte: " + ipByte1);
    }
}
