package rpg.characters;
import rpg.HealingItem;

public class Player extends GameCharacter {
    // Attributes
    protected HealingItem[] inventory = new HealingItem[3];
    protected int gold;
    protected int enemiesDefeated;

    public Player(String name, int health, int maxHealth, int defense, int attackPower)
    {
        super(name, health, maxHealth, attackPower, defense);
    }

    public int attack()
    {
        return GetAttackPower();
    }
}
