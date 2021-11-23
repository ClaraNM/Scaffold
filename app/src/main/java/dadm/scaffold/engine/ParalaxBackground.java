package dadm.scaffold.engine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import dadm.scaffold.R;

public class ParalaxBackground extends GameObject{

    private final double speed = 50d / 1000d;
    private double speedFactor;
    private double pixelFactor;
    private double posY;
    private double height;

    private Bitmap bitmap;
    private final Matrix matrix = new Matrix();


    public ParalaxBackground(GameEngine gameEngine, int drawableRes, int n)
    {
        Resources r = gameEngine.getContext().getResources();
        Drawable spriteDrawable = r.getDrawable(drawableRes);
        height = spriteDrawable.getIntrinsicHeight();
        speedFactor = gameEngine.pixelFactor * speed;
        pixelFactor = gameEngine.pixelFactor;
        posY = -n * height;
        this.bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();
    }

    @Override
    public void startGame() { }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        posY += speedFactor * elapsedMillis;
        if (posY > height) {
            posY -= height * 2;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        matrix.reset();
        matrix.postScale((float) pixelFactor, (float) pixelFactor);
        matrix.postTranslate(0f, (float) posY);
        canvas.drawBitmap(bitmap, matrix, null);
    }

}
