/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.praktikum_7;
import java.util.Random;
/**
 *
 * @author User
 */
public class Goblin extends Enemy {
    Random random = new Random();
    
    public Goblin (String nama, int hp, int baseDamage){
        super (nama, hp, baseDamage);
    }
    
    @Override
    public void attack(Player p){
        boolean chanceDamage = random.nextInt(100) < 100;
        int totalDamage = getBaseDamageEnemy();
        if (chanceDamage){
            totalDamage *= 2;
            System.out.println(getNamaEnemy() + " melakukan serangan critikal!");
        }
        p.setHpPlayer(p.getHpPlayer() - totalDamage);
        System.out.println(getNamaEnemy() + " menyerang " + p.getNamaPlayer() 
                           + " dengan damage " + totalDamage);
        System.out.println("Sisa hp " + p.getNamaPlayer() 
                           + " adalah " + p.getHpPlayer());
    } 
}
