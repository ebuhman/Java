package rpg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import rpg.characters.Enemy;

public class EnemySpawner {
    
    // Attributes
    private List<EnemyType> enemyTypes;
    private int spawnCount;
    private int bossTrigger;
    private Random random;

    // Methods
    public EnemySpawner(int bossTrigger)
    {
        this.enemyTypes = new ArrayList<>();
        // Add all the enemy types to the random pool
        enemyTypes.add(EnemyType.GOBLIN);
        enemyTypes.add(EnemyType.SKELETON);
        enemyTypes.add(EnemyType.SLIME);

        this.spawnCount = 0;
        this.bossTrigger = bossTrigger;
        this.random = new Random();
    }

    public Enemy spawnEnemy()
    {
        EnemyType type = enemyTypes.get(random.nextInt(enemyTypes.size()));
        spawnCount++;

        switch (type)
        {
            case GOBLIN:
                return new Enemy("Goblin", 30, 30, 8, 3, EnemyType.GOBLIN);
            
            case SKELETON:
                return new Enemy("Skeleton", 20, 20, 6, 5, EnemyType.SKELETON);
            
            case SLIME:
                return new Enemy("Slime", 40, 40, 4, 4, EnemyType.SLIME);
            
            default:
                return new Enemy("Enemy", 20, 20, 5, 5, type);
        }
    }
    
    public boolean shouldSpawnBoss()
    {
        return spawnCount >= bossTrigger;
    }

    public void resetCount()
    {
        spawnCount = 0;
    }

}
