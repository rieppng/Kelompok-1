package com.mycompany.quiz_varel;

/**
 *
 * @author BlueBird
 */
public class Enemy extends Character {
    public Enemy (String nama, int hp, int attackPower, int defense){
        super(nama, hp, attackPower, defense);
    }
    
    public void info(){
        System.out.println("Nama Enemy         : " + this.nama);
        System.out.println("HP Enemy           : " + this.hp);
        System.out.println("Base Damage Enemy  : " + this.attackPower);
        System.out.println("Defense Enemy      : " + this.defense);
        System.err.println("--------------------------------------");
    }
    
    public void attack(Hero h){
        p.setHpCharacter(h.getHpCharacter() - this.attack);
        System.out.println(getNamaEnemy() + " menyerang " + h.getNamaHero() + " dengan damage " + this.baseDamage);
        System.out.println("Sisa hp " + h.getNamaHero() + " adalah " + h.getHpHero());
    }
    
    public void defense(Enemy e){
        e.setAttackEnemy(e.getHpEnemy() - this.attackPower);
        System.out.println(this.nama + " Menangkal damage " + e.getNamaEnemy() + " dengan damage " + this.attackPower);
        System.out.println("Sisa hp " + e.getNamaEnemy() + " adalah " + e.getHpEnemy());
    }
    
    public void useSkill (Enemy e){
        System.out.println(getNama);
    }
}
