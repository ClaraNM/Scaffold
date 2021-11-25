package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.counter.Communicator;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.sound.GameEvent;

public class EnemyHard extends Enemy{
double speedX;
double speedY;
GameController gameController;
    public EnemyHard(GameController gameController, GameEngine gameEngine) {
        super(gameController, gameEngine, R.drawable.enemy_pro, 6,100,0);
        this.setSpeed(pixelFactor * 100d / 2000d);
        this.initBulletPool(gameEngine);
        this.gameController = gameController;

    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new EnemyBullet(gameEngine));
        }
    }

    private EnemyBullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    void releaseBullet(EnemyBullet bullet) {
        bullets.add(bullet);
    }


    @Override
    public void startGame() {

    }
    public void init(GameEngine gameEngine) {
        // They initialize in a [-30, 30] degrees angle
        double angle = gameEngine.random.nextDouble()*Math.PI/3d-Math.PI/6d;
        speedX = this.getSpeed() * Math.sin(angle);
        speedY = this.getSpeed() * Math.cos(angle);
        positionX = gameEngine.random.nextInt(gameEngine.width/2)+gameEngine.width/4;
        // They initialize outside of the screen vertically
        positionY = -height;
    }
    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        checkFiring(elapsedMillis,gameEngine);
        positionX += speedX * elapsedMillis;
        positionY += speedY * elapsedMillis;
        checkFiring(elapsedMillis, gameEngine);
        // Check of the sprite goes out of the screen and return it to the pool if so
        if (positionY > gameEngine.height) {
            // Return to the pool
            gameEngine.removeGameObject(this);
            gameController.returnEnemyToPool(this);
        }
    }
    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (this.getTimeSinceLastFire() > TIME_BETWEEN_BULLETS) {
                EnemyBullet bullet = getBullet();
                if (bullet == null) {
                    return;
                }
                bullet.init(this, positionX + width/2, positionY+height);
                gameEngine.addGameObject(bullet);
            this.setTimeSinceLastFire(0);
            gameEngine.onGameEvent(GameEvent.LaserFired);
        }
        this.setTimeSinceLastFire(this.getTimeSinceLastFire()+elapsedMillis);

    }
    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Bullet) {
            int life=this.getLifes()-((Bullet) otherObject).getDAMAGE();
            this.setLifes(life);
        }
        if (otherObject instanceof BigBullet) {
            int life=this.getLifes()-((BigBullet) otherObject).getDAMAGE();
            this.setLifes(life);
        }
    }
}
