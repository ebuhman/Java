package rpg;

import static org.junit.Assert.*;
import org.junit.Test;

import rpg.EnemyType;
import rpg.HealingItem;
import rpg.characters.Player;
import rpg.characters.Enemy;
import rpg.characters.MiniBoss;

public class GameTest {
    
    @Test
    public void testPlayerCreation() {
        Player player1 = new Player("Hero", 100, 100, 15, 10);
        Player player2 = new Player("Warrior", 120, 120, 20, 5);
        
        assertEquals("Hero", player1.GetName());
        assertEquals(100, player1.GetHealth());
        assertEquals(15, player1.GetAttackPower());
        
        assertEquals("Warrior", player2.GetName());
        assertEquals(120, player2.GetHealth());
        assertEquals(20, player2.GetAttackPower());
    }
    
    @Test
    public void testTakeDamage() {
        Player player = new Player("TestGuy", 100, 100, 15, 10);
        
        // Player has 10 defense, so 30 damage - 10 defense = 20 actual damage
        player.takeDamage(30);
        assertEquals(80, player.GetHealth());
        
        // Take more damage
        player.takeDamage(25);
        assertEquals(65, player.GetHealth());
    }
    
    @Test
    public void testHealing() {
        Player player = new Player("TestGuy", 100, 100, 15, 10);
        
        // Damage the player first
        player.takeDamage(40);
        assertEquals(70, player.GetHealth());
        
        // Heal with a potion
        HealingItem potion = new HealingItem("Health Potion", "Restores 20 HP", 20);
        potion.use(player);
        assertEquals(90, player.GetHealth());
    }
    
    @Test
    public void testHealingDoesNotExceedMaxHealth() {
        Player player = new Player("TestGuy", 100, 100, 15, 10);
        
        // Only take 10 damage
        player.takeDamage(20);
        assertEquals(90, player.GetHealth());
        
        // Try to heal 50 HP (should cap at 100)
        HealingItem bigPotion = new HealingItem("Mega Potion", "Restores 50 HP", 50);
        bigPotion.use(player);
        assertEquals(100, player.GetHealth());
    }
    
    @Test
    public void testIsAlive() {
        Player player = new Player("TestGuy", 50, 50, 15, 5);
        
        assertTrue(player.isAlive());
        
        // Take damage but still alive
        player.takeDamage(30);
        assertTrue(player.isAlive());
        
        // Take lethal damage
        player.takeDamage(100);
        assertFalse(player.isAlive());
    }
    @Test
    public void testEnemyCreation() {
        Enemy goblin = new Enemy("Goblin", 30, 30, 8, 2, EnemyType.GOBLIN);
        Enemy skeleton = new Enemy("Skeleton", 25, 25, 10, 1, EnemyType.SKELETON);
        
        assertEquals("Goblin", goblin.GetName());
        assertEquals(30, goblin.GetHealth());
        assertEquals(8, goblin.GetAttackPower());
        assertEquals(EnemyType.GOBLIN, goblin.getEnemyType());
        
        assertEquals("Skeleton", skeleton.GetName());
        assertEquals(25, skeleton.GetHealth());
        assertEquals(EnemyType.SKELETON, skeleton.getEnemyType());
    }

    @Test
    public void testEnemyAttack() {
        Enemy goblin = new Enemy("Goblin", 30, 30, 8, 2, EnemyType.GOBLIN);
        
        int damage = goblin.attack();
        assertEquals(8, damage);  // Should return attackPower
    }   

    @Test
    public void testEnemyTakesDamage() {
        Enemy goblin = new Enemy("Goblin", 30, 30, 8, 2, EnemyType.GOBLIN);
        
        goblin.takeDamage(15);
        assertEquals(17, goblin.GetHealth());  // 30 - (15 - 2 defense) = 17
        
        assertTrue(goblin.isAlive());
        
        goblin.takeDamage(50);
        assertFalse(goblin.isAlive());
    }

    @Test
    public void testEnemyDrops() {
        Enemy goblin = new Enemy("Goblin", 30, 30, 8, 2, EnemyType.GOBLIN);
        
        // This test is probabilistic - rollDrop might return null or an item
        // We'll just verify it doesn't crash and returns the right type
        HealingItem drop = goblin.rollDrop();
        
        // drop can be null or a HealingItem, both are valid
        if (drop != null) {
            assertEquals("Health Potion", drop.getName());
            assertEquals(20, drop.getHealAmount());
        }
        // If null, the 30% chance failed - that's okay
    }
    @Test
    public void testMiniBossCreation() {
        MiniBoss boss = new MiniBoss("Shadow Lord", 100, 100, 20, 5);
        
        assertEquals("Shadow Lord", boss.GetName());
        assertEquals(100, boss.GetHealth());
        assertEquals(20, boss.GetAttackPower());
        assertEquals(1, boss.getPhase());  // Starts in phase 1
    }

    @Test
    public void testMiniBossAttack() {
        MiniBoss boss = new MiniBoss("Shadow Lord", 100, 100, 20, 5);
        
        int damage = boss.attack();
        assertEquals(20, damage);
    }

    @Test
    public void testMiniBossPhaseTransition() {
        MiniBoss boss = new MiniBoss("Shadow Lord", 100, 100, 20, 5);
        
        // Boss is at full health, phase should be 1
        assertEquals(1, boss.getPhase());
        
        // Damage boss to below 50% HP (51+ damage)
        boss.takeDamage(60);
        
        // Check for phase transition
        boss.checkPhaseTransition();
        
        // Should now be in phase 2
        assertEquals(2, boss.getPhase());
        
        // Stats should be boosted (adjust based on your actual boosts)
        assertEquals(110, boss.getMaxHealth());  // If you added +20
        assertTrue(boss.GetAttackPower() > 20);   // Should be boosted
    }

    @Test
    public void testMiniBossPhaseTransitionOnlyOnce() {
        MiniBoss boss = new MiniBoss("Shadow Lord", 100, 100, 20, 5);
        
        // Damage to trigger phase 2
        boss.takeDamage(60);
        boss.checkPhaseTransition();
        
        int attackAfterFirstTransition = boss.GetAttackPower();
        
        // Call transition again - stats shouldn't increase again
        boss.checkPhaseTransition();
        
        assertEquals(attackAfterFirstTransition, boss.GetAttackPower());
        assertEquals(2, boss.getPhase());  // Still phase 2, not phase 3
    }

    @Test
    public void testMiniBossDoesNotTransitionEarly() {
        MiniBoss boss = new MiniBoss("Shadow Lord", 100, 100, 20, 5);
        
        // Take only 30 damage (still above 50% HP)
        boss.takeDamage(30);
        boss.checkPhaseTransition();
        
        // Should still be in phase 1
        assertEquals(1, boss.getPhase());
        assertEquals(20, boss.GetAttackPower());  // Stats unchanged
    }
}