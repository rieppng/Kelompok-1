package com.mycompany.quiz_2_pbo_varel;

/**
 *
 * @author BlueBird
 */
public class Karyawan implements IDapatGaji{
    private String nama;
    private int nip;
    
    public Karyawan (String nama, int nip){
        this.nama = nama;
        this.nip = nip;
    }
    
    public String getNamaKaryawan(){
        return this.nama;
    }
    
    public void setNamaKaryawan(String nama){
        this.nama = nama;
    }
    
    public int getNipKaryawan(){
        return this.nip;
    }
    
    public void setNipKaryawan(int nip){
        this.nip = nip;
    }
    
    
    public void infoPlayer(){
        System.out.println("Nama Karyawan        : " + this.nama);
        System.out.println("NIP Karyawan         : " + this.nip);
        System.err.println("--------------------------------------");
    }
    
    @Override
    public void tampilkanSlipGaji();
}