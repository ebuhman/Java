package rpg;

public class Game {
    public static void main(String[] args) {
      rpg.characters.Character testCharacter = new rpg.characters.Character("TestGuy", 100, 100, 15, 10) {
        public int attack() {
            return GetAttackPower();
            }
        };
        System.out.println(testCharacter.GetName());
        System.out.println(testCharacter.GetHealth());
        System.out.println(testCharacter.GetAttackPower());
    }   
}
