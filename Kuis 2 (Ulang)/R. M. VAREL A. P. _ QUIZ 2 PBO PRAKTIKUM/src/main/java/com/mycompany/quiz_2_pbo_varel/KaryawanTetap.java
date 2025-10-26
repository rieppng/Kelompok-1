package com.mycompany.quiz_2_pbo_varel;

/**
 *
 * @author BlueBird
 */
public class KaryawanTetap extends Karyawan implements IDapatGaji {
    private double gajiBulanan;
    
    public Goblin (String nama, int nip, double gajiBulanan){
        super (nama, nip);
        this.gajiBulanan = gajiBulanan;
    }
    
    @Override
    public void tampilkanSlipGaji();
}
