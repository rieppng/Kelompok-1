package com.mycompany.aplikasiklinik;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import com.mycompany.aplikasiklinik.model.db.DatabaseConnection;
import com.mycompany.aplikasiklinik.view.LoginView;
import com.mycompany.aplikasiklinik.controller.LoginController;

/**
 * Titik masuk utama aplikasi klinik.
 * Bertanggung jawab untuk:
 * 1. Memastikan koneksi database berhasil sebelum aplikasi dimulai.
 * 2. Menjalankan antarmuka GUI melalui LoginView yang dikendalikan oleh LoginController.
 */
public class Main {

    /**
     * Metode utama yang dijalankan saat aplikasi pertama kali di-start.
     * 
     * @param args argumen baris perintah (tidak digunakan dalam aplikasi ini).
     */
    public static void main(String[] args) {
        // Langkah 1: Uji koneksi database sebelum memulai antarmuka pengguna
        try {
            DatabaseConnection.getConnection();
            System.out.println("Koneksi Database Awal Berhasil!");
        } catch (SQLException e) {
            // Jika koneksi gagal, tampilkan pesan error dan hentikan aplikasi
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                null,
                "Gagal terhubung ke Database:\n" + e.getMessage(),
                "Error Database",
                JOptionPane.ERROR_MESSAGE
            );
            return; // Hentikan eksekusi jika database tidak tersedia
        }

        // Langkah 2: Inisialisasi antarmuka pengguna (GUI) di thread EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            new LoginController(loginView); // Hubungkan view dengan controller
            loginView.setVisible(true);    // Tampilkan layar login
        });
    }
}