package com.mycompany.aplikasiklinik.model.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.aplikasiklinik.model.db.DatabaseConnection;
import com.mycompany.aplikasiklinik.model.entity.Bill;

/**
 * DAO (Data Access Object) untuk entitas Bill.
 * Mengelola operasi database terkait tagihan pasien, termasuk:
 * - Pembuatan tagihan setelah pemeriksaan selesai
 * - Penandaan pembayaran (lunas)
 * - Pengambilan tagihan berdasarkan status (lunas/belum lunas)
 */
public class BillDAO {

    /**
     * Menandai tagihan sebagai telah dibayar dan mencatat waktu pembayaran.
     * 
     * @param billId ID tagihan yang akan ditandai lunas.
     * @return true jika berhasil; false jika gagal.
     */
    public boolean markAsPaid(int billId) {
        String sql = "UPDATE bills SET is_paid = true, payment_date = NOW() WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, billId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil tagihan berdasarkan ID appointment terkait.
     * Digunakan saat kasir menampilkan tagihan untuk pasien yang sedang di antrian kasir.
     * 
     * @param appointmentId ID antrian.
     * @return objek Bill jika ditemukan; null jika tidak.
     */
    public Bill getBillByAppointmentId(int appointmentId) {
        String sql = "SELECT * FROM bills WHERE appointment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, appointmentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setAppointmentId(rs.getInt("appointment_id"));
                bill.setTotalMedicationCost(rs.getDouble("total_medication_cost"));
                bill.setTotalServiceCost(rs.getDouble("total_service_cost"));
                bill.setConsultationFee(rs.getDouble("consultation_fee"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setPaid(rs.getBoolean("is_paid"));
                if (rs.getTimestamp("payment_date") != null) {
                    bill.setPaymentDate(rs.getTimestamp("payment_date").toLocalDateTime());
                }
                return bill;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mengambil semua tagihan yang belum dibayar.
     * Digunakan di panel kasir untuk menampilkan daftar pasien yang perlu membayar.
     * 
     * @return daftar tagihan dengan status belum lunas.
     */
    public List<Bill> getUnpaidBills() {
        List<Bill> unpaidBills = new ArrayList<>();
        String sql = "SELECT id, appointment_id, total_medication_cost, total_service_cost, consultation_fee, " +
                     "(total_medication_cost + total_service_cost + consultation_fee) AS calculated_total " +
                     "FROM bills WHERE is_paid = 0";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setAppointmentId(rs.getInt("appointment_id"));
                bill.setTotalMedicationCost(rs.getDouble("total_medication_cost"));
                bill.setTotalServiceCost(rs.getDouble("total_service_cost"));
                bill.setConsultationFee(rs.getDouble("consultation_fee"));
                bill.setTotalAmount(rs.getDouble("calculated_total"));
                bill.setPaid(false);
                unpaidBills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unpaidBills;
    }

    /**
     * Mengambil daftar tagihan yang telah dibayar (maksimal 20 terbaru).
     * Digunakan untuk historis pembayaran di panel kasir atau admin.
     * 
     * @return daftar tagihan yang sudah lunas, diurutkan berdasarkan waktu pembayaran terbaru.
     */
    public List<Bill> getPaidBills() {
        String sql = "SELECT * FROM bills WHERE is_paid = 1 ORDER BY payment_date DESC LIMIT 20";
        List<Bill> bills = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setAppointmentId(rs.getInt("appointment_id"));
                bill.setTotalMedicationCost(rs.getDouble("total_medication_cost"));
                bill.setTotalServiceCost(rs.getDouble("total_service_cost"));
                bill.setConsultationFee(rs.getDouble("consultation_fee"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setPaid(rs.getBoolean("is_paid"));
                if (rs.getTimestamp("payment_date") != null) {
                    bill.setPaymentDate(rs.getTimestamp("payment_date").toLocalDateTime());
                }
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    /**
     * Mengambil tagihan berdasarkan ID tagihan.
     * 
     * @param id ID tagihan.
     * @return objek Bill jika ditemukan; null jika tidak.
     */
    public Bill getBillById(int id) {
        String sql = "SELECT * FROM bills WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setAppointmentId(rs.getInt("appointment_id"));
                bill.setTotalMedicationCost(rs.getDouble("total_medication_cost"));
                bill.setTotalServiceCost(rs.getDouble("total_service_cost"));
                bill.setConsultationFee(rs.getDouble("consultation_fee"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setPaid(rs.getBoolean("is_paid"));
                if (rs.getTimestamp("payment_date") != null) {
                    bill.setPaymentDate(rs.getTimestamp("payment_date").toLocalDateTime());
                }
                return bill;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mengambil semua tagihan (lunas dan belum lunas).
     * Digunakan di panel admin untuk laporan lengkap.
     * 
     * @return daftar semua tagihan, diurutkan: belum lunas dulu, lalu lunas, berdasarkan ID.
     */
    public List<Bill> getAllBills() {
        String sql = "SELECT * FROM bills ORDER BY is_paid ASC, id ASC";
        List<Bill> bills = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setAppointmentId(rs.getInt("appointment_id"));
                bill.setTotalMedicationCost(rs.getDouble("total_medication_cost"));
                bill.setTotalServiceCost(rs.getDouble("total_service_cost"));
                bill.setConsultationFee(rs.getDouble("consultation_fee"));
                bill.setTotalAmount(rs.getDouble("total_amount"));
                bill.setPaid(rs.getBoolean("is_paid"));
                if (rs.getTimestamp("payment_date") != null) {
                    bill.setPaymentDate(rs.getTimestamp("payment_date").toLocalDateTime());
                }
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    /**
     * Membuat tagihan baru di database.
     * Tagihan dibuat setelah dokter menyimpan rekam medis dan sistem menghitung total biaya.
     * 
     * @param bill objek Bill yang akan disimpan.
     * @return true jika berhasil; false jika gagal.
     */
    public boolean createBill(Bill bill) {
        String sql = "INSERT INTO bills (appointment_id, total_medication_cost, total_service_cost, consultation_fee, total_amount, is_paid) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bill.getAppointmentId());
            stmt.setDouble(2, bill.getTotalMedicationCost());
            stmt.setDouble(3, bill.getTotalServiceCost());
            stmt.setDouble(4, bill.getConsultationFee());
            stmt.setDouble(5, bill.getTotalAmount());
            stmt.setBoolean(6, bill.isPaid());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}