package rpg.characters;

public abstract class GameCharacter {
    // Attributes
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int attackPower;
    protected int defense;

    public GameCharacter(String name, int health, int maxHealth, int attackPower, int defense)
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
        health -= Math.max(0, incomingDamage - defense);
    }

    public boolean isAlive()
    {
        return health > 0;
    }

    public int GetAttackPower()
    {
        return attackPower;
    }

    public void SetHealth(int newHealth)
    {
        health = newHealth;
    }
    
    public void heal(int amount)
    {
        health = Math.min(health + amount, maxHealth);
    }

    public abstract int attack();
}
