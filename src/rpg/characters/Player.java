package rpg.characters;
import rpg.HealingItem;

public class Player extends GameCharacter {
    // Attributes
    protected HealingItem[] inventory = new HealingItem[3];
    protected int enemiesDefeated;

    public Player(String name, int health, int maxHealth, int attackPower, int defense)
    {
        super(name, health, maxHealth, attackPower, defense);
    }

    @Override
    public int attack()
    {
        return getAttackPower();
    }

    public void useItem(int slot)
    {
        if (slot >= 0 && slot < 3 && inventory[slot] != null)
        {
            inventory[slot].use(this);
            System.out.println("Used" + inventory[slot].getName() + "!");
            inventory[slot] = null;
        }
        else 
        {
            System.out.println("You have nothing in your inventory!");
        }
        
    }

    public HealingItem[] getInventory()
    {
        return inventory;
    }

    public boolean addItem(HealingItem item)
    {
        for (int i = 0; i < inventory.length; i++)
        {
            if (inventory[i] == null) // Empty value in array to add to
            {
                inventory[i] = item;
                return true;
            }
        }
        System.out.println("Your inventory is full!");
        return false;
    }

    public boolean hasItems()
    {
        for (HealingItem item : inventory)
        {
            if (item != null)
            {
                return true;
            }
        }
        return false; // Inventory Empty
    }
}
