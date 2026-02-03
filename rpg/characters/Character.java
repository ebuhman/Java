package rpg.characters;

public abstract class Character {
    // Attributes
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int attackPower;
    protected int defense;

    public Character(String name, int health, int maxHealth, int attackPower, int defense)
    {
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
        this.attackPower = attackPower;
        this.defense = defense;
    }

    // Methods
    public String GetName()
    {
        return name;
    }

    public int GetHealth()
    {
        return health;
    }

    public void takeDamage(int incomingDamage)
    {
        health -= Math.max(0, incomingDamage - attackPower);
    }

    public boolean isAlive()
    {
        return health > 0;
    }

    public int GetAttackPower()
    {
        return attackPower;
    }

    public abstract int attack();
}
