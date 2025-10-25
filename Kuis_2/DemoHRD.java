/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.kuis_2;

/**
 *
 * @author User
 */
public class DemoHRD {

    public static void main(String[] args) {
        KaryawanTetap karyawan1 = new KaryawanTetap("Tyo", "1234", 5000000);
        KaryawanKontrak karyawan2 = new KaryawanKontrak("Radityo", "1235", 100000, 20);
        
        IDapatGaji[] daftarGaji = {karyawan1, karyawan2};
        
        
        for(int i = 0; i <=1; i++){
            daftarGaji[i].tampilkanSlipGaji();
            System.out.println("-------------------");
        }
    }
}
