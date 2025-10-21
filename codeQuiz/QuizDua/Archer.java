import java.util.Random;

public class Archer extends Hero {
    Random rand = new Random();
    private int plusDamage;
    int totalDamage;
    private boolean hasUseSkill = false;

    public Archer(String name, int hp, int baseDamage, int defense) {
        super(name, hp, baseDamage, defense);
    }

    public int getPlusDamage() {
        return plusDamage;
    }

    public void setPlusDamage(int plusDamage) {
        this.plusDamage = plusDamage;
    }

    @Override
    public void uniqueSkill(Enemy enemy) {
        if(hasUseSkill) return;
        plusDamage = rand.nextInt(100);
        if (plusDamage < 25 && plusDamage>10) {
            int chance = plusDamage + 100 / 100;
            totalDamage = getBaseDamage() * chance;
            enemy.setHp(enemy.getHp() - totalDamage);
            if (enemy.getHp() <= 0) {
                enemy.setHp(0);
            }
            System.out.println(getName() + " menyerang " + enemy.getName() + " dengan skill Double Shot");
            System.out.println(enemy.getName() + " mendapatkan damage sebesar " + totalDamage);
            System.out.println(enemy.getName() + " memiliki sisa hp " + enemy.getHp());
            System.out.println("============================================================");
            hasUseSkill = true;
            return;
        } else {
            totalDamage = getBaseDamage() * 2;
            enemy.setHp(enemy.getHp() - totalDamage);
            if (enemy.getHp() <= 0) {
                enemy.setHp(0);
            }
            System.out.println(getName() + " menyerang " + enemy.getName() + " dengan skill Double Shot");
            System.out.println(enemy.getName() + " mendapatkan damage sebesar " + totalDamage);
            System.out.println(enemy.getName() + " memiliki sisa hp " + enemy.getHp());
            System.out.println("============================================================");
            hasUseSkill = true;
            return;
        }
    }
}
