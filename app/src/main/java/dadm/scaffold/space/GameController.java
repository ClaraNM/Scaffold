package dadm.scaffold.space;

import android.content.ClipData;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;
import dadm.scaffold.items.Item_DoubleShoot;
import dadm.scaffold.items.Item_Protection;
import dadm.scaffold.items.Item_RestoreAmmo;
import dadm.scaffold.items.Item_RestoreLife;

public class GameController extends GameObject {

    private static final int TIME_BETWEEN_ASTEROIDS = 500;
    private static final int TIME_BETWEEN_ENEMIES = 5000;
    private static final int TIME_BETWEEN_RESTORE_ITEMS = 20000;
    private static final int TIME_BETWEEN_AMMO_ITEMS = 25000;
    private static final int TIME_BETWEEN_DOUBLESHOOT_ITEMS = 100000;
    private static final int TIME_BETWEEN_SHIELD_ITEMS = 50000;



    private long currentMillis;
    private List<Asteroid> asteroidPool = new ArrayList<Asteroid>();
    private List<Item_RestoreLife> pillPool = new ArrayList<Item_RestoreLife>();
    private List<Item_RestoreAmmo> ammoPool = new ArrayList<Item_RestoreAmmo>();
    private List<Item_DoubleShoot> dsPool = new ArrayList<Item_DoubleShoot>();
    private List<Item_Protection> shieldPool = new ArrayList<Item_Protection>();
    private List<Enemy> enemyPool = new ArrayList<Enemy>();

    Random rand = new Random();

    private int enemiesSpawned;
    private int asteroidsSpawned;
    private int pillsSpawned;
    private int ammoSpawned;
    private int doubleShootSpawned;
    private int shieldsSpawned;


    public GameController(GameEngine gameEngine) {
        // We initialize the pool of items now
        for (int i=0; i<10; i++) {
            asteroidPool.add(new Asteroid(this, gameEngine));
        }
        for (int i=0; i<20; i++) {
            if (i>=11&& i<17){
                enemyPool.add(new EnemyMid(this, gameEngine));

            }else if (i>=17){
                enemyPool.add(new EnemyHard(this, gameEngine));
            }else{
                enemyPool.add(new EnemyWeak(this, gameEngine));
            }
        }
        for (int i = 0; i < 10; i++) {
            pillPool.add(new Item_RestoreLife(this, gameEngine));
        }
        for (int i = 0; i < 10; i++) {
            ammoPool.add(new Item_RestoreAmmo(this, gameEngine));
        }
        for (int i = 0; i < 10; i++) {
            dsPool.add(new Item_DoubleShoot(this, gameEngine));
        }
        for (int i = 0; i < 10; i++) {
            shieldPool.add(new Item_Protection(this, gameEngine));
        }
    }

    @Override
    public void startGame() {
        currentMillis = 0;
        enemiesSpawned = 0;
        pillsSpawned=0;
        ammoSpawned =0;
        doubleShootSpawned=0;
        shieldsSpawned=0;
        asteroidsSpawned=0;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        currentMillis += elapsedMillis;

        long waveTimestamp = enemiesSpawned*TIME_BETWEEN_ENEMIES;
        long waveAsteroids=asteroidsSpawned*TIME_BETWEEN_ASTEROIDS;
        long waveItemsR=pillsSpawned*TIME_BETWEEN_RESTORE_ITEMS;
        long waveItemsA=ammoSpawned*TIME_BETWEEN_AMMO_ITEMS;
        long waveItemsDS=doubleShootSpawned*TIME_BETWEEN_DOUBLESHOOT_ITEMS;
        long waveItemsS=shieldsSpawned*TIME_BETWEEN_SHIELD_ITEMS;


        if (currentMillis > waveAsteroids) {
            // Spawn a new asteroid
            Asteroid a = asteroidPool.remove(0);
            a.init(gameEngine);
            gameEngine.addGameObject(a);
            asteroidsSpawned++;
            return;
        }
        if (currentMillis > waveTimestamp) {
            // Spawn a new enemy
            Enemy eAux= enemyPool.remove(0);
            if (eAux instanceof EnemyMid){
                EnemyMid e = (EnemyMid) eAux;
                e.init(gameEngine);
                gameEngine.addGameObject(e);
            }else if (eAux instanceof EnemyHard){
                EnemyHard e = (EnemyHard) eAux;
                e.init(gameEngine);
                gameEngine.addGameObject(e);
            }else if(eAux instanceof EnemyWeak) {
                EnemyWeak e = (EnemyWeak) eAux;
                e.init(gameEngine);
                gameEngine.addGameObject(e);
            }
            enemiesSpawned++;
            return;
        }
        if (currentMillis>waveItemsR){
            Item_RestoreLife p = pillPool.remove(0);
            p.init(gameEngine);
            gameEngine.addGameObject(p);
            pillsSpawned++;
            return;
        }
        if (currentMillis>waveItemsA){
            Item_RestoreAmmo a = ammoPool.remove(0);
            a.init(gameEngine);
            gameEngine.addGameObject(a);
            ammoSpawned++;
            return;
        }
        if (currentMillis>waveItemsDS){
            Item_DoubleShoot ds = dsPool.remove(0);
            ds.init(gameEngine);
            gameEngine.addGameObject(ds);
            doubleShootSpawned++;
            return;
        }
        if (currentMillis>waveItemsS){
            Item_Protection p = shieldPool.remove(0);
            p.init(gameEngine);
            gameEngine.addGameObject(p);
            shieldsSpawned++;
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
    public void returnEnemyToPool(Enemy enemy) {
        enemyPool.add(enemy);
    }
    public void returnPillToPool(Item_RestoreLife pill){pillPool.add(pill);}
    public void returnAmmoToPool(Item_RestoreAmmo ammo){ammoPool.add(ammo);}
    public void returnDoubleShootToPool(Item_DoubleShoot ds){dsPool.add(ds);}
    public void returnShieldsToPool(Item_Protection shield){shieldPool.add(shield);}


}
