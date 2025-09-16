package com.mycompany.praktikum_5;

/**
 *
 * @author Varel
 */
public class Tugas_Praktikum_5 {
    public static void main(String[] args) {
        Mahasiswa m1 = new Mahasiswa();                  
        Mahasiswa m2 = new Mahasiswa("Varel");             
        Mahasiswa m3 = new Mahasiswa("Jepri", 20, "2407113635");
        
        m1.tampilkanInpo();
        m2.tampilkanInpo();
        m3.tampilkanInpo();


        m1.tampilkanInpo("Aurel");
        m1.tampilkanInpo("Widya", 20);
        m1.tampilkanInpo("Puput", 19, "2407113876");
    }
}

