import java.util.Scanner;
import java.util.Random;

public class Game {
    public void main(){
        Random rand = new Random();
        Enemy enemy = new Goblin("Goblin", 500, 150, 50);
        Hero hero = null;
        Scanner input = new Scanner(System.in);
        System.out.println("Role");
        System.out.println("1. Warrior");
        System.out.println("2. Mage");
        System.out.println("3. Archer");
        System.out.print("Pilih role : ");    
        String role = input.nextLine();
        int skill;

        switch(role){
            case "1":
                System.out.println("Kamu memilih role Warrior.");
                hero = new Warrior ("Barbarian", 1000, 200, 200);
                break;
            case "2":
                System.out.println("Kamu memilih role Mage.");
                hero = new Mage("Witch", 700, 400, 100, 300);
                break;
            case "3":
                System.out.println("Kamu memilih role Archer.");
                hero = new Warrior ("Archer", 500, 350, 50);
                break;
            default:
                System.out.println("Role tidak tersedia");
                break;
        }


        do {
            System.out.println("Aksi");
            System.out.println("1. Normal Attack");
            System.out.println("2. Unique Skill");
            System.out.print("Pilih aksi : ");
            skill = input.nextInt();

            switch (skill) {
                case 1:
                    hero.attack(enemy);
                    break;
                case 2:
                    hero.uniqueSkill(enemy);
                    break;
                default:
                    System.out.println("Aksi tidak tersedia");
                    break;
            }
        } while (enemy.getHp() > 0 && hero.getHp() > 0);
        if (enemy.getHp() <= 0 && hero.getHp() > 0) {
            System.out.println(enemy.getName() + " telah dikalahkan");
        } else {
            System.out.println("kamu telah dikalahkan oleh " + enemy.getName());
        }
    }
}
