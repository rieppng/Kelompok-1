package com.mycompany.aplikasiklinik.model.entity;

import java.util.Objects;

/**
 * Entitas yang merepresentasikan pengguna sistem (dokter, admin, resepsionis, dll).
 * Berisi informasi akun, peran, status aktif, dan biaya konsultasi (khusus dokter).
 */
public class User {

    private int id;
    private String username;
    private String password;
    private String fullName;
    private UserRole role;
    private int medicalSpecialtyId;
    private MedicalSpecialty medicalSpecialty;
    private boolean isActive;
    private double consultationFee; // Biaya konsultasi dokter, digunakan saat membuat tagihan

    /**
     * Konstruktor default (tanpa parameter).
     */
    public User() {}

    /**
     * Konstruktor untuk membuat objek User tanpa biaya konsultasi.
     * Digunakan terutama untuk entitas non-dokter atau saat biaya tidak relevan.
     *
     * @param id ID unik pengguna.
     * @param username nama pengguna untuk login.
     * @param password kata sandi (disimpan dalam bentuk plain text atau hash).
     * @param fullName nama lengkap pengguna.
     * @param role peran pengguna dalam sistem.
     * @param medicalSpecialtyId ID poli/keahlian medis (0 jika tidak berlaku).
     * @param isActive status apakah pengguna sedang aktif (login).
     */
    public User(int id, String username, String password, String fullName, UserRole role, int medicalSpecialtyId, boolean isActive) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.medicalSpecialtyId = medicalSpecialtyId;
        this.isActive = isActive;
    }

    /**
     * Konstruktor alternatif tanpa ID (digunakan saat membuat pengguna baru sebelum disimpan ke DB).
     */
    public User(String username, String password, String fullName, UserRole role, int medicalSpecialtyId, boolean isActive) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.medicalSpecialtyId = medicalSpecialtyId;
        this.isActive = isActive;
    }

    /**
     * Konstruktor lengkap yang mencakup biaya konsultasi.
     * Digunakan terutama untuk dokter, karena biaya konsultasi diambil dari data pengguna.
     *
     * @param id ID unik pengguna.
     * @param username nama pengguna untuk login.
     * @param password kata sandi.
     * @param fullName nama lengkap pengguna.
     * @param role peran pengguna.
     * @param medicalSpecialtyId ID poli medis.
     * @param isActive status aktif pengguna.
     * @param consultationFee biaya konsultasi dokter (dalam Rupiah).
     */
    public User(int id, String username, String password, String fullName, UserRole role, int medicalSpecialtyId, boolean isActive, double consultationFee) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.medicalSpecialtyId = medicalSpecialtyId;
        this.isActive = isActive;
        this.consultationFee = consultationFee;
    }

    /**
     * Konstruktor alternatif lengkap tanpa ID.
     */
    public User(String username, String password, String fullName, UserRole role, int medicalSpecialtyId, boolean isActive, double consultationFee) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.medicalSpecialtyId = medicalSpecialtyId;
        this.isActive = isActive;
        this.consultationFee = consultationFee;
    }

    // --- Getter dan Setter ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public int getMedicalSpecialtyId() {
        return medicalSpecialtyId;
    }

    public void setMedicalSpecialtyId(int medicalSpecialtyId) {
        this.medicalSpecialtyId = medicalSpecialtyId;
    }

    public MedicalSpecialty getMedicalSpecialty() {
        return medicalSpecialty;
    }

    public void setMedicalSpecialty(MedicalSpecialty medicalSpecialty) {
        this.medicalSpecialty = medicalSpecialty;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role=" + role +
                ", isActive=" + isActive +
                ", consultationFee=" + consultationFee +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}