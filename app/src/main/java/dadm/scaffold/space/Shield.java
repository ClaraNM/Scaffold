package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.input.InputController;

public class Shield extends Sprite {

    private static final long MAX_TIME_ON_SHIELD = 15000;
    private long timeSinceLastShield;
    private double []shield_position;
    SpaceShipPlayer shipPlayer;


    public Shield(GameEngine gameEngine, SpaceShipPlayer player) {
        super(gameEngine, R.drawable.shield);
        this.shipPlayer=player;
    }

    @Override
    public void startGame() {
        shield_position=shipPlayer.getShipPosition();
        positionX=shield_position[0]-(shipPlayer.getShipWidth()/2);
        positionY=shield_position[1]-(shipPlayer.getShipHeight()/2);
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        updatePosition();
        checkShieldTime(elapsedMillis, gameEngine);
    }
    private void updatePosition() {
        shield_position=shipPlayer.getShipPosition();
        positionX=shield_position[0]-(shipPlayer.getShipWidth()/2);
        positionY=shield_position[1]-(shipPlayer.getShipHeight()/2);

    }
    public void checkShieldTime(long elapsedMillis, GameEngine gameEngine){
        if (timeSinceLastShield > MAX_TIME_ON_SHIELD){
            timeSinceLastShield=0;
            gameEngine.removeGameObject(this);
        }
        timeSinceLastShield += elapsedMillis;

    }
    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {

    }
}
