package rpg.characters;

public class MiniBoss extends GameCharacter {
    // Attributes
    private int phase;

    // Behaviors
    public MiniBoss(String name, int health, int maxHealth, int attackPower, int defense) {
        super(name, health, maxHealth, attackPower, defense);
        this.phase = 1;
    }

    @Override
    public int attack()
    {
        return GetAttackPower();
    }


   public void checkPhaseTransition() 
   {
    if (health <= maxHealth / 2 && phase == 1) 
        {
        phase = 2;
        maxHealth = maxHealth + 10;
        attackPower = attackPower + 2;
        defense = defense + 1;
        System.out.println(GetName() + " enters phase 2!");
        }
    }

    public int getPhase()
    {
        return phase;
    }

}
