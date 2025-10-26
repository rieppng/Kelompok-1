package com.mycompany.quiz_varel;

/**
 *
 * @author BlueBird
 */
abstract public class Character {
    private String nama;
    private int hp, attackPower, defense;
   
    public Character (String nama, int hp, int attackPower, int defense){
        this.nama = nama;
        this.hp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
    }
    
    public String getNamaCharacter(){
        return this.nama;
    }
    
    public void setNamaCharacter(String nama){
        this.nama = nama;
    }
    
    public int getHpCharacter(){
        return this.hp;
    }
    
    public void setHpCharacter(int hp){
        this.hp = hp;
    }
    
    public int getAttackPowerCharacter(){
        return this.attackPower;
    }
    
    public void setAttackPowerCharacter(int attackPower){
        this.attackPower = attackPower;
    }
    
    public int getDefenseCharacter(){
        return this.defense;
    }
    
    public void setDefenseCharacter(int defense){
        this.defense = defense;
    }
    
    public void infoCharacter(){
    System.out.println("Nama Character        : " + this.nama);
    System.out.println("HP Character          : " + this.hp);
    System.out.println("Base Damage Character : " + this.attackPower);
    System.out.println("Defense Character     : " + this.defense);
    System.err.println("--------------------------------------");
    }
}

