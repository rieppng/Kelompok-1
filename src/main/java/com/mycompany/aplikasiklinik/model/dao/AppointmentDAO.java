package com.mycompany.aplikasiklinik.model.dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.aplikasiklinik.model.db.DatabaseConnection;
import com.mycompany.aplikasiklinik.model.entity.Appointment;

/**
 * DAO (Data Access Object) untuk entitas Appointment.
 * Mengelola operasi database terkait antrian pasien, termasuk:
 * - Pendaftaran antrian baru
 * - Pengelolaan nomor antrian otomatis
 * - Pembaruan status alur pemeriksaan
 * - Pengambilan daftar antrian berdasarkan dokter, tanggal, atau status
 */
public class AppointmentDAO {

    /**
     * Menambahkan appointment (antrian) baru ke dalam database.
     * Termasuk referensi ke biaya konsultasi melalui consultation_service_fee_id.
     * 
     * @param app objek Appointment yang akan disimpan.
     * @return true jika berhasil; false jika gagal.
     */
    public boolean addAppointment(Appointment app) {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, date, queue_number, weight, height, blood_pressure, status, consultation_service_fee_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, app.getPatientId());
            stmt.setInt(2, app.getDoctorId());
            stmt.setDate(3, java.sql.Date.valueOf(app.getDate()));
            stmt.setInt(4, app.getQueueNumber());
            stmt.setDouble(5, app.getWeight());
            stmt.setDouble(6, app.getHeight());
            stmt.setString(7, app.getBloodPressure());
            stmt.setString(8, app.getStatus());
            stmt.setInt(9, app.getConsultationServiceFeeId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil daftar antrian pasien yang sedang menunggu atau dalam konsultasi
     * untuk dokter tertentu pada tanggal tertentu.
     * Digunakan di panel dokter untuk menampilkan antrian aktif.
     * 
     * @param doctorId ID dokter.
     * @param date tanggal kunjungan.
     * @return daftar appointment dengan status WAITING atau IN_CONSULTATION.
     */
    public List<Appointment> getWaitingListByDoctor(int doctorId, LocalDate date) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT id, patient_id, doctor_id, date, queue_number, weight, height, blood_pressure, status " +
                     "FROM appointments " +
                     "WHERE doctor_id = ? AND date = ? AND status IN ('WAITING', 'IN_CONSULTATION') " +
                     "ORDER BY queue_number ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);
            stmt.setDate(2, java.sql.Date.valueOf(date));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Appointment app = new Appointment(
                    rs.getInt("id"),
                    rs.getInt("patient_id"),
                    rs.getInt("doctor_id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getInt("queue_number"),
                    rs.getDouble("weight"),
                    rs.getDouble("height"),
                    rs.getString("status")
                );
                app.setBloodPressure(rs.getString("blood_pressure"));
                list.add(app);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Menghitung nomor antrian berikutnya untuk dokter pada tanggal tertentu.
     * 
     * @param doctorId ID dokter.
     * @param date tanggal kunjungan.
     * @return nomor antrian berikutnya (maksimum saat ini + 1).
     */
    public int getNextQueueNumber(int doctorId, LocalDate date) {
        String sql = "SELECT MAX(queue_number) AS max_queue FROM appointments WHERE doctor_id = ? AND date = ?";
        int maxQueue = 0;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);
            stmt.setDate(2, Date.valueOf(date));

            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getObject("max_queue") != null) {
                maxQueue = rs.getInt("max_queue");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxQueue + 1;
    }

    /**
     * Mengambil semua appointment pada tanggal tertentu yang belum selesai (status != COMPLETED).
     * Digunakan untuk ringkasan harian di panel resepsionis atau admin.
     * 
     * @param date tanggal kunjungan.
     * @return daftar appointment yang belum selesai.
     */
    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE date = ? AND status != 'COMPLETED' ORDER BY queue_number ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Appointment app = new Appointment();
                app.setId(rs.getInt("id"));
                app.setPatientId(rs.getInt("patient_id"));
                app.setDoctorId(rs.getInt("doctor_id"));
                app.setDate(rs.getDate("date").toLocalDate());
                app.setQueueNumber(rs.getInt("queue_number"));
                app.setWeight(rs.getDouble("weight"));
                app.setHeight(rs.getDouble("height"));
                app.setStatus(rs.getString("status"));
                list.add(app);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Memperbarui status appointment berdasarkan ID-nya.
     * Digunakan untuk mengatur alur: WAITING → IN_CONSULTATION → IN_PHARMACY → IN_CASHIER → COMPLETED.
     * 
     * @param id ID appointment.
     * @param status status baru.
     * @return true jika berhasil; false jika gagal.
     */
    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE appointments SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mereset status semua pasien yang sedang dalam konsultasi (IN_CONSULTATION)
     * milik dokter tertentu kembali ke WAITING saat dokter logout.
     * Mencegah pasien "terjebak" dalam status IN_CONSULTATION.
     * 
     * @param doctorId ID dokter yang logout.
     * @return true jika setidaknya satu status direset; false jika tidak ada atau gagal.
     */
    public boolean resetDoctorConsultationStatus(int doctorId) {
        String sql = "UPDATE appointments SET status = 'WAITING' WHERE doctor_id = ? AND status = 'IN_CONSULTATION'";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil appointment berdasarkan ID-nya.
     * 
     * @param id ID appointment.
     * @return objek Appointment jika ditemukan; null jika tidak.
     */
    public Appointment getAppointmentById(int id) {
        String sql = "SELECT id, patient_id, doctor_id, date, queue_number, weight, height, blood_pressure, status, consultation_service_fee_id " +
                     "FROM appointments WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Appointment app = new Appointment();
                app.setId(rs.getInt("id"));
                app.setPatientId(rs.getInt("patient_id"));
                app.setDoctorId(rs.getInt("doctor_id"));
                app.setDate(rs.getDate("date").toLocalDate());
                app.setQueueNumber(rs.getInt("queue_number"));
                app.setWeight(rs.getDouble("weight"));
                app.setHeight(rs.getDouble("height"));
                app.setBloodPressure(rs.getString("blood_pressure"));
                app.setStatus(rs.getString("status"));
                app.setConsultationServiceFeeId(rs.getInt("consultation_service_fee_id"));
                return app;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}