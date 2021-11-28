package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.sound.GameEvent;

public class EnemyMid extends Enemy{
    double speedX;
    double speedY;
    private int MAX_LIFES;
    GameController gameController;

    public EnemyMid(GameController gameController, GameEngine gameEngine) {
        super(gameController, gameEngine, R.drawable.enemy_mid,7,75,0);
        this.setSpeed(pixelFactor * 100d / 2500d);
        this.initBulletPool(gameEngine);
        this.gameController = gameController;
        this.MAX_LIFES=this.getLifes();
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
        speedX = this.getSpeed()*10 * Math.sin(angle);
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
        if (positionX<width){
            speedX=-speedX;

        }else if(positionX>(gameEngine.width-width)){
            speedX= -speedX;
        }
        if (positionY > gameEngine.height) {
            // Return to the pool
            gameEngine.removeGameObject(this);
            gameController.returnEnemyToPool(this);
        }
    }
    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        //Disparo primario
        if (this.getTimeSinceLastFire() > TIME_BETWEEN_BULLETS) {
            EnemyBullet bullet = getBullet();
            if (bullet == null) {
                return;
            }
            bullet.init(this, positionX + width/2, positionY+height);
            gameEngine.addGameObject(bullet);
            this.setTimeSinceLastFire(0);
            //gameEngine.onGameEvent(GameEvent.LaserFired);
        }
        this.setTimeSinceLastFire(this.getTimeSinceLastFire()+elapsedMillis);

    }
    private void changeSprite(GameEngine gameEngine, int life){
        if (life==(MAX_LIFES-1)){
            this.updateSprite(gameEngine,R.drawable.enemy_mid_2);
        }
        if (life==(MAX_LIFES-3)){
            this.updateSprite(gameEngine,R.drawable.enemy_mid_3);
        }
        if (life==(MAX_LIFES-4)){
            this.updateSprite(gameEngine,R.drawable.enemy_mid_4);
        }
        if (life==(MAX_LIFES-6)){
            this.updateSprite(gameEngine,R.drawable.enemy_mid_5);
        }
    }
    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Bullet) {
            int life=this.getLifes()-((Bullet) otherObject).getDAMAGE();
            this.setLifes(life);
            changeSprite(gameEngine, this.getLifes());

        }
        if (otherObject instanceof BigBullet) {
            int life=this.getLifes()-((BigBullet) otherObject).getDAMAGE();
            this.setLifes(life);
            changeSprite(gameEngine, this.getLifes());

        }
    }
}
