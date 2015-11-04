package fr.polytech.si5.arc_en_ciel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class FillColorActivity extends Activity {
    private FillColorView colorsView;
    private FillColorView imageView;
    private ImageButton currPaint;
    private Bitmap bmpColors;
    private Bitmap bmp;
    private int color;
    private boolean isColoring = false;
    private Mode mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_color);

        imageView = (FillColorView) findViewById(R.id.drawing);
        colorsView = (FillColorView) findViewById(R.id.colors);

        mode = (Mode) getIntent().getSerializableExtra("mode");
        Integer imageId = getIntent().getIntExtra("imageId", 0);

        Point screenDimensions = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenDimensions);

        bmpColors = null;
        if(mode.equals(Mode.HARD)) {
            switch (imageId) {
                case R.drawable.poisson:
                    bmpColors = BitmapFactory.decodeResource(getResources(), R.drawable.poisson_colors);
                    break;
                case R.drawable.tortue:
                    bmpColors = BitmapFactory.decodeResource(getResources(), R.drawable.tortue_colors);
                    break;
            }
            double ratio = ((double) screenDimensions.x) / bmpColors.getWidth();
            bmpColors = Bitmap.createScaledBitmap(bmpColors, screenDimensions.x, (int) (bmpColors.getHeight() * ratio), true);

            colorsView.setBitmap(bmpColors);
        }

        bmp = BitmapFactory.decodeResource(getResources(), imageId);
        double ratio = ((double) screenDimensions.x) / bmp.getWidth();
        bmp = bmp.copy(bmp.getConfig(), true);
        bmp = Bitmap.createScaledBitmap(bmp, screenDimensions.x, (int)(bmp.getHeight()*ratio), true);

        imageView.setBitmap(bmp);

        LinearLayout paintLayout = (LinearLayout) findViewById(R.id.linLay);
        currPaint = (ImageButton) paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
        color = Color.parseColor(currPaint.getTag().toString());

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && !isColoring) {
                    float touchX = motionEvent.getX();
                    float touchY = motionEvent.getY();

                    if(mode.equals(Mode.HARD) && bmpColors.getPixel((int)touchX, (int)touchY) != color){
                        return true;
                    }
                    isColoring = true;

                    Point p = new Point((int)touchX, (int)touchY);

                    new FloodFill(FillColorActivity.this, bmp, p, color).execute();
                }
                return true;
            }
        });

    }

    public void paintClicked(View view) {
        // use chosen color
        if (view != currPaint) {
            ImageButton imgView = (ImageButton) view;
            String color = view.getTag().toString();
            this.color = Color.parseColor(color);

            // clean the previous selection
            //if(currPaint.getId() != (R.id.erase_btn)) {
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            //}
            //else { // special case : eraser
            //    currPaint.setBackgroundColor(getResources().getColor(R.color.myGrey));
            // }

            // highlight the new selection
            currPaint = (ImageButton) imgView;
            //  if(imgView.getId() != (R.id.erase_btn)) {
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            // }
            // else {   // special case : eraser
            // imgView.setBackgroundColor(getResources().getColor(R.color.myWhite));
            //}
        }
    }

    public void save(View view) {
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

        // active cache
        imageView.setDrawingCacheEnabled(true);

        // save
        String imgSaved = MediaStore.Images.Media.insertImage(
                getContentResolver(), imageView.getDrawingCache(),
                UUID.randomUUID().toString() + ".png", "drawing");

        // verify
        if (imgSaved != null) {
            Toast savedToast = Toast.makeText(getApplicationContext(),
                    "Image sauvegardée dans la galerie !", Toast.LENGTH_LONG);
            savedToast.show();
        } else {
            Toast unsavedToast = Toast.makeText(getApplicationContext(),
                    "Oups! L'image n'a pas été sauvegardée...", Toast.LENGTH_LONG);
            unsavedToast.show();
        }

        // desactive cache
        imageView.destroyDrawingCache();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Retour")
                .setMessage("Etes-vous sûr de vouloir quitter le coloriage ?")
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    public FillColorView getImageView(){
        return imageView;
    }

    public boolean isColoring(){
        return isColoring;
    }

    public void setIsColoring(boolean b){
        isColoring = b;
    }
}
