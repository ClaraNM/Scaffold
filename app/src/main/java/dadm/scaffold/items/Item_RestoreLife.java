package dadm.scaffold.items;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.space.GameController;

public class Item_RestoreLife extends Sprite {
    private final GameController gameController;
    private double speed;
    private double speedY;
    private double rotationSpeed;

    public Item_RestoreLife(GameController gameController, GameEngine gameEngine) {
        super(gameEngine, R.drawable.pill_red);
        this.speed = 50d * pixelFactor/950d;
        this.gameController = gameController;
    }

    @Override
    public void startGame() {

    }

    public void removeObject(GameEngine gameEngine) {
        // Return to the pool
        gameEngine.removeGameObject(this);
        gameController.returnPillToPool(this);
    }

    public void init(GameEngine gameEngine) {
        double angle = gameEngine.random.nextDouble()*Math.PI/3d-Math.PI/6d;
        speedY = speed;
        positionX = gameEngine.random.nextInt(gameEngine.width)+gameEngine.width/2;
        // They initialize outside of the screen vertically
        positionY = -height;
        rotationSpeed = angle*(180d / Math.PI)/250d; // They rotate 4 times their ange in a second.
        rotation = gameEngine.random.nextInt(360);
    }


    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY += speedY * elapsedMillis;
        rotation += rotationSpeed * elapsedMillis;
        if (rotation > 360) {
            rotation = 0;
        }
        else if (rotation < 0) {
            rotation = 360;
        }
        // Check of the sprite goes out of the screen and return it to the pool if so
        if (positionY > gameEngine.height) {
            // Return to the pool
            gameEngine.removeGameObject(this);
            gameController.returnPillToPool(this);
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }
}
