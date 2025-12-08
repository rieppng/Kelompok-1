package com.mycompany.aplikasiklinik.util;

import com.mycompany.aplikasiklinik.model.entity.User;

/**
 * Mengelola sesi pengguna saat ini dalam aplikasi.
 * Mengimplementasikan pola Singleton untuk memastikan hanya ada satu instance
 * yang mengatur data login pengguna di seluruh aplikasi.
 */
public class SessionManager {

    private static SessionManager instance;
    private User currentUser;

    /**
     * Konstruktor privat untuk mencegah instansiasi langsung dari luar kelas.
     */
    private SessionManager() {}

    /**
     * Mengembalikan satu-satunya instance dari SessionManager (Singleton).
     * Jika instance belum ada, maka akan dibuat terlebih dahulu.
     *
     * @return instance tunggal dari SessionManager.
     */
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    /**
     * Menyimpan objek pengguna yang sedang login ke dalam sesi.
     *
     * @param user objek User yang telah berhasil login.
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Mengembalikan objek pengguna yang sedang aktif dalam sesi.
     *
     * @return objek User yang sedang login, atau null jika belum ada yang login.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Memeriksa apakah ada pengguna yang sedang dalam keadaan login.
     *
     * @return true jika pengguna sedang login, false jika tidak.
     */
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Mengakhiri sesi pengguna saat ini dengan mengatur currentUser menjadi null.
     */
    public void logout() {
        this.currentUser = null;
    }
}