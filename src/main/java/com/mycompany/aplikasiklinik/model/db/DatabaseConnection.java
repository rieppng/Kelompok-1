package com.mycompany.aplikasiklinik.model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Kelas utilitas untuk mengelola koneksi ke database MySQL.
 * Setiap pemanggilan {@link #getConnection()} akan membuat koneksi baru,
 * sehingga aman digunakan di lingkungan multi-thread seperti aplikasi GUI.
 * 
 * Konfigurasi database saat ini:
 * - URL: jdbc:mysql://localhost:3306/klinik_db
 * - Username: root
 * - Password: (kosong)
 */
public class DatabaseConnection {

    // Konfigurasi koneksi database (sesuaikan jika perlu)
    private static final String URL = "jdbc:mysql://localhost:3306/klinik_db";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Kosong untuk instalasi lokal default

    /**
     * Membuat dan mengembalikan koneksi baru ke database.
     * 
     * @return objek {@link Connection} yang siap digunakan.
     * @throws SQLException jika gagal terhubung ke database atau driver tidak ditemukan.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Memastikan driver MySQL termuat
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Membuat koneksi baru setiap kali dipanggil (tidak menyimpan state)
            return DriverManager.getConnection(URL, USER, PASSWORD);
            
        } catch (ClassNotFoundException e) {
            // Driver MySQL JDBC tidak ditemukan di classpath
            throw new SQLException("Driver MySQL tidak ditemukan! Pastikan mysql-connector-java tersedia.", e);
        }
    }
}