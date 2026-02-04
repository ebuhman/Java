package rpg;

import static org.junit.Assert.*;
import org.junit.Test;

import rpg.HealingItem;
import rpg.characters.Player;

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
}