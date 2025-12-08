package com.mycompany.aplikasiklinik.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.aplikasiklinik.model.db.DatabaseConnection;
import com.mycompany.aplikasiklinik.model.entity.MedicalSpecialty;

/**
 * DAO (Data Access Object) untuk entitas MedicalSpecialty.
 * Mengelola operasi database terkait spesialisasi medis (poli),
 * seperti "Umum", "Gigi", "Mata", dll.
 */
public class MedicalSpecialtyDAO {

    /**
     * Menambahkan spesialisasi medis baru ke dalam database.
     * 
     * @param specialty objek MedicalSpecialty yang akan disimpan.
     * @return true jika berhasil; false jika gagal.
     */
    public boolean addSpecialty(MedicalSpecialty specialty) {
        String sql = "INSERT INTO medical_specialties (name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, specialty.getName());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Memperbarui nama spesialisasi medis yang sudah ada.
     * 
     * @param specialty objek MedicalSpecialty yang telah dimodifikasi.
     * @return true jika berhasil; false jika gagal.
     */
    public boolean updateSpecialty(MedicalSpecialty specialty) {
        String sql = "UPDATE medical_specialties SET name = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, specialty.getName());
            stmt.setInt(2, specialty.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Menghapus spesialisasi medis dari database berdasarkan ID-nya.
     * 
     * @param id ID spesialisasi yang akan dihapus.
     * @return true jika berhasil; false jika gagal.
     */
    public boolean deleteSpecialty(int id) {
        String sql = "DELETE FROM medical_specialties WHERE id = ?";
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
     * Mengambil spesialisasi medis berdasarkan ID-nya.
     * 
     * @param id ID spesialisasi.
     * @return objek MedicalSpecialty jika ditemukan; null jika tidak.
     */
    public MedicalSpecialty getSpecialtyById(int id) {
        String sql = "SELECT id, name FROM medical_specialties WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new MedicalSpecialty(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mengambil semua spesialisasi medis dari database.
     * 
     * @return daftar semua poli/spesialisasi yang tersedia.
     */
    public List<MedicalSpecialty> getAllSpecialties() {
        List<MedicalSpecialty> specialties = new ArrayList<>();
        String sql = "SELECT id, name FROM medical_specialties";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                specialties.add(new MedicalSpecialty(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialties;
    }
}