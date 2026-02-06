package rpg.characters;

import rpg.EnemyType;
import rpg.HealingItem;

public class Enemy extends GameCharacter {
    // Attributes
    private final EnemyType enemyType;
    private static final float DROP_CHANCE = 0.30f;
    
    public Enemy(String name, int health, int maxHealth, int attackPower, int defense, EnemyType type) {
        super(name, health, maxHealth, attackPower, defense);
        this.enemyType = type;   
    }

    @Override
    public int attack()
    {
        return getAttackPower();
    }

    public HealingItem rollDrop()
    {
        if (Math.random() <= DROP_CHANCE)
        {
            return new HealingItem("Health Potion", "Restores 20 HP", 20);
        }
        return null;
    }

    public EnemyType getEnemyType()
    {
        return enemyType;
    }
}