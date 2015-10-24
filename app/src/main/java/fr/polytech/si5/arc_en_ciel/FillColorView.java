package fr.polytech.si5.arc_en_ciel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class FillColorView extends View {
    private Bitmap bmp;


    public FillColorView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(bmp != null) {
            canvas.drawBitmap(bmp, 0, 0, new Paint(Paint.ANTI_ALIAS_FLAG));
        }
    }


    public Bitmap getBitmap() {
        return bmp;
    }

    public void setBitmap(Bitmap bmp) {
        this.bmp = bmp;
        invalidate();
    }
}