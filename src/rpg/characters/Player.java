package rpg.characters;
import rpg.HealingItem;

public class Player extends GameCharacter {
    // Attributes
    protected HealingItem[] inventory = new HealingItem[3];
    protected int gold;
    protected int enemiesDefeated;

    public Player(String name, int health, int maxHealth, int attackPower, int defense)
    {
        super(name, health, maxHealth, attackPower, defense);
    }

    @Override
    public int attack()
    {
        return GetAttackPower();
    }
}
