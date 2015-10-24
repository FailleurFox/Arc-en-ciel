package fr.polytech.si5.arc_en_ciel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.UUID;

public class DrawingActivity extends Activity {
    private DrawingView drawingView;
    private ImageButton currPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        LinearLayout paintLayout = (LinearLayout)findViewById(R.id.linLay);
        currPaint = (ImageButton)paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

        drawingView = (DrawingView)findViewById(R.id.drawing);

        drawingView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float touchX = motionEvent.getX();
                float touchY = motionEvent.getY();

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        drawingView.getDrawPath().moveTo(touchX, touchY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        drawingView.getDrawPath().lineTo(touchX, touchY);
                        break;
                    case MotionEvent.ACTION_UP:
                        drawingView.getDrawCanvas().drawPath(drawingView.getDrawPath(), drawingView.getDrawPaint());
                        drawingView.getDrawPath().reset();
                        break;
                    default:
                        return false;
                }
                drawingView.invalidate();
                return true;
            }
        });

    }

    public void paintClicked(View view){
        // use chosen color
        if(view != currPaint){
            ImageButton imgView = (ImageButton)view;
            String color = view.getTag().toString();
            drawingView.setColor(color);

            // clean the previous selection
            //if(currPaint.getId() != (R.id.erase_btn)) {
                currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            //}
            //else { // special case : eraser
            //    currPaint.setBackgroundColor(getResources().getColor(R.color.myGrey));
           // }

            // highlight the new selection
                currPaint = (ImageButton)imgView;
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
        drawingView.setDrawingCacheEnabled(true);

        // save
        String imgSaved = MediaStore.Images.Media.insertImage(
                getContentResolver(), drawingView.getDrawingCache(),
                UUID.randomUUID().toString()+".png", "drawing");

        // verify
        if(imgSaved!=null){
            Toast savedToast = Toast.makeText(getApplicationContext(),
                    "Image sauvegardée dans la galerie !", Toast.LENGTH_LONG);
            savedToast.show();
        }
        else{
            Toast unsavedToast = Toast.makeText(getApplicationContext(),
                    "Oups! L'image n'a pas été sauvegardée...", Toast.LENGTH_LONG);
            unsavedToast.show();
        }

        // desactive cache
        drawingView.destroyDrawingCache();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Retour")
            .setMessage("Etes-vous sûr de vouloir quitter le dessin ?")
            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            })
            .setNegativeButton("Annuler", null)
            .show();
    }
}
