package rpg;

import rpg.characters.GameCharacter;

public class HealingItem {
    // Attributes
    protected String name;
    protected String description;
    protected int healAmount;

    public HealingItem(String name, String description, int healAmount)
    {
        this.name = name;
        this.description = description;
        this.healAmount = healAmount;
    }

    // Methods

    public void use(GameCharacter Player)
    {
        Player.heal(healAmount);
    }

    public int getHealAmount()
    {
        return healAmount;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

}
