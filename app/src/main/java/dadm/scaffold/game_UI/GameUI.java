package dadm.scaffold.game_UI;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import dadm.scaffold.R;
import dadm.scaffold.counter.Communicator;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;
import dadm.scaffold.engine.Sprite;

public class GameUI extends GameObject {
    private final float textWidth_S;
    private final float textHeight_S;
    private int maxX;
    private int maxY;
    private Paint paint_S;
    private Paint paint_A;
    private long totalMillis;
    private String score="";
    private String ammo="";


public GameUI(GameEngine gameEngine){
    paint_S = new Paint();
    paint_S.setTextAlign(Paint.Align.CENTER);
    textHeight_S = (float) (100 * gameEngine.pixelFactor);
    textWidth_S = (float) (125 * gameEngine.pixelFactor);
    paint_S.setTextSize(textHeight_S / 2);


    paint_A = new Paint();
    paint_A.setTextAlign(Paint.Align.CENTER);
    paint_A.setTextSize(125);


    maxX = gameEngine.width;
    maxY = gameEngine.height;

}
    @Override
    public void startGame() {
        totalMillis = 0;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        totalMillis += elapsedMillis;
        if (totalMillis > 200) {
            score =String.valueOf(Communicator.getScore());
            ammo =String.valueOf(Communicator.getAmmo());

            totalMillis = 0;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint_S.setColor(Color.WHITE);
        canvas.drawText(score, (maxX-(maxX-50)), (maxY-(maxY-25)), paint_S);

        paint_A.setColor(Color.WHITE);
        canvas.drawText(ammo, (maxX -300), (maxY-100), paint_A);

 }
}
