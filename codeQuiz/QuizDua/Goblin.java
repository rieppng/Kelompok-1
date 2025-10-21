public class Goblin extends Enemy {
    public Goblin(String name, int hp, int baseDamage, int defense) {
        super(name, hp, baseDamage, defense);
    }

    @Override
    public void uniqueSkill(Hero hero) {
        int damage = getBaseDamage() + 50;
        hero.setHp(hero.getHp() - damage);
        if (hero.getHp() <= 0) {
            hero.setHp(0);
        }
        System.out.println(getName() + " menyerang " + hero.getName() + " dengan skill Lempar Tombak");
        System.out.println(hero.getName() + " mendapatkan damage sebesar " + damage);
        System.out.println(hero.getName() + " memiliki sisa hp " + hero.getHp());
        System.out.println("============================================================");
    }
}
