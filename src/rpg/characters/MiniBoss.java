package rpg.characters;

import rpg.EnemyType;

public class MiniBoss extends Enemy {
    // Attributes
    private int phase;

    // Behaviors
    public MiniBoss(String name, int health, int maxHealth, int attackPower, int defense, EnemyType type) {
        super(name, health, maxHealth, attackPower, defense, EnemyType.BOSS);
        this.phase = 1;
    }

    @Override
    public int attack()
    {
        return getAttackPower();
    }


   public void checkPhaseTransition() 
   {
    if (health <= maxHealth / 2 && phase == 1) 
        {
        phase = 2;
        maxHealth = maxHealth + 10;
        attackPower = attackPower + 2;
        defense = defense + 1;
        System.out.println(getName() + " enters phase 2!");
        }
    }

    public int getPhase()
    {
        return phase;
    }

}
