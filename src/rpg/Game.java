package rpg;
import rpg.characters.*;

public class Game {
    public static void main(String[] args) {
        Player testCharacter = new Player("TestGuy", 100, 100, 15, 10);
        
        System.out.println(testCharacter.GetName());
        System.out.println("Health: " + testCharacter.GetHealth());
        
        // Damage the player
        testCharacter.takeDamage(30);
        System.out.println("Health after damage: " + testCharacter.GetHealth());
        
        // Create and use a healing item
        HealingItem potion = new HealingItem("Health Potion", "Restores 20 HP", 20);
        potion.use(testCharacter);
        System.out.println("Health after potion: " + testCharacter.GetHealth());
    }   
}

