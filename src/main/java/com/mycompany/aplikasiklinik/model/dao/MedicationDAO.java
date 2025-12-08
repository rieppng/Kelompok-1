package com.mycompany.aplikasiklinik.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.aplikasiklinik.model.db.DatabaseConnection;
import com.mycompany.aplikasiklinik.model.entity.Medication;

/**
 * DAO (Data Access Object) untuk entitas Medication.
 * Mengelola operasi database terkait data obat:
 * - Penambahan, pembaruan, dan penghapusan obat
 * - Pengambilan daftar obat
 * - Pembaruan stok saat resep diproses oleh apoteker
 */
public class MedicationDAO {

    /**
     * Menambahkan obat baru ke dalam database.
     * 
     * @param med objek Medication yang akan disimpan.
     * @return true jika berhasil; false jika gagal.
     */
    public boolean addMedication(Medication med) {
        String sql = "INSERT INTO medications (name, category, stock_quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, med.getName());
            stmt.setString(2, med.getCategory());
            stmt.setInt(3, med.getStockQuantity());
            stmt.setDouble(4, med.getPrice());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Memperbarui data obat yang sudah ada di database.
     * 
     * @param med objek Medication yang telah dimodifikasi.
     * @return true jika berhasil; false jika gagal.
     */
    public boolean updateMedication(Medication med) {
        String sql = "UPDATE medications SET name = ?, category = ?, stock_quantity = ?, price = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, med.getName());
            stmt.setString(2, med.getCategory());
            stmt.setInt(3, med.getStockQuantity());
            stmt.setDouble(4, med.getPrice());
            stmt.setInt(5, med.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus obat dari database berdasarkan ID-nya.
     * 
     * @param id ID obat yang akan dihapus.
     * @return true jika berhasil; false jika gagal.
     */
    public boolean deleteMedication(int id) {
        String sql = "DELETE FROM medications WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil semua obat dari database.
     * 
     * @return daftar semua obat yang tersedia.
     */
    public List<Medication> getAllMedications() {
        List<Medication> medications = new ArrayList<>();
        String sql = "SELECT id, name, category, stock_quantity, price FROM medications";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Medication med = new Medication(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("stock_quantity"),
                    rs.getDouble("price")
                );
                medications.add(med);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medications;
    }

    /**
     * Mengurangi stok obat saat resep diproses oleh apoteker.
     * 
     * @param medicationId ID obat yang stoknya akan dikurangi.
     * @param quantityToDeduct jumlah stok yang akan dikurangi.
     * @return true jika berhasil; false jika gagal (misal stok tidak cukup atau error SQL).
     */
    public boolean updateStock(int medicationId, int quantityToDeduct) {
        String sql = "UPDATE medications SET stock_quantity = stock_quantity - ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, quantityToDeduct);
            ps.setInt(2, medicationId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil data obat berdasarkan ID-nya.
     * Digunakan oleh apoteker untuk menampilkan nama dan detail obat saat memproses resep.
     * 
     * @param id ID obat.
     * @return objek Medication jika ditemukan; null jika tidak.
     */
    public Medication getMedicationById(int id) {
        String sql = "SELECT id, name, category, stock_quantity, price FROM medications WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Medication(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getInt("stock_quantity"),
                    rs.getDouble("price")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}