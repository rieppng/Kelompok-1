package com.mycompany.aplikasiklinik.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.aplikasiklinik.model.db.DatabaseConnection;
import com.mycompany.aplikasiklinik.model.entity.MedicalRecord;
import com.mycompany.aplikasiklinik.model.entity.MedicalRecordHistory;

/**
 * DAO (Data Access Object) untuk entitas MedicalRecord.
 * Mengelola operasi database terkait rekam medis pasien, termasuk:
 * - Penyimpanan hasil diagnosis dokter
 * - Pengambilan riwayat lengkap untuk admin
 * - Ekstraksi detail resep, layanan, dan tanda vital untuk laporan/kwitansi
 */
public class MedicalRecordDAO {

    /**
     * Menyimpan rekam medis baru ke dalam database dan mengembalikan ID-nya.
     * Digunakan setelah dokter menyelesaikan pemeriksaan.
     * 
     * @param mr objek MedicalRecord yang akan disimpan.
     * @return ID rekam medis yang baru dibuat, atau -1 jika gagal.
     */
    public int addMedicalRecord(MedicalRecord mr) {
        String sql = "INSERT INTO medical_records (appointment_id, symptoms, diagnosis, treatment) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, mr.getAppointmentId());
            stmt.setString(2, mr.getSymptoms());
            stmt.setString(3, mr.getDiagnosis());
            stmt.setString(4, mr.getTreatment());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Mengambil riwayat lengkap rekam medis untuk ditampilkan di panel admin.
     * Data diambil melalui JOIN antara medical_records, appointments, patients, users, dan medical_specialties.
     * 
     * @return daftar objek MedicalRecordHistory yang berisi data ringkas namun lengkap.
     */
    public List<MedicalRecordHistory> getAllHistory() {
        List<MedicalRecordHistory> list = new ArrayList<>();
        String sql = "SELECT mr.id, a.date, p.name AS patient_name, u.full_name AS doctor_name, " +
                     "ms.name AS poli_name, mr.diagnosis, mr.treatment " +
                     "FROM medical_records mr " +
                     "JOIN appointments a ON mr.appointment_id = a.id " +
                     "JOIN patients p ON a.patient_id = p.id " +
                     "JOIN users u ON a.doctor_id = u.id " +
                     "LEFT JOIN medical_specialties ms ON u.medical_specialty_id = ms.id " +
                     "ORDER BY a.date DESC, mr.id DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new MedicalRecordHistory(
                    rs.getInt("id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getString("patient_name"),
                    rs.getString("doctor_name"),
                    rs.getString("poli_name") != null ? rs.getString("poli_name") : "Umum",
                    rs.getString("diagnosis"),
                    rs.getString("treatment")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Mengambil daftar obat dalam format teks berdasarkan ID rekam medis.
     * Digunakan untuk menampilkan resep di kwitansi atau laporan.
     * 
     * @param medicalRecordId ID rekam medis.
     * @return string berisi daftar obat dengan instruksi, atau pesan default jika kosong.
     */
    public String getPrescriptionDetails(int medicalRecordId) {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT m.name, pi.quantity, pi.instructions " +
                     "FROM prescription_items pi " +
                     "JOIN medications m ON pi.medication_id = m.id " +
                     "WHERE pi.medical_record_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, medicalRecordId);
            ResultSet rs = ps.executeQuery();

            int count = 1;
            while (rs.next()) {
                sb.append(count++).append(". ")
                  .append(rs.getString("name")).append(" (")
                  .append(rs.getInt("quantity")).append(") - ")
                  .append(rs.getString("instructions")).append("\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sb.length() == 0 ? "- Tidak ada resep obat -" : sb.toString();
    }

    /**
     * Mengambil daftar layanan tambahan dalam format teks berdasarkan ID appointment.
     * Digunakan untuk menampilkan layanan di kwitansi.
     * 
     * @param appointmentId ID antrian.
     * @return string berisi daftar layanan, atau pesan default jika kosong.
     */
    public String getServiceDetails(int appointmentId) {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT sf.name, aps.quantity " +
                     "FROM appointment_services aps " +
                     "JOIN service_fees sf ON aps.service_fee_id = sf.id " +
                     "WHERE aps.appointment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            ResultSet rs = ps.executeQuery();

            int count = 1;
            while (rs.next()) {
                sb.append(count++).append(". ")
                  .append(rs.getString("name")).append(" (")
                  .append(rs.getInt("quantity")).append("x)\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sb.length() == 0 ? "- Tidak ada layanan tambahan -" : sb.toString();
    }

    /**
     * Mengambil data tanda vital pasien (berat, tinggi, tekanan darah) pada saat appointment.
     * 
     * @param appointmentId ID antrian.
     * @return string berisi data tanda vital, atau pesan error jika tidak ditemukan.
     */
    public String getPatientVitals(int appointmentId) {
        String sql = "SELECT weight, height, blood_pressure FROM appointments WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointmentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return "Berat: " + rs.getDouble("weight") + " kg, Tinggi: " +
                       rs.getDouble("height") + " cm, Tensi: " +
                       rs.getString("blood_pressure");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Data vital tidak ditemukan";
    }

    /**
     * Helper: mengambil ID appointment berdasarkan ID rekam medis.
     * 
     * @param medicalRecordId ID rekam medis.
     * @return ID appointment terkait, atau -1 jika tidak ditemukan.
     */
    public int getAppointmentIdByMedicalRecord(int medicalRecordId) {
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