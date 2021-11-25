package dadm.scaffold.game_UI;

import dadm.scaffold.R;
import dadm.scaffold.counter.Communicator;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;

public class PlayerLife extends Sprite {

    int maxX;
    int maxY;

    public PlayerLife(GameEngine gameEngine) {
        super(gameEngine, R.drawable.player_life_full);
        maxX = gameEngine.width - width;
        maxY = gameEngine.height - height;
    }

    @Override
    public void startGame() {
        positionX=maxX-(maxX-20);
        positionY=maxY-20;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        if (Communicator.getLifes()==2){
            this.updateSprite(gameEngine, R.drawable.player_life_mid);
        }
        if(Communicator.getLifes()==1){
            this.updateSprite(gameEngine, R.drawable.player_life_low);
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }
}
