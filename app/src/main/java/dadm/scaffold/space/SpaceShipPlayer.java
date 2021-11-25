package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.counter.Communicator;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.ScreenGameObject;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.input.InputController;
import dadm.scaffold.items.Item_DoubleShoot;
import dadm.scaffold.items.Item_Protection;
import dadm.scaffold.items.Item_RestoreAmmo;
import dadm.scaffold.items.Item_RestoreLife;
import dadm.scaffold.sound.GameEvent;

public class SpaceShipPlayer extends Sprite {

    private static final int INITIAL_BULLET_POOL_AMOUNT = 12;
    private static final int MAX_BIG_BULLET_AMMO = 6;
    private static final long TIME_BETWEEN_BULLETS = 500;
    private static final long MAX_TIME_ON_SHIELD = 15000;
    List<Bullet> bullets = new ArrayList<Bullet>();
    List<BigBullet> big_bullets= new ArrayList<BigBullet>();
    private long timeSinceLastFire;
    private long timeSinceLastShield;
    private final int MAX_LIFES=3;
    private int lifes=MAX_LIFES;
    private int maxX;
    private int maxY;
    private int ammo = MAX_BIG_BULLET_AMMO;;
    private boolean doubleShoot=false;
    private boolean protection=false;
    private double speedFactor;


    public SpaceShipPlayer(GameEngine gameEngine, int ship_ID){
        super(gameEngine, ship_ID);
        speedFactor = pixelFactor * 100d / 1000d; // We want to move at 100px per second on a 400px tall screen
        maxX = gameEngine.width - width;
        maxY = gameEngine.height - height;
        Communicator.setAmmo(ammo);
        Communicator.setLifes(this.lifes);
        initBulletPool(gameEngine);
        initBigBulletPool(gameEngine);
    }

    //Disparo principal/////////////////
    private void initBulletPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new Bullet(gameEngine));
        }
    }

    private Bullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    void releaseBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    //Disparo secundario/////////////
    private void initBigBulletPool(GameEngine gameEngine) {
        for (int i=0; i<MAX_BIG_BULLET_AMMO; i++) {
            big_bullets.add(new BigBullet(gameEngine));
        }
    }

    private BigBullet getBigBullet() {
        if (big_bullets.isEmpty()) {
            return null;
        }
        return big_bullets.remove(0);
    }

    void releaseBigBullet(BigBullet bullet) {
        big_bullets.add(bullet);
    }


    @Override
    public void startGame() {
        positionX = maxX / 2;
        positionY = maxY / 2;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        // Get the info from the inputController
        updatePosition(elapsedMillis, gameEngine.theInputController);
        checkFiring(elapsedMillis, gameEngine);
        if (protection==true){
            checkShieldTime(elapsedMillis, gameEngine);
        }
    }

    private void updatePosition(long elapsedMillis, InputController inputController) {
        positionX += speedFactor * inputController.horizontalFactor * elapsedMillis;
        if (positionX < 0) {
            positionX = 0;
        }
        if (positionX > maxX) {
            positionX = maxX;
        }
        positionY += speedFactor * inputController.verticalFactor * elapsedMillis;
        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > maxY) {
            positionY = maxY;
        }
    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        //Disparo primario
        if (timeSinceLastFire > TIME_BETWEEN_BULLETS) {
            if (doubleShoot==false){
                Bullet bullet = getBullet();
                if (bullet == null) {
                    return;
                }
                bullet.init(this, positionX + width/2, positionY);
                gameEngine.addGameObject(bullet);
            }else{
                Bullet bullet1 = getBullet();
                Bullet bullet2 = getBullet();

                if (bullet1 == null || bullet2 == null) {
                    return;
                }
                bullet1.init(this, positionX , positionY);
                bullet2.init(this, positionX +width, positionY);
                gameEngine.addGameObject(bullet1);
                gameEngine.addGameObject(bullet2);

            }

            timeSinceLastFire = 0;
            gameEngine.onGameEvent(GameEvent.LaserFired);
        }
        //Disparo Secundario
        else if (gameEngine.theInputController.isFiring && timeSinceLastFire > (TIME_BETWEEN_BULLETS/2)&&ammo>0){
            BigBullet big_bullet = getBigBullet();

            if (big_bullet == null) {
                return;
            }

            big_bullet.init(this, positionX + width, positionY);

            gameEngine.addGameObject(big_bullet);
            timeSinceLastFire = 0;
            gameEngine.onGameEvent(GameEvent.LaserFired);
            ammo--;
            Communicator.setAmmo(ammo);
        }
            timeSinceLastFire += elapsedMillis;
    }

    public void checkShieldTime(long elapsedMillis, GameEngine gameEngine){
        if (timeSinceLastShield > MAX_TIME_ON_SHIELD){
            gameEngine.onGameEvent(GameEvent.ShieldDown);
            timeSinceLastShield=0;
    protection=false;
        }
        timeSinceLastShield += elapsedMillis;

    }

    public double [] getShipPosition(){
        double[] ship_position= new double[2];
        ship_position[0]=positionX;
        ship_position[1]=positionY;
        return ship_position;
    }
public int getShipWidth(){
        return this.width;
}
    public int getShipHeight(){
        return this.height;
    }
    public int getLifes(){return lifes;}

    @Override
    public void onCollision(GameEngine gameEngine, ScreenGameObject otherObject) {
        //Colision con asteriode
        if (otherObject instanceof Asteroid) {
            if (protection==false){
                if (lifes>1){
                  lifes--;
                    Communicator.loseLife();
                }else {
                   gameEngine.removeGameObject(this);
                }
            }
            Asteroid a = (Asteroid) otherObject;
            a.removeObject(gameEngine);
            gameEngine.onGameEvent(GameEvent.SpaceshipHit);
        }
//Colision con curar vidad
        if (otherObject instanceof Item_RestoreLife){
            lifes=MAX_LIFES;
            Item_RestoreLife i = (Item_RestoreLife) otherObject;
            i.removeObject(gameEngine);
        }
 //Colision con recuperar municion
    if (otherObject instanceof Item_RestoreAmmo){
        ammo=MAX_BIG_BULLET_AMMO;
        Item_RestoreAmmo i = (Item_RestoreAmmo) otherObject;
        i.removeObject(gameEngine);
    }
 //Colision con item disparo doble
 if (otherObject instanceof Item_DoubleShoot){
     doubleShoot=true;
     Item_DoubleShoot i = (Item_DoubleShoot) otherObject;
     i.removeObject(gameEngine);
 }
 //Colision con item escudo
        if (otherObject instanceof Item_Protection){
            protection=true;
            Item_Protection i = (Item_Protection) otherObject;
            i.removeObject(gameEngine);
            Shield shield=new Shield(gameEngine, this);
            gameEngine.addGameObject(shield);
            gameEngine.onGameEvent(GameEvent.ShieldUp);
        }
        //Colision con nave enemiga
        if(otherObject instanceof  Enemy){
            if (protection==false) {
                lifes = 0;
                Enemy e = (Enemy) otherObject;
                e.removeObject(gameEngine);
                gameEngine.removeGameObject(this);
            }else{
                Enemy e = (Enemy) otherObject;
                e.removeObject(gameEngine);
            }
        }
        //Colision con bala enemiga
        if (otherObject instanceof EnemyBullet) {
            if (protection==false){
            if (lifes>1){
                lifes=lifes-((EnemyBullet) otherObject).getDAMAGE();
                Communicator.loseLife();
            }else {
                gameEngine.onGameEvent(GameEvent.Lose);
                gameEngine.removeGameObject(this);
            }

        }
        }
    }
}
