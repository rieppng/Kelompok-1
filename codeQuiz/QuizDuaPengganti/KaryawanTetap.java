public class KaryawanTetap extends Karyawan implements IDapatGaji {
    private double gajiBulanan;

    public KaryawanTetap(String nama, String nip, double gajiBulanan) {
        super(nama, nip);
        this.gajiBulanan = gajiBulanan;
    }

    public double getGajiBulanan() {
        return gajiBulanan;
    }

    public void setGajiBulanan(double gajiBulanan) {
        this.gajiBulanan = gajiBulanan;
    }

    @Override
    public void tampilkanSlipGaji() {
        
        System.out.println("Nama: " + getNama());
        System.out.println("NIP: " + getNip());
        System.out.println("Status : Karyawan Tetap");
        System.out.println("Gaji Bulanan: " + getGajiBulanan());
    }
}