package com.mycompany.aplikasiklinik.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.aplikasiklinik.model.db.DatabaseConnection;
import com.mycompany.aplikasiklinik.model.entity.PrescriptionItem;

/**
 * DAO (Data Access Object) untuk entitas PrescriptionItem.
 * Mengelola operasi database terkait item resep obat, termasuk:
 * - Penyimpanan resep dengan status awal PENDING
 * - Pengambilan resep yang menunggu pemrosesan
 * - Pembaruan status (misal: ke PROCESSED)
 * - Integrasi dengan rekam medis dan antrian
 */
public class PrescriptionItemDAO {

    /**
     * Menyimpan satu item resep ke dalam database.
     * Status awal selalu diatur sebagai "PENDING".
     * 
     * @param item objek PrescriptionItem yang akan disimpan.
     * @return true jika berhasil; false jika gagal.
     */
    public boolean addPrescriptionItem(PrescriptionItem item) {
        String sql = "INSERT INTO prescription_items (medical_record_id, medication_id, quantity, price_at_time, instructions, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getMedicalRecordId());
            stmt.setInt(2, item.getMedicationId());
            stmt.setInt(3, item.getQuantity());
            stmt.setDouble(4, item.getPriceAtTime());
            stmt.setString(5, item.getInstructions());
            stmt.setString(6, item.getStatus());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil semua item resep yang statusnya PENDING,
     * diurutkan berdasarkan ID rekam medis (dari yang paling awal).
     * Digunakan oleh panel apoteker untuk menampilkan antrian resep.
     * 
     * @return daftar item resep yang menunggu pemrosesan.
     */
    public List<PrescriptionItem> getPendingPrescriptions() {
        List<PrescriptionItem> list = new ArrayList<>();
        String sql = "SELECT * FROM prescription_items WHERE status = 'PENDING' ORDER BY medical_record_id ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                PrescriptionItem item = new PrescriptionItem();
                item.setId(rs.getInt("id"));
                item.setMedicalRecordId(rs.getInt("medical_record_id"));
                item.setMedicationId(rs.getInt("medication_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPriceAtTime(rs.getDouble("price_at_time"));
                item.setInstructions(rs.getString("instructions"));
                item.setStatus(rs.getString("status"));
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Memperbarui status satu item resep berdasarkan ID-nya.
     * 
     * @param id ID item resep.
     * @param newStatus status baru (misal: "PROCESSED", "CANCELLED").
     * @return true jika berhasil; false jika gagal.
     */
    public boolean updateStatus(int id, String newStatus) {
        String sql = "UPDATE prescription_items SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Memperbarui status semua item resep yang terkait dengan satu rekam medis.
     * Digunakan saat dokter menyimpan resep dan semua item langsung dikirim ke apotek.
     * 
     * @param medicalRecordId ID rekam medis terkait.
     * @param newStatus status baru untuk semua item.
     * @return true jika setidaknya satu baris diperbarui; false jika gagal.
     */
    public boolean updateStatusByMedicalRecordId(int medicalRecordId, String newStatus) {
        String sql = "UPDATE prescription_items SET status = ? WHERE medical_record_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, medicalRecordId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil semua item resep yang terkait dengan satu rekam medis.
     * Digunakan saat menampilkan detail resep lengkap (misal di kwitansi).
     * 
     * @param medicalRecordId ID rekam medis.
     * @return daftar item resep.
     */
    public List<PrescriptionItem> getPrescriptionItemsByMedicalRecordId(int medicalRecordId) {
        List<PrescriptionItem> list = new ArrayList<>();
        String sql = "SELECT * FROM prescription_items WHERE medical_record_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, medicalRecordId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PrescriptionItem item = new PrescriptionItem();
                item.setId(rs.getInt("id"));
                item.setMedicalRecordId(rs.getInt("medical_record_id"));
                item.setMedicationId(rs.getInt("medication_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPriceAtTime(rs.getDouble("price_at_time"));
                item.setInstructions(rs.getString("instructions"));
                item.setStatus(rs.getString("status"));
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Mengambil ID appointment berdasarkan ID rekam medis.
     * Digunakan untuk integrasi antara resep, rekam medis, dan tagihan.
     * 
     * @param medicalRecordId ID rekam medis.
     * @return ID appointment terkait, atau -1 jika tidak ditemukan.
     */
    public int getAppointmentIdByMedicalRecordId(int medicalRecordId) {
        String sql = "SELECT appointment_id FROM medical_records WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, medicalRecordId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("appointment_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}