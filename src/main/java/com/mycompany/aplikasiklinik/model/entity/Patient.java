package com.mycompany.aplikasiklinik.model.entity;

import java.time.LocalDate;

/**
 * Entitas yang merepresentasikan data pasien klinik.
 * Berisi informasi identitas, demografi, dan kontak pasien.
 */
public class Patient {

    private int id;
    private String name;          // Nama lengkap pasien
    private String nik;           // Nomor Induk Kependudukan (NIK)
    private LocalDate birthDate;  // Tanggal lahir
    private String gender;        // Jenis kelamin: "L" (Laki-laki) atau "P" (Perempuan)
    private String address;       // Alamat lengkap
    private String phoneNumber;   // Nomor telepon/handphone

    /**
     * Konstruktor default (tanpa parameter).
     */
    public Patient() {}

    /**
     * Konstruktor lengkap untuk membuat objek Patient dengan semua atribut.
     *
     * @param id ID unik pasien dari database.
     * @param name nama lengkap pasien.
     * @param nik Nomor Induk Kependudukan (NIK).
     * @param birthDate tanggal lahir pasien.
     * @param gender jenis kelamin ("L" atau "P").
     * @param address alamat lengkap pasien.
     * @param phoneNumber nomor telepon pasien.
     */
    public Patient(int id, String name, String nik, LocalDate birthDate, String gender, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.nik = nik;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // --- Getter dan Setter ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Mengembalikan representasi teks singkat untuk tampilan di antarmuka,
     * berupa nama pasien diikuti NIK-nya dalam tanda kurung.
     *
     * @return string dalam format: "Nama (NIK)".
     */
    @Override
    public String toString() {
        return name + " (" + nik + ")";
    }
}