package com.mycompany.praktikum_5;

/**
 *
 * @author Varel
 */
public class Tugas_Praktikum_5 {
    public static void main(String[] args) {
        Mahasiswa m1 = new Mahasiswa();                  
        Mahasiswa m2 = new Mahasiswa("Varel", 20, "2407113635");             
        Mahasiswa m3 = new Mahasiswa();
        
        m1.tampilkanInpo("Puput", 19, "2407113536");
        m2.tampilkanInpo();
        m3.tampilkanInpo();
    }
}