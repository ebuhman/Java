package rpg;

import static org.junit.Assert.*;
import org.junit.Test;

import rpg.EnemySpawner;
import rpg.EnemyType;
import rpg.HealingItem;
import rpg.characters.Player;
import rpg.characters.Enemy;
import rpg.characters.MiniBoss;
import rpg.EnemySpawner;
import rpg.BattleManager;

public class GameTest {
    
    @Test
    public void testPlayerCreation() {
        Player player1 = new Player("Hero", 100, 100, 15, 10);
        Player player2 = new Player("Warrior", 120, 120, 20, 5);
        
        assertEquals("Hero", player1.getName());
        assertEquals(100, player1.getHealth());
        assertEquals(15, player1.getAttackPower());
        
        assertEquals("Warrior", player2.getName());
        assertEquals(120, player2.getHealth());
        assertEquals(20, player2.getAttackPower());
    }
    
    @Test
    public void testTakeDamage() {
        Player player = new Player("TestGuy", 100, 100, 15, 10);
        
        // Player has 10 defense, so 30 damage - 10 defense = 20 actual damage
        player.takeDamage(30);
        assertEquals(80, player.getHealth());
        
        // Take more damage
        player.takeDamage(25);
        assertEquals(65, player.getHealth());
    }
    
    @Test
    public void testHealing() {
        Player player = new Player("TestGuy", 100, 100, 15, 10);
        
        // Damage the player first
        player.takeDamage(40);
        assertEquals(70, player.getHealth());
        
        // Heal with a potion
        HealingItem potion = new HealingItem("Health Potion", "Restores 20 HP", 20);
        potion.use(player);
        assertEquals(90, player.getHealth());
    }
    
    @Test
    public void testHealingDoesNotExceedMaxHealth() {
        Player player = new Player("TestGuy", 100, 100, 15, 10);
        
        // Only take 10 damage
        player.takeDamage(20);
        assertEquals(90, player.getHealth());
        
        // Try to heal 50 HP (should cap at 100)
        HealingItem bigPotion = new HealingItem("Mega Potion", "Restores 50 HP", 50);
        bigPotion.use(player);
        assertEquals(100, player.getHealth());
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
        
        assertEquals("Goblin", goblin.getName());
        assertEquals(30, goblin.getHealth());
        assertEquals(8, goblin.getAttackPower());
        assertEquals(EnemyType.GOBLIN, goblin.getEnemyType());
        
        assertEquals("Skeleton", skeleton.getName());
        assertEquals(25, skeleton.getHealth());
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
        assertEquals(17, goblin.getHealth());  // 30 - (15 - 2 defense) = 17
        
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
        
        assertEquals("Shadow Lord", boss.getName());
        assertEquals(100, boss.getHealth());
        assertEquals(20, boss.getAttackPower());
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
        assertTrue(boss.getAttackPower() > 20);   // Should be boosted
    }

    @Test
    public void testMiniBossPhaseTransitionOnlyOnce() {
        MiniBoss boss = new MiniBoss("Shadow Lord", 100, 100, 20, 5);
        
        // Damage to trigger phase 2
        boss.takeDamage(60);
        boss.checkPhaseTransition();
        
        int attackAfterFirstTransition = boss.getAttackPower();
        
        // Call transition again - stats shouldn't increase again
        boss.checkPhaseTransition();
        
        assertEquals(attackAfterFirstTransition, boss.getAttackPower());
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
        assertEquals(20, boss.getAttackPower());  // Stats unchanged
    }

    @Test
    public void testEnemySpawnerCreation() {
        EnemySpawner spawner = new EnemySpawner(5);
        
        // Should not spawn boss initially
        assertFalse(spawner.shouldSpawnBoss());
    }

    @Test
    public void testSpawnEnemy() {
        EnemySpawner spawner = new EnemySpawner(5);
        
        Enemy enemy = spawner.spawnEnemy();
        
        // Enemy should be created
        assertNotNull(enemy);
        assertTrue(enemy.isAlive());
        
        // Should be one of the three types
        EnemyType type = enemy.getEnemyType();
        assertTrue(type == EnemyType.GOBLIN || 
                type == EnemyType.SKELETON || 
                type == EnemyType.SLIME);
    }

    @Test
    public void testSpawnMultipleEnemies() {
        EnemySpawner spawner = new EnemySpawner(5);
        
        // Spawn 3 enemies
        Enemy enemy1 = spawner.spawnEnemy();
        Enemy enemy2 = spawner.spawnEnemy();
        Enemy enemy3 = spawner.spawnEnemy();
        
        // All should be valid enemies
        assertNotNull(enemy1);
        assertNotNull(enemy2);
        assertNotNull(enemy3);
    }

    @Test
    public void testBossTrigger() {
        EnemySpawner spawner = new EnemySpawner(3);  // Boss after 3 enemies
        
        // No boss initially
        assertFalse(spawner.shouldSpawnBoss());
        
        // Spawn 3 enemies
        spawner.spawnEnemy();
        assertFalse(spawner.shouldSpawnBoss());  // Not yet
        
        spawner.spawnEnemy();
        assertFalse(spawner.shouldSpawnBoss());  // Not yet
        
        spawner.spawnEnemy();
        assertTrue(spawner.shouldSpawnBoss());   // Now boss should spawn!
    }

    @Test
    public void testResetCount() {
        EnemySpawner spawner = new EnemySpawner(3);
        
        // Spawn 3 enemies to trigger boss
        spawner.spawnEnemy();
        spawner.spawnEnemy();
        spawner.spawnEnemy();
        
        assertTrue(spawner.shouldSpawnBoss());
        
        // Reset the counter (after boss is defeated)
        spawner.resetCount();
        
        // Boss should not spawn anymore
        assertFalse(spawner.shouldSpawnBoss());
    }

    @Test
    public void testEnemyTypeVariety() {
        EnemySpawner spawner = new EnemySpawner(100);
        
        boolean foundGoblin = false;
        boolean foundSkeleton = false;
        boolean foundSlime = false;
        
        // Spawn 20 enemies and check we get variety
        for (int i = 0; i < 20; i++) {
            Enemy enemy = spawner.spawnEnemy();
            EnemyType type = enemy.getEnemyType();
            
            if (type == EnemyType.GOBLIN) foundGoblin = true;
            if (type == EnemyType.SKELETON) foundSkeleton = true;
            if (type == EnemyType.SLIME) foundSlime = true;
        }
        
        // With 20 spawns, we should see at least 2 of the 3 types
        // (statistically very likely)
        int typesFound = (foundGoblin ? 1 : 0) + 
                        (foundSkeleton ? 1 : 0) + 
                        (foundSlime ? 1 : 0);
        assertTrue(typesFound >= 2);
    }
}