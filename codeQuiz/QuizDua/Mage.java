import java.util.Random;

public class Mage extends Hero {
    Random rand = new Random();
    private int magicDamage;
    private boolean hasUseSkill = false;

    public Mage(String name, int hp, int baseDamage, int defense, int magicDamage) {
        super(name, hp, baseDamage, defense);
        this.magicDamage = magicDamage;
    }
    
    public int getMagicDamage() {
        return magicDamage;
    }

    @Override
    public void uniqueSkill(Enemy enemy) {
        if(hasUseSkill) return;
        if (rand.nextInt(100) < 70) {
            enemy.setHp(enemy.getHp() - getMagicDamage());
            if (enemy.getHp() <= 0) {
                enemy.setHp(0);
            }
            System.out.println(getName() + " menyerang " + enemy.getName() + " dengan skill Fireball");
            System.out.println(enemy.getName() + " mendapatkan damage sebesar " + getMagicDamage());
            System.out.println(enemy.getName() + " memiliki sisa hp " + enemy.getHp());
            System.out.println("============================================================");
        } else {
            System.out.println(getName() + " gagal menyerang " + enemy.getName() + " dengan skill Fireball");
        }
        hasUseSkill = true;
    }
}
