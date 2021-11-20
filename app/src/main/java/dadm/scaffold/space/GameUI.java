package dadm.scaffold.space;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import dadm.scaffold.R;
import dadm.scaffold.counter.Communicator;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameObject;

public class GameUI extends GameObject {
    private final float textWidth_S;
    private final float textHeight_S;
    private final float textWidth_L;
    private final float textHeight_L;
private int maxX;
private int maxY;
    private Paint paint_S;
    private Paint paint_L;
    private long totalMillis;
private String score="";
private String lifes="";

public GameUI(GameEngine gameEngine){
    paint_S = new Paint();
    paint_S.setTextAlign(Paint.Align.CENTER);
    textHeight_S = (float) (75 * gameEngine.pixelFactor);
    textWidth_S = (float) (125 * gameEngine.pixelFactor);
    paint_S.setTextSize(textHeight_S / 2);

    paint_L = new Paint();
    paint_L.setTextAlign(Paint.Align.CENTER);
    textHeight_L = (float) (75 * gameEngine.pixelFactor);
    textWidth_L = (float) (125 * gameEngine.pixelFactor);
    paint_L.setTextSize(textHeight_L / 2);


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
            lifes =String.valueOf(Communicator.getLifes());
            totalMillis = 0;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint_S.setColor(Color.WHITE);
        canvas.drawRect(maxX/25, 50, textWidth_S-20, textHeight_S-15, paint_S);
        paint_S.setColor(Color.BLACK);
        canvas.drawText(score, (maxX /25)+(textWidth_S/3), textHeight_S/2+50, paint_S);

        paint_L.setColor(Color.RED);
        canvas.drawText(lifes, (maxX /7), (maxY-50), paint_L);
    }
}
