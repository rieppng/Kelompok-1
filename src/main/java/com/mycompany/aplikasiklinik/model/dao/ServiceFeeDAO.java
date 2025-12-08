package com.mycompany.aplikasiklinik.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.aplikasiklinik.model.db.DatabaseConnection;
import com.mycompany.aplikasiklinik.model.entity.ServiceFee;

/**
 * DAO (Data Access Object) untuk entitas ServiceFee.
 * Mengelola operasi database terkait layanan tambahan klinik,
 * seperti biaya vaksinasi, laboratorium, atau tindakan medis non-konsultasi.
 */
public class ServiceFeeDAO {

    /**
     * Mengambil semua layanan tambahan dari database.
     * 
     * @return daftar semua layanan yang tersedia.
     */
    public List<ServiceFee> getAllServiceFees() {
        List<ServiceFee> fees = new ArrayList<>();
        String sql = "SELECT id, name, price FROM service_fees";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                fees.add(new ServiceFee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gunakan logger di lingkungan produksi
        }
        return fees;
    }

    /**
     * Mengambil layanan berdasarkan ID-nya.
     * 
     * @param id ID layanan.
     * @return objek ServiceFee jika ditemukan; null jika tidak.
     */
    public ServiceFee getServiceFeeById(int id) {
        String sql = "SELECT id, name, price FROM service_fees WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new ServiceFee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Memperbarui data layanan yang sudah ada di database.
     * 
     * @param fee objek ServiceFee yang telah dimodifikasi.
     * @return true jika berhasil; false jika gagal.
     */
    public boolean updateServiceFee(ServiceFee fee) {
        String sql = "UPDATE service_fees SET name = ?, price = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fee.getName());
            stmt.setDouble(2, fee.getPrice());
            stmt.setInt(3, fee.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menambahkan layanan baru ke dalam database.
     * 
     * @param fee objek ServiceFee yang akan disimpan.
     * @return true jika berhasil; false jika gagal.
     */
    public boolean addServiceFee(ServiceFee fee) {
        String sql = "INSERT INTO service_fees (name, price) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fee.getName());
            stmt.setDouble(2, fee.getPrice());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus layanan dari database berdasarkan ID-nya.
     * 
     * @param id ID layanan yang akan dihapus.
     * @return true jika berhasil; false jika gagal.
     */
    public boolean deleteServiceFee(int id) {
        String sql = "DELETE FROM service_fees WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}