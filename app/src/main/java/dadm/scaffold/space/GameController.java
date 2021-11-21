package dadm.scaffold.space;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;

public class GameController extends GameObject {

    private static final int TIME_BETWEEN_ENEMIES = 500;
    private static final int TIME_BETWEEN_RESTORE_ITEMS = 20000;

    private long currentMillis;
    private List<Asteroid> asteroidPool = new ArrayList<Asteroid>();
    private List<Item_RestoreLife> pillPool = new ArrayList<Item_RestoreLife>();

    private int enemiesSpawned;
    private int itemsSpawned;

    public GameController(GameEngine gameEngine) {
        // We initialize the pool of items now
        for (int i=0; i<10; i++) {
            asteroidPool.add(new Asteroid(this, gameEngine));
        }
        for (int i = 0; i < 3; i++) {
            pillPool.add(new Item_RestoreLife(this, gameEngine));

        }
    }

    @Override
    public void startGame() {
        currentMillis = 0;
        enemiesSpawned = 0;
        itemsSpawned=0;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        currentMillis += elapsedMillis;

        long waveTimestamp = enemiesSpawned*TIME_BETWEEN_ENEMIES;
        long waveItems=itemsSpawned*TIME_BETWEEN_RESTORE_ITEMS;
        if (currentMillis > waveTimestamp) {
            // Spawn a new enemy
            Asteroid a = asteroidPool.remove(0);
            a.init(gameEngine);
            gameEngine.addGameObject(a);
            enemiesSpawned++;
            return;
        }
        if (currentMillis>waveItems){
            Item_RestoreLife p = pillPool.remove(0);
            p.init(gameEngine);
            gameEngine.addGameObject(p);
            itemsSpawned++;
            return;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        // This game object does not draw anything
    }

    public void returnToPool(Asteroid asteroid) {
        asteroidPool.add(asteroid);
    }
    public void returnPillToPool(Item_RestoreLife pill){pillPool.add(pill);}
}
