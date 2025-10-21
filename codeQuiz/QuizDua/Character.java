public class Character {
    private String name;
    private int hp, baseDamage, defense;
    
    public Character(String name, int hp, int baseDamage, int defense) {
        this.name = name;
        this.hp = hp;
        this.baseDamage = baseDamage;
        this.defense = defense;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void getAttack(Enemy enemy) {
        setHp(getHp() - enemy.getBaseDamage());
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void attack(Enemy enemy) {
        enemy.setHp(enemy.getHp() - getBaseDamage());
        System.out.println(getName() + " menyerang " + enemy.getName() + "dengan attack normal");
        System.out.println(enemy.getName() + " mendapatkan damage sebesar " + getBaseDamage());
        System.out.println(enemy.getName() + " memiliki sisa hp " + enemy.getHp());
        System.out.println("============================================================");
    }

    public void uniqueSkill(Enemy enemy) {}
}
