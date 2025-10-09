package com.mycompany.praktikum_7;

/**
 *
 * @author User
 */
public class Game {
    public static void main (String[] args){
        Player player1 = new Player ("Kratos", 1000, 200);
        Goblin goblin1 = new Goblin("Goby", 950, 150);
        player1.infoPlayer();
        goblin1.infoEnemy();
        player1.attack(goblin1);
        goblin1.attack(player1);        
    }
}
