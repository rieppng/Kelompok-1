package com.mycompany.quiz_varel;

/**
 *
 * @author BlueBird
 */
public class Hero extends Character {
    public Hero (String nama, int hp, int attackPower, int defense){
        super(nama, hp, attackPower, defense);
    }
    
    public void info(){
        System.out.println("Nama Hero         : " + this.nama);
        System.out.println("HP Hero           : " + this.hp);
        System.out.println("Base Damage Hero  : " + this.attackPower);
        System.out.println("Defense Hero      : " + this.defense);
        System.err.println("--------------------------------------");
    }
    
    public void attack(Enemy e){
        e.setHpEnemy(e.getHpEnemy() - this.attackPower);
        System.out.println(this.nama + " menyerang " + e.getNamaEnemy() + " dengan damage " + this.attackPower);
        System.out.println("Sisa hp " + e.getNamaEnemy() + " adalah " + e.getHpEnemy());
    }
    
    public void defense(Hero h){
        e.setHpEnemy(e.getHpEnemy() - this.defense);
        System.out.println(this.nama + " menyerang " + e.getNamaEnemy() + " dengan damage " + this.attackPower);
        System.out.println("Sisa hp " + e.getNamaEnemy() + " adalah " + e.getHpEnemy());
    }
    
    public void useSkill (Enemy e){
        System.out.println(getNama);
    }
    
}
