package dadm.scaffold.game_UI;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;

public class AmmoTab extends Sprite {
    int maxX;
    int maxY;
    public AmmoTab(GameEngine gameEngine) {
        super(gameEngine, R.drawable.ammo_tab);
        maxX = gameEngine.width - width;
        maxY = gameEngine.height - height;
    }

    @Override
    public void startGame() {
        positionX=maxX-20;
        positionY=maxY-20;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }
}
