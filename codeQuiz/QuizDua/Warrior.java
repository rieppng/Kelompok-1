public class Warrior extends Hero {
    private boolean hasUseSkill = false;

    public Warrior(String name, int hp, int baseDamage, int defense) {
        super(name, hp, baseDamage, defense);
    }

    @Override
    public void uniqueSkill(Enemy enemy) {
        if(hasUseSkill) return;
        int damage = getBaseDamage() * 2;
        setHp(getHp()-5);
        enemy.setHp(enemy.getHp() - damage);
        if (enemy.getHp() <= 0) {
            enemy.setHp(0);
        }
        hasUseSkill = true;
        System.out.println(getName() + " menyerang " + enemy.getName() + " dengan skill Power Strike");
        System.out.println(enemy.getName() + " mendapatkan damage sebesar " + damage);
        System.out.println(enemy.getName() + " memiliki sisa hp " + enemy.getHp());
        System.out.println(getName() + " kehilangan 5 hp karena menggunakan skill power strike");
        System.out.println("============================================================");
    }
}