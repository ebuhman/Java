package rpg;
import java.util.Scanner;
import rpg.characters.Player;

public class Game {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // 1. Setup the Player
        System.out.println("--- Welcome to the Java RPG ---");
        System.out.print("Enter your hero's name: ");
        String name = input.nextLine();
        
        // Initializing hero with Health, MaxHealth, Attack, and Defense
        Player hero = new Player(name, 100, 100, 15, 2);
        BattleManager battleManager = new BattleManager(hero, 7);

        boolean gameRunning = true;

        // 2. The Main Game Loop
        while (gameRunning && hero.isAlive()) {
            System.out.println("\n================================");
            System.out.println("You wander into the tall grass...");
            
            // Triggers the battle logic in BattleManager
            battleManager.runBattle(); 

            // 3. Post-Battle Menu
            if (hero.isAlive()) {
                System.out.println("\n1. Continue adventuring");
                System.out.println("2. Retire (Quit Game)");
                System.out.print("Choice: ");
                
                // Handling user input to either continue or stop
                if (input.hasNextInt()) {
                    int choice = input.nextInt();
                    input.nextLine(); // Clear scanner buffer
                    
                    if (choice == 2) {
                        gameRunning = false; 
                        System.out.println("You retired a valiant hero!");
                    }
                }
            } else {
                System.out.println("GAME OVER. Better luck next time!");
                gameRunning = false;
            }
        }
        input.close();
    }
}