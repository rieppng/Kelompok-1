public class KaryawanKontrak extends Karyawan implements IDapatGaji {
    private double upahHarian;
    private int jumlahHariMasuk;
    private double totalGaji;

    public void setTotalGaji(double totalGaji) {
        this.totalGaji = getUpahHarian() * getJumlahHariMasuk();
    }

    public KaryawanKontrak(String nama, String nip) {
        super(nama, nip);
    }

    public int getJumlahHariMasuk() {
        return jumlahHariMasuk;
    }

    public double getUpahHarian() {
        return upahHarian;
    }

    public double getTotalGaji() {
        return totalGaji;
    }

    public void setJumlahHariMasuk(int jumlahHariMasuk) {
        this.jumlahHariMasuk = jumlahHariMasuk;
    }

    public void setUpahHarian(double upahHarian) {
        this.upahHarian = upahHarian;
    }
    
    public KaryawanKontrak(String nama, String nip, double upahHarian, int jumlahHariMasuk) {
        super(nama, nip);
        this.upahHarian = upahHarian;
        this.jumlahHariMasuk = jumlahHariMasuk;
        this.totalGaji = upahHarian * jumlahHariMasuk;
    }

    @Override
    public void tampilkanSlipGaji() {
        System.out.println("Nama: " + getNama());
        System.out.println("NIP: " + getNip());
        System.out.println("Status : Karyawan Kontrak");
        System.out.println("Total Gaji : " + getTotalGaji());
    }
}
