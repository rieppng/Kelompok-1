/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum_5;

/**
 *
 * @author User
 */
public class Tugas_Praktikum_5 {
    public static void main (String[] args){
        Game game1 = new Game();                        // default constructor
        Game game2 = new Game("Valorant", "FPS", 2020); // constructor dengan parameter
        Game game3 = new Game();                        // default constructor

        game1.infoGame("Minecraft", "Sandbox", 2011);  // method dengan parameter
        game2.infoGame();                              // method tanpa parameter
        game3.infoGame();                              // method tanpa parameter (default)
    }
}
