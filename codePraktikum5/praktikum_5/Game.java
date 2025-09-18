/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum_5;

/**
 *
 * @author User
 */
public class Game {
    String judul;
    String genre;
    int tahunRilis;

    // Default constructor
    public Game() {
        this.judul = "Belum ditentukan";
        this.genre = "Belum ditentukan";
        this.tahunRilis = 0;
    }

    // Constructor dengan parameter
    public Game(String judul, String genre, int tahunRilis) {
        this.judul = judul;
        this.genre = genre;
        this.tahunRilis = tahunRilis;
    }

    // Method tanpa parameter
    public void infoGame() {
        System.out.println("Judul Game: " + judul);
        System.out.println("Genre: " + genre);
        if (tahunRilis == 0) {
            System.out.println("Tahun Rilis: Belum ditentukan");
        } else {
            System.out.println("Tahun Rilis: " + tahunRilis);
        }
        System.out.println("----------------------------");
    }

    // Method dengan parameter
    public void infoGame(String judul, String genre, int tahunRilis) {
        System.out.println("Judul Game: " + judul);
        System.out.println("Genre: " + genre);
        System.out.println("Tahun Rilis: " + tahunRilis);
        System.out.println("----------------------------");
    }
}

