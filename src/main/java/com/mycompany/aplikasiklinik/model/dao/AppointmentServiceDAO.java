package com.mycompany.aplikasiklinik.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.aplikasiklinik.model.db.DatabaseConnection;
import com.mycompany.aplikasiklinik.model.entity.AppointmentService;

/**
 * DAO (Data Access Object) untuk entitas AppointmentService.
 * Mengelola operasi database terkait layanan tambahan yang diberikan
 * dalam suatu appointment (antrian), seperti vaksinasi atau tes laboratorium.
 */
public class AppointmentServiceDAO {

    /**
     * Menyimpan satu layanan tambahan ke dalam database.
     * 
     * @param service objek AppointmentService yang akan disimpan.
     * @return true jika berhasil; false jika gagal.
     */
    public boolean addAppointmentService(AppointmentService service) {
        String sql = "INSERT INTO appointment_services (appointment_id, service_fee_id, quantity, price_at_time) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, service.getAppointmentId());
            stmt.setInt(2, service.getServiceFeeId());
            stmt.setInt(3, service.getQuantity());
            stmt.setDouble(4, service.getPriceAtTime());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengambil semua layanan tambahan yang terkait dengan suatu appointment.
     * Digunakan saat menghitung total biaya layanan untuk tagihan.
     * 
     * @param appointmentId ID antrian.
     * @return daftar layanan tambahan untuk appointment tersebut.
     */
    public List<AppointmentService> getServicesByAppointmentId(int appointmentId) {
        List<AppointmentService> list = new ArrayList<>();
        String sql = "SELECT * FROM appointment_services WHERE appointment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, appointmentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                AppointmentService s = new AppointmentService();
                s.setId(rs.getInt("id"));
                s.setAppointmentId(rs.getInt("appointment_id"));
                s.setServiceFeeId(rs.getInt("service_fee_id"));
                s.setQuantity(rs.getInt("quantity"));
                s.setPriceAtTime(rs.getDouble("price_at_time"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}