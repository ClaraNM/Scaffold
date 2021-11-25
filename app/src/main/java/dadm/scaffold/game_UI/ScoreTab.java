package dadm.scaffold.game_UI;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;

public class ScoreTab extends Sprite {
    int maxX;
    int maxY;
    public ScoreTab(GameEngine gameEngine) {
        super(gameEngine, R.drawable.score_tab);
        maxX = gameEngine.width - width;
        maxY = gameEngine.height - height;
    }

    @Override
    public void startGame() {
        positionX=maxX-(maxX-20);
        positionY=maxY-(maxY-20);
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

    }

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }
}
