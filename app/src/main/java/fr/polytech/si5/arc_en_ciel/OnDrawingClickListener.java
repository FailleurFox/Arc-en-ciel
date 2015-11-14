package fr.polytech.si5.arc_en_ciel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by Hugo on 24/10/2015.
 */
public class OnDrawingClickListener implements View.OnClickListener{
    private Drawing drawing;
    private Mode mode;
    private Context context;
    private SpeechSynthesis speech;


    public OnDrawingClickListener(Context context, Drawing drawing, Mode mode){
        this.drawing = drawing;
        this.context = context;
        this.mode = mode;

        // thread pour synthèse vocale
        speech = new SpeechSynthesis() {
            protected void onPostExecute(Boolean result) {
            }
        };
        speech.context = context;
        speech.execute();
    }

    @Override
    public void onClick(View v) {
        // dit le nom du dessin
        speech.speek(drawing.getName());

        Intent intent = new Intent(context, FillColorActivity.class);
        intent.putExtra("imageId", drawing.getDrawableId());
        intent.putExtra("nbCircles", drawing.getNbCircles());
        intent.putExtra("mode", mode);
        if(mode.equals(Mode.HARD)){
            intent.putExtra("colorsId", drawing.getColorsDrawableId());
        }
        context.startActivity(intent);
    }
}
