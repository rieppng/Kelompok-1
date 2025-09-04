/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum_3;

import java.util.Scanner;

/**
 *
 * @author User
 */
public class Latihan_3 {
    public static void main(String[] args) {
        System.out.println("## PROGRAM MENCARI VOLUME LIMAS PERSEGI ##");
        
        Scanner scanner = new Scanner(System.in);
        
        //input sisi alas limas
        System.out.print("Masukkan panjang sisi alas limas (cm): ");
        double sisi = scanner.nextDouble();
        
        //input tinggi limas
        System.out.print("Masukkan tinggi limas (cm): ");
        double tinggi = scanner.nextDouble();
        
        //rumus menghitung volume limas persegi
        double volume = sisi * sisi * tinggi / 3;
        
        //menampilkan output volume limas
        System.out.println("Volume limas persegi adalah: " + volume + " cm"); 
    }
}
