package com.mycompany.praktikum_7;

/**
 *
 * @author User
 */
public class Enemy {
    private String nama;
    private int hp, baseDamage;
    
    public Enemy (String nama, int hp, int baseDamage){
        this.nama = nama;
        this.hp = hp;
        this.baseDamage = baseDamage;
    }
    
    public String getNamaEnemy(){
        return this.nama;
    }
    
    public void setNamaEnemy(String nama){
        this.nama = nama;
    }
    
    public int getHpEnemy(){
        return hp;
    }
    
    public void setHpEnemy(int hp){
        this.hp = hp;
    }
    
    public int getBaseDamageEnemy(){
        return this.baseDamage;
    }
    
    public void setBaseDamageEnemy(int baseDamage){
        this.baseDamage = baseDamage;
    }
    
    public void infoEnemy(){
        System.out.println("Nama Musuh       : " + this.nama);
        System.out.println("HP Musuh         : " + this.hp);
        System.out.println("Base Damage Musuh: " + this.baseDamage);
        System.err.println("--------------------------------------");
    }
    
    public void attack(Player p){
        p.setHpPlayer(p.getHpPlayer() - this.baseDamage);
        System.out.println(getNamaEnemy() + " menyerang " + p.getNamaPlayer() 
                           + " dengan damage " + this.baseDamage);
        System.out.println("Sisa hp " + p.getNamaPlayer() 
                           + " adalah " + p.getHpPlayer());
    } 
}
