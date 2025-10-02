package com.mycompany.praktikum_6;

/**
 *
 * @author Varel
 */
public class Rekening {
    private String namaNasabah;
    private final String nomorRekening;
    private String pinRekening;
    private double saldoRekening;
    private final Bank cabang;
    
    public Rekening(Bank cabang, String namaNasabah, String nomorRekening,
                    String pinRekening, double saldoRekening){
        this.cabang = cabang;
        this.namaNasabah = namaNasabah;
        this.nomorRekening = nomorRekening;
        this.pinRekening = pinRekening;
        this.saldoRekening = saldoRekening;
        Bank.tambahNasabah();
    }
    
    public Bank getCabang(){
        return cabang;
    }
    
    public String getNamaNasabah(){
        return namaNasabah;
    }
    
    public void setNamaNasabah(String namaNasabahBaru){
        this.namaNasabah = namaNasabahBaru;
    }
    
    public String getNomorRekening(){
        return nomorRekening;
    }
    
    public String getPinRekening(){
        return pinRekening;
    }
    
    public void setPinRekening(String pinRekeningBaru){
        if(pinRekeningBaru.length() == 6){
            this.pinRekening = pinRekeningBaru;
            System.out.println("Pin berhasil diperbarui menjadi " + this.pinRekening);
        }
        else {
            System.out.println("Pin " + pinRekeningBaru + " tidak valid, pin harus 6 digit");
        }
    }
    
    public double getSaldo() {
        return saldoRekening;
    }

    // method tambahan: setor dan tarik
    public void setor(double jumlah) {
        System.out.println("Melakukan setoran senilai Rp" + jumlah);
        saldoRekening += jumlah;
    }

    public void tarik(double jumlah) {
        if (saldoRekening >= jumlah) {
            System.out.println("Melakukan penarikan saldo senilai Rp" + jumlah);
            saldoRekening -= jumlah;
        } else {
            System.out.println("Saldo tidak cukup!");
        }
    }
}
