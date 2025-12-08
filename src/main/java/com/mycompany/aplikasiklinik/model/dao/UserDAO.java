package com.mycompany.aplikasiklinik.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.aplikasiklinik.model.db.DatabaseConnection;
import com.mycompany.aplikasiklinik.model.entity.User;
import com.mycompany.aplikasiklinik.model.entity.UserRole;

/**
 * DAO (Data Access Object) untuk entitas User.
 * Bertanggung jawab atas semua operasi database terkait pengguna:
 * autentikasi, pembuatan, pembaruan, dan pengambilan data pengguna.
 */
public class UserDAO {

    /**
     * Melakukan autentikasi pengguna berdasarkan username dan password.
     * 
     * @param username nama pengguna.
     * @param password kata sandi (dalam bentuk plain text — pertimbangkan hashing di produksi).
     * @return objek User jika autentikasi berhasil; null jika gagal.
     */
    public User login(String username, String password) {
        String sql = "SELECT u.id, u.username, u.password, u.full_name, u.role, u.medical_specialty_id, u.is_active, u.consultation_fee FROM users u WHERE u.username = ? AND u.password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("full_name"),
                    UserRole.valueOf(rs.getString("role")),
                    rs.getInt("medical_specialty_id"),
                    rs.getBoolean("is_active"),
                    rs.getDouble("consultation_fee")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Menambahkan pengguna baru ke dalam database.
     * 
     * @param user objek User yang akan disimpan.
     * @return true jika berhasil; false jika gagal.
     */
    public boolean addUser(User user) {
        String sql = "INSERT INTO users (username, password, full_name, role, medical_specialty_id, is_active, consultation_fee) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getRole().name());
            stmt.setInt(5, user.getMedicalSpecialtyId());
            stmt.setBoolean(6, user.isActive());
            stmt.setDouble(7, user.getConsultationFee());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil semua pengguna dari database (password disembunyikan).
     * 
     * @return daftar semua pengguna.
     */
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT id, username, full_name, role, is_active, consultation_fee FROM users";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    "***",
                    rs.getString("full_name"),
                    UserRole.valueOf(rs.getString("role")),
                    0, // medicalSpecialtyId tidak diambil di sini — bisa diperluas jika perlu
                    rs.getBoolean("is_active"),
                    rs.getDouble("consultation_fee")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Mengambil pengguna berdasarkan ID-nya.
     * 
     * @param id ID pengguna.
     * @return objek User jika ditemukan; null jika tidak.
     */
    public User getUserById(int id) {
        String sql = "SELECT u.id, u.username, u.full_name, u.role, u.medical_specialty_id, u.is_active, u.consultation_fee FROM users u WHERE u.id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    "***",
                    rs.getString("full_name"),
                    UserRole.valueOf(rs.getString("role")),
                    rs.getInt("medical_specialty_id"),
                    rs.getBoolean("is_active"),
                    rs.getDouble("consultation_fee")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mengambil semua pengguna dengan role DOCTOR.
     * 
     * @return daftar dokter.
     */
    public List<User> getDoctors() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT id, username, full_name, medical_specialty_id, is_active, consultation_fee FROM users WHERE role = 'DOCTOR'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    "***",
                    rs.getString("full_name"),
                    UserRole.DOCTOR,
                    rs.getInt("medical_specialty_id"),
                    rs.getBoolean("is_active"),
                    rs.getDouble("consultation_fee")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Mengambil daftar dokter aktif berdasarkan ID spesialisasi medis (poli).
     * 
     * @param specialtyId ID poli/spesialisasi.
     * @return daftar dokter aktif dalam poli tersebut.
     */
    public List<User> getActiveDoctorsBySpecialtyId(int specialtyId) {
        String sql = "SELECT u.id, u.username, u.full_name, u.medical_specialty_id, u.is_active, u.consultation_fee FROM users u WHERE u.role = 'DOCTOR' AND u.medical_specialty_id = ? AND u.is_active = TRUE";
        List<User> doctors = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, specialtyId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User doctor = new User();
                doctor.setId(rs.getInt("id"));
                doctor.setUsername(rs.getString("username"));
                doctor.setFullName(rs.getString("full_name"));
                doctor.setMedicalSpecialtyId(rs.getInt("medical_specialty_id"));
                doctor.setRole(UserRole.DOCTOR);
                doctor.setActive(rs.getBoolean("is_active"));
                doctor.setConsultationFee(rs.getDouble("consultation_fee"));
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    /**
     * Memperbarui data pengguna yang sudah ada di database.
     * 
     * @param user objek User yang telah dimodifikasi.
     * @return true jika berhasil; false jika gagal.
     */
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET username = ?, full_name = ?, role = ?, medical_specialty_id = ?, is_active = ?, consultation_fee = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getFullName());
            stmt.setString(3, user.getRole().name());
            stmt.setInt(4, user.getMedicalSpecialtyId());
            stmt.setBoolean(5, user.isActive());
            stmt.setDouble(6, user.getConsultationFee());
            stmt.setInt(7, user.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Memperbarui status aktif (login/logout) pengguna.
     * Digunakan saat dokter login atau logout untuk mengatur ketersediaan.
     * 
     * @param userId ID pengguna.
     * @param isActive true jika login, false jika logout.
     * @return true jika berhasil; false jika gagal.
     */
    public boolean updateUserActiveStatus(int userId, boolean isActive) {
        String sql = "UPDATE users SET is_active = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, isActive);
            stmt.setInt(2, userId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mencari pengguna berdasarkan username (digunakan di panel admin untuk pencarian).
     * 
     * @param username nama pengguna yang dicari.
     * @return objek User jika ditemukan; null jika tidak.
     */
    public User getUserByUsername(String username) {
        String sql = "SELECT u.id, u.username, u.full_name, u.role, u.medical_specialty_id, u.is_active, u.consultation_fee FROM users u WHERE u.username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    "***",
                    rs.getString("full_name"),
                    UserRole.valueOf(rs.getString("role")),
                    rs.getInt("medical_specialty_id"),
                    rs.getBoolean("is_active"),
                    rs.getDouble("consultation_fee")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}