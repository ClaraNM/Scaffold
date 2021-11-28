package dadm.scaffold.engine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;



public abstract class Sprite extends ScreenGameObject {
protected boolean max;
    Paint mPaint;
    protected double rotation;

    protected double pixelFactor;

    private Bitmap bitmap;

    private final Matrix matrix = new Matrix();

    protected Sprite (GameEngine gameEngine, int drawableRes) {
        Resources r = gameEngine.getContext().getResources();
        Drawable spriteDrawable = r.getDrawable(drawableRes);
        this.mPaint=new Paint();
        this.pixelFactor = gameEngine.pixelFactor;

        this.max=false;
        this.width = (int) (spriteDrawable.getIntrinsicWidth() * this.pixelFactor);
        this.height = (int) (spriteDrawable.getIntrinsicHeight() * this.pixelFactor);

        this.bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();

        //Coge la colision mínima o la máxima segun el objeto
        if (max==true){
            radius = Math.max(height, width)/2;

        }else {
            radius = Math.min(height, width)/2;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (positionX > canvas.getWidth()
                || positionY > canvas.getHeight()
                || positionX < - width
                || positionY < - height) {
            return;
        }

        //Pintar colisiones
        mPaint.setColor(Color.YELLOW);
        mPaint.setAlpha(50);
       /* canvas.drawCircle(
                (int) (positionX + width / 2),
                (int) (positionY + height / 2),
                (int) radius,
                mPaint);*/
        matrix.reset();
        matrix.postScale((float) pixelFactor, (float) pixelFactor);
        matrix.postTranslate((float) positionX, (float) positionY);
        matrix.postRotate((float) rotation, (float) (positionX + width/2), (float) (positionY + height/2));
        canvas.drawBitmap(bitmap, matrix, null);
    }

    public void updateSprite(GameEngine gameEngine, int drawableRes){
        Resources r = gameEngine.getContext().getResources();
        Drawable spriteDrawable = r.getDrawable(drawableRes);

        this.pixelFactor = gameEngine.pixelFactor;

        this.width = (int) (spriteDrawable.getIntrinsicWidth() * this.pixelFactor);
        this.height = (int) (spriteDrawable.getIntrinsicHeight() * this.pixelFactor);

        this.bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();

        if (max==true){
            radius = Math.max(height, width)/2;

        }else {
            radius = Math.min(height, width)/2;
        }
    }
}
