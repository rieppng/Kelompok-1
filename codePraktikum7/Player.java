/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum_7;

/**
 *
 * @author User
 */
public class Player {
    
    private String nama;
    private int hp, baseDamage;
    
    
    public Player (String nama, int hp, int baseDamage){
        this.nama = nama;
        this.hp = hp;
        this.baseDamage = baseDamage;
    }
    
    public String getNamaPlayer(){
        return this.nama;
    }
    
    public void setNamaPlayer(String nama){
        this.nama = nama;
    }
    
    public int getHpPlayer(){
        return this.hp;
    }
    
    public void setHpPlayer(int hp){
        this.hp = hp;
    }
    
    public int getBaseDamagePlayer(){
        return this.baseDamage;
    }
    
    public void setBaseDamagePlayer(int baseDamage){
        this.baseDamage = baseDamage;
    }
    
    public void infoPlayer(){
        System.out.println("Nama Player       : " + this.nama);
        System.out.println("HP Player         : " + this.hp);
        System.out.println("Base Damage Player: " + this.baseDamage);
        System.err.println("--------------------------------------");
    }
    
    public void attack(Enemy e){
        e.setHpEnemy(e.getHpEnemy() - this.baseDamage);
        System.out.println(this.nama + " menyerang " + e.getNamaEnemy() 
                           + " dengan damage " + this.baseDamage);
        System.out.println("Sisa hp " + e.getNamaEnemy() 
                           + " adalah " + e.getHpEnemy());
    }
}