package dadm.scaffold.items;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.space.GameController;

public class Item_Protection extends Sprite {
    private final GameController gameController;
    private double speed;
    private double speedY;
    public Item_Protection(GameController gameController, GameEngine gameEngine) {
        super(gameEngine, R.drawable.shield_silver);
        this.speed = 50d * pixelFactor/800d;
        this.gameController = gameController;
    }

    @Override
    public void startGame() {

    }

    public void removeObject(GameEngine gameEngine) {
        // Return to the pool
        gameEngine.removeGameObject(this);
        gameController.returnShieldsToPool(this);
    }
    public void init(GameEngine gameEngine) {
        speedY = speed;
        positionX = gameEngine.random.nextInt(gameEngine.width);
        // They initialize outside of the screen vertically
        positionY = -height;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += speedY * elapsedMillis;
        // Check of the sprite goes out of the screen and return it to the pool if so
        if (positionY > gameEngine.height) {
            // Return to the pool
            gameEngine.removeGameObject(this);
            gameController.returnShieldsToPool(this);
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }
}
