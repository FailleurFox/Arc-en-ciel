package fr.polytech.si5.arc_en_ciel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

public class FillColorActivity extends Activity {
    private FillColorView imageView;
    private ImageButton currPaint;
    private Bitmap bmpColors;
    private Bitmap bmp;
    private int color;
    private boolean isColoring = false;
    private Mode mode;
    private int nbCircles;

    private SpeechSynthesis speech;
    private MediaPlayer mpWrongSound;
    private MediaPlayer mpApplauseSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_color);

        // thread pour synthèse vocale
        speech = new SpeechSynthesis() {
            protected void onPostExecute(Boolean result) {
            }
        };
        speech.context = getApplicationContext();
        speech.execute();

        // Init the mediaPlayer pour jouer un mp3 (Son quand on se trompe)
        mpWrongSound = MediaPlayer.create(this, R.raw.wrongsound);
        mpApplauseSound = MediaPlayer.create(this, R.raw.applausesound);
       // mp.start();

        imageView = (FillColorView) findViewById(R.id.drawing);
        FillColorView colorsView = (FillColorView) findViewById(R.id.colors);

        mode = (Mode) getIntent().getSerializableExtra("mode");
        Integer imageId = getIntent().getIntExtra("imageId", 0);

        Point screenDimensions = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenDimensions);

        if (mode.equals(Mode.HARD)) {
            Integer colorsId = getIntent().getIntExtra("colorsId", 0);
            nbCircles = getIntent().getIntExtra("nbCircles", 0);
            bmpColors = BitmapFactory.decodeResource(getResources(), colorsId);
            double ratio = ((double) screenDimensions.x) / bmpColors.getWidth();
            bmpColors = Bitmap.createScaledBitmap(bmpColors, screenDimensions.x, (int) (bmpColors.getHeight() * ratio), true);

            colorsView.setBitmap(bmpColors);
        }

        bmp = BitmapFactory.decodeResource(getResources(), imageId);
        double ratio = ((double) screenDimensions.x) / bmp.getWidth();
        bmp = bmp.copy(bmp.getConfig(), true);
        bmp = Bitmap.createScaledBitmap(bmp, screenDimensions.x, (int) (bmp.getHeight() * ratio), true);

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

                    if (mode.equals(Mode.HARD) && bmpColors.getPixel((int) touchX, (int) touchY) != color
                            || bmp.getPixel((int) touchX, (int) touchY) == color
                            || (bmp.getPixel((int) touchX, (int) touchY) == 0 && ((ColorDrawable)imageView.getBackground()).getColor() == color)) {

                        // si on clique sur une mauvaise pastille (mais si on clique sur du blanc ou du noir, ne fait rien)v
                        if(bmpColors.getPixel((int) touchX, (int) touchY)!=0)
                            // son quand on se trompe
                            mpWrongSound.start();

                        return true;
                    }
                    isColoring = true;

                    // mode hard : dit la couleur si c'est la bonne
                    if(mode.equals(Mode.HARD))
                        speech.speek(speech.colorToSpeech(currPaint.getTag().toString()));

                    Point p = new Point((int) touchX, (int) touchY);

                    new FloodFill(FillColorActivity.this, bmp, p, color).execute();

                    if (mode.equals(Mode.HARD)) {
                        nbCircles--;
                        if (nbCircles == 0) {
                            winHardMode();
                        }
                    }
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

            // la synthèse vocale dit les couleurs ! =D
            if(mode.equals(Mode.EASY)) {
                speech.speek(speech.colorToSpeech(view.getTag().toString()));
            }

            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));

            // highlight the new selection
            currPaint = (ImageButton) imgView;
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
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
                        Intent homeIntent = new Intent(FillColorActivity.this, LevelSelectorActivity.class);
                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        FillColorActivity.this.startActivity(homeIntent);
                    }
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    private void winHardMode() {

        mpApplauseSound.start();
        //speech.speek("Bravo !");
        new AlertDialog.Builder(this).setMessage("Bravo !").show();

    }

    public FillColorView getImageView() {
        return imageView;
    }

    public boolean isColoring() {
        return isColoring;
    }

    public void setIsColoring(boolean b) {
        isColoring = b;
    }
}
