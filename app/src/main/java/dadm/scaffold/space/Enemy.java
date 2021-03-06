package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public abstract class Enemy extends Sprite {
    private int MAX_LIFES;
private int lifes;
private int points;
private double speed;

    private final GameController gameController;

    public static final int INITIAL_BULLET_POOL_AMOUNT = 12;
    public static final long TIME_BETWEEN_BULLETS = 1000;
    List<EnemyBullet> bullets = new ArrayList<EnemyBullet>();
    private long timeSinceLastFire;

    public Enemy(GameController gameController,GameEngine gameEngine, int drawableRes, int lifes, int points, double speed) {
        super(gameEngine, drawableRes);
        this.gameController=gameController;
        this.MAX_LIFES=lifes;
        this.lifes=MAX_LIFES;
        this.points=points;
        this.speed=speed;
    }
    void releaseBullet(EnemyBullet bullet) {
        bullets.add(bullet);
    }
    public void removeObject(GameEngine gameEngine) {
        // Return to the pool
        gameEngine.removeGameObject(this);
        this.setLifes(MAX_LIFES);
        gameController.returnEnemyToPool(this);
    }

    public void setLifes(int lifes) { this.lifes = lifes; }
    public int getLifes() { return lifes; }

    public int getPoints() { return points; }
    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }
    public long getTimeSinceLastFire() { return timeSinceLastFire; }
    public void setTimeSinceLastFire(long timeSinceLastFire) { this.timeSinceLastFire = timeSinceLastFire; }
}
