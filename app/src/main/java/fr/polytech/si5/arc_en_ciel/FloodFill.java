package fr.polytech.si5.arc_en_ciel;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

public class FloodFill extends AsyncTask<Void, Void, Bitmap> {
    private FillColorActivity fillColorActivity;
    private Bitmap bmp;
    private Point pt;
    private int targetColor, replacementColor;
    private boolean colorBackground = false;

    public FloodFill(FillColorActivity activity, Bitmap bmp, Point pt, int replacementColor) {
        fillColorActivity = activity;
        this.bmp = bmp;
        this.targetColor = bmp.getPixel(pt.x, pt.y);
        this.replacementColor = replacementColor;
        this.pt = pt;
    }

    protected Bitmap doInBackground(Void... params) {
        if(targetColor < Color.parseColor("#111111")){
            return bmp;
        }
        if(targetColor == 0){
            colorBackground = true;
            return bmp;
        }

        int[] pixels = new int[bmp.getHeight()*bmp.getWidth()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0,
                bmp.getWidth(), bmp.getHeight());
        QueueLinearFloodFiller.floodFill(pixels, bmp.getWidth(), bmp.getHeight(), pt, targetColor, replacementColor, 80);
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        return bmp;
    }

    protected void onPostExecute(Bitmap result) {
        if(colorBackground){
            fillColorActivity.getImageView().setBackground(new ColorDrawable(replacementColor));
        }
        fillColorActivity.getImageView().setBitmap(bmp);
        fillColorActivity.setIsColoring(false);
    }
}
