package rpg;

import java.util.Scanner;
import rpg.characters.Enemy;
import rpg.characters.MiniBoss;
import rpg.characters.Player;

public class BattleManager {
    // Attributes
    private Player player;
    private Enemy currentEnemy;
    private EnemySpawner spawner;
    private int turnCount;
    private Scanner scanner;

    public BattleManager (Player player, int bossTrigger) {
        this.player = player;
        this.spawner = new EnemySpawner(bossTrigger);
        this.scanner = new Scanner(System.in);

    }

    // Behaviors
    public void startBattle()
    {
        if (spawner.shouldSpawnBoss())
        {
            System.out.println("\n=== A Boss Appears! ===");
            currentEnemy = new MiniBoss("Shadow Lord", 100, 100, 10, 5, EnemyType.BOSS);
            spawner.resetCount();
        }
        else 
        {
            // Use the spawner object to get a random enemy and assign it
            currentEnemy = spawner.spawnEnemy(); 
            System.out.println("\nA wild " + currentEnemy.getName() + " appeared!");
        }
    }

    public void runBattle() {
        startBattle();
        
        boolean playerQuit = false; // Track if the player chooses to quit

        while (player.isAlive() && currentEnemy.isAlive() && !playerQuit) {
            
            playerQuit = playerTurn();

            if (!playerQuit && currentEnemy.isAlive()) {
                enemyTurn();
            }
        }

        endBattle(playerQuit);
    }

    

    // Update playerTurn to return boolean for the "Quit" logic
        public boolean playerTurn() {
            System.out.println("\n--- Your Turn ---");
            System.out.println("Your HP: " + player.getHealth() + "/" + player.getMaxHealth());
            System.out.println(currentEnemy.getName() + " HP: " + currentEnemy.getHealth() + "/" + currentEnemy.getMaxHealth());

            System.out.println("\n1. Attack");
            System.out.println("2. Use Item");
            System.out.print("Choose action: ");

            int choice = scanner.nextInt();

            if (choice == 1) {
                int damage = player.attack();
                currentEnemy.takeDamage(damage);
                // Note: You need a getDefense() method in GameCharacter for this line
                int actualDamage = Math.max(0, damage - currentEnemy.getDefense());
                System.out.println("You did " + actualDamage + " damage!");

                if (currentEnemy instanceof MiniBoss) {
                    ((MiniBoss) currentEnemy).checkPhaseTransition();
                }
            } 

            else if (choice == 2) {
                System.out.println("\n--- Inventory ---");
                HealingItem[] inventory = player.getInventory();
                for (int i = 0; i < inventory.length; i++) {
                    String itemName = (inventory[i] != null) ? inventory[i].getName() : "Empty";
                    System.out.println((i + 1) + ". " + itemName);
                }

                System.out.print("Choose a slot (1-3) or 0 to cancel: ");
                int slotChoice = scanner.nextInt();

                if (slotChoice >= 1 && slotChoice <= 3) {
                    player.useItem(slotChoice - 1); // Adjust for 0-based array index
                } else {
                    System.out.println("Returning to menu...");
                }
            }

        return false; // Battle continues
    }

    public void enemyTurn()
    {
        int damage = currentEnemy.attack();
        player.takeDamage(damage);

        int actualDamage = Math.max(0, damage - player.getDefense());
        System.out.println(currentEnemy.getName() + " attacks!");
        System.out.println("You actually took " + actualDamage + " damage after defense!");
    }

    public boolean checkVictory()
    {
        return currentEnemy.getHealth() <= 0;
    }

    public void awardDrops()
    {
    // 1. Roll to see if an item dropped
    HealingItem droppedItem = currentEnemy.rollDrop();

    // 2. Check if something actually dropped (it wasn't null)
    if (droppedItem != null)
    {
        System.out.println(currentEnemy.getName() + " dropped a " + droppedItem.getName() + "!");
        
        // 3. Try to add it to the player's inventory
        boolean wasAdded = player.addItem(droppedItem);
        
        if (wasAdded) {
            System.out.println("Item added to your inventory.");
        } 
        else {
            System.out.println("You couldn't carry it because your inventory is full!");
        }
        }
        else {
        }
    }

    public void endBattle(boolean playerQuit) {
        if (playerQuit) {
            System.out.println("\n--- RETREAT ---");
            System.out.println(player.getName() + " fled from the battle!");
        } 
        else if (!player.isAlive()) {
            System.out.println("\n--- DEFEAT ---");
            System.out.println(player.getName() + " has fallen in battle.");
        } 
        else if (!currentEnemy.isAlive()) {
            System.out.println("\n--- VICTORY ---");
            System.out.println("You defeated the " + currentEnemy.getName() + "!");
            awardDrops();
        }
    }
}