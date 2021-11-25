package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.counter.Communicator;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.sound.GameEvent;

public class BigBullet extends Sprite {
    private final int DAMAGE=5;
    private double speedFactor;

    private SpaceShipPlayer parent;

    public BigBullet(GameEngine gameEngine) {
        super(gameEngine, R.drawable.player_big_laser);
        speedFactor = gameEngine.pixelFactor * -500d / 1000d;
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += speedFactor * elapsedMillis;
        if (positionY < -height) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBigBullet(this);
        }
    }


    public void init(SpaceShipPlayer parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - width/2;
        positionY = initPositionY - height/2;
        parent = parentPlayer;
    }

    private void removeObject(GameEngine gameEngine) {
        gameEngine.removeGameObject(this);
        // And return it to the pool
        parent.releaseBigBullet(this);
    }

    public int getDAMAGE() {
        return DAMAGE;
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        if (otherObject instanceof Asteroid) {
            if (((Asteroid) otherObject).getLife()>0){
                removeObject(gameEngine);
                gameEngine.onGameEvent(GameEvent.AsteroidHit);
            }else {
                // Remove both from the game (and return them to their pools)
                Asteroid a = (Asteroid) otherObject;
                // Add some score
                Communicator.addHit(a.points);

                a.removeObject(gameEngine);
                gameEngine.onGameEvent(GameEvent.AsteroidHit);
            }
        }
        if(otherObject instanceof Enemy){
            if (((Enemy) otherObject).getLifes()>0){
                removeObject(gameEngine);
                gameEngine.onGameEvent(GameEvent.AsteroidHit);
            }else {
                // Remove both from the game (and return them to their pools)
                removeObject(gameEngine);
                Enemy e = (Enemy) otherObject;
                // Add some score
                Communicator.addHit(e.getPoints());

                e.removeObject(gameEngine);
                gameEngine.onGameEvent(GameEvent.AsteroidHit);


            }
        }
    }
}
