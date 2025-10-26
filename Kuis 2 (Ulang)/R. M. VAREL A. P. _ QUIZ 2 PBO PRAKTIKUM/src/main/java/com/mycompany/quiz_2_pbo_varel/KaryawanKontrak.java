package com.mycompany.quiz_2_pbo_varel;

/**
 *
 * @author BlueBird
 */
public class KaryawanKontrak extends Karyawan implements IDapatGaji {
    private double upahHarian;
    private int jumlahHariMasuk;
    
    public Goblin (String nama, int nip, double upahHarian, int jumlahHariMasuk){
        super (nama, nip);
        this.upahHarian = upahHarian;
        this.jumlahHariMasuk = jumlahHariMasuk;
    }    
    
    @Override
    public void tampilkanSlipGaji();
}
