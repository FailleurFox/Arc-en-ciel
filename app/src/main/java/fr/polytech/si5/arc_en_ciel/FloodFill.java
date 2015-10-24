package fr.polytech.si5.arc_en_ciel;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

public class FloodFill extends AsyncTask<Void, Void, Bitmap> {
    private FillColorView imageView;
    private Bitmap bmp;
    private Point pt;
    private int targetColor, replacementColor;

    public FloodFill(FillColorView imageView, Bitmap bmp, Point pt, int replacementColor) {
        this.imageView = imageView;
        this.bmp = bmp;
        this.targetColor = bmp.getPixel(pt.x, pt.y);
        this.replacementColor = replacementColor;
        this.pt = pt;
    }

    protected Bitmap doInBackground(Void... params) {
        /*// TEST
        for(int i=pt.x-2; i<pt.x+3; i++){
            for(int j=pt.y-2; j<pt.y+3; j++){
                bmp.setPixel(i, j, replacementColor);
            }
        }
        //*/
        Queue<Point> q = new LinkedList<Point>();
        q.add(pt);
        while (q.size() > 0) {
            Point n = q.poll();
            if (bmp.getPixel(n.x, n.y) != targetColor)
                continue;

            Point w = n, e = new Point(n.x + 1, n.y);
            while ((w.x > 0) && (bmp.getPixel(w.x, w.y) == targetColor)) {
                bmp.setPixel(w.x, w.y, replacementColor);
                if ((w.y > 0) && (bmp.getPixel(w.x, w.y - 1) == targetColor))
                    q.add(new Point(w.x, w.y - 1));
                if ((w.y < bmp.getHeight() - 1)
                        && (bmp.getPixel(w.x, w.y + 1) == targetColor))
                    q.add(new Point(w.x, w.y + 1));
                w.x--;
            }
            while ((e.x < bmp.getWidth() - 1)
                    && (bmp.getPixel(e.x, e.y) == targetColor)) {
                bmp.setPixel(e.x, e.y, replacementColor);

                if ((e.y > 0) && (bmp.getPixel(e.x, e.y - 1) == targetColor))
                    q.add(new Point(e.x, e.y - 1));
                if ((e.y < bmp.getHeight() - 1)
                        && (bmp.getPixel(e.x, e.y + 1) == targetColor))
                    q.add(new Point(e.x, e.y + 1));
                e.x++;
            }
        }
        return bmp;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setBitmap(bmp);
    }
}
