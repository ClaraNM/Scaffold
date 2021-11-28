package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.counter.Communicator;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.sound.GameEvent;

public class EnemyBullet extends Sprite {

    private final int DAMAGE=1;
    private double speedFactor;
    private Enemy parent;


    public EnemyBullet(GameEngine gameEngine) {
        super(gameEngine, R.drawable.enemy_laser);
        speedFactor = gameEngine.pixelFactor * 100d / 1000d;
        max=false;

    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += speedFactor * elapsedMillis;
        if (positionY < -height) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBullet(this);
        }
    }
    public void init(Enemy parentEnemy, double initPositionX, double initPositionY) {
        positionX = initPositionX - width/2;
        positionY = initPositionY - height/2;
        parent = parentEnemy;
    }

    private void removeObject(GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
        // And return it to the pool
        parent.releaseBullet(this);
    }

    public int getDAMAGE() {
        return DAMAGE;
    }
    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof SpaceShipPlayer) {
            if (((SpaceShipPlayer) otherObject).getLifes()>0){
                removeObject(gameEngine);
                gameEngine.onGameEvent(GameEvent.AsteroidHit);
            }else {
                // Remove both from the game (and return them to their pools)
                removeObject(gameEngine);
                SpaceShipPlayer a = (SpaceShipPlayer) otherObject;
                gameEngine.removeGameObject(a);
                gameEngine.onGameEvent(GameEvent.AsteroidHit);
            }
        }
    }
}
