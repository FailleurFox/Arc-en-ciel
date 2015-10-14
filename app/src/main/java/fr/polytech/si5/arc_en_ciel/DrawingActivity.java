package fr.polytech.si5.arc_en_ciel;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

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
        //use chosen color
        if(view!=currPaint){
            ImageButton imgView = (ImageButton)view;
            String color = view.getTag().toString();
            drawingView.setColor(color);
        }

    }


}
