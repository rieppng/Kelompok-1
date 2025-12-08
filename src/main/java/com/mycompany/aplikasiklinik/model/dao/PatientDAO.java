package com.mycompany.aplikasiklinik.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.aplikasiklinik.model.db.DatabaseConnection;
import com.mycompany.aplikasiklinik.model.entity.Patient;

/**
 * DAO (Data Access Object) untuk entitas Patient.
 * Mengelola operasi database terkait data pasien:
 * - Pendaftaran pasien baru
 * - Pencarian berdasarkan ID atau NIK
 * - (Saat ini) pengambilan semua pasien dikosongkan untuk kinerja
 */
public class PatientDAO {

    /**
     * Menambahkan pasien baru ke dalam database dan mengembalikan ID-nya.
     * 
     * @param p objek Patient yang akan disimpan.
     * @return ID pasien yang baru dibuat, atau 0 jika gagal.
     */
    public int addPatient(Patient p) {
        String sql = "INSERT INTO patients (name, nik, birth_date, gender, address, phone_number) VALUES (?, ?, ?, ?, ?, ?)";
        int generatedId = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, p.getName());
            stmt.setString(2, p.getNik());
            stmt.setDate(3, java.sql.Date.valueOf(p.getBirthDate()));
            stmt.setString(4, p.getGender());
            stmt.setString(5, p.getAddress());
            stmt.setString(6, p.getPhoneNumber());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    /**
     * Mengambil data pasien berdasarkan ID-nya.
     * 
     * @param id ID pasien.
     * @return objek Patient jika ditemukan; null jika tidak.
     */
    public Patient getPatientById(int id) {
        String sql = "SELECT * FROM patients WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Patient p = new Patient();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setNik(rs.getString("nik"));
                p.setBirthDate(rs.getDate("birth_date").toLocalDate());
                p.setGender(rs.getString("gender"));
                p.setAddress(rs.getString("address"));
                p.setPhoneNumber(rs.getString("phone_number"));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mencari pasien berdasarkan Nomor Induk Kependudukan (NIK).
     * Digunakan saat pendaftaran ulang atau pencarian cepat.
     * 
     * @param nik Nomor Induk Kependudukan pasien.
     * @return objek Patient jika ditemukan; null jika tidak.
     */
    public Patient getPatientByNik(String nik) {
        String sql = "SELECT * FROM patients WHERE nik = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nik);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Patient p = new Patient();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setNik(rs.getString("nik"));
                p.setBirthDate(rs.getDate("birth_date").toLocalDate());
                p.setGender(rs.getString("gender"));
                p.setAddress(rs.getString("address"));
                p.setPhoneNumber(rs.getString("phone_number"));
                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mengambil semua pasien dari database.
     * Saat ini dikembalikan sebagai daftar kosong untuk menghindari beban kinerja.
     * (Bisa diimplementasikan penuh jika diperlukan di fitur admin.)
     * 
     * @return daftar kosong (placeholder).
     */
    public List<Patient> getAllPatients() {
        return new ArrayList<>();
    }
}