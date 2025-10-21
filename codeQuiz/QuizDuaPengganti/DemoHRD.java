public class DemoHRD {
    public static void main(String[] args) throws Exception {
        Karyawan karyawan ;
        IDapatGaji[] daftarGaji = new IDapatGaji[2];

        daftarGaji[0] = new KaryawanTetap("Arief", "12345", 5000000);
        daftarGaji[1] = new KaryawanKontrak("Budi", "67890", 200000, 20);

        for (IDapatGaji iDapatGaji : daftarGaji) {
            System.out.println("=".repeat(100));
            iDapatGaji.tampilkanSlipGaji();
        }
    }
}
