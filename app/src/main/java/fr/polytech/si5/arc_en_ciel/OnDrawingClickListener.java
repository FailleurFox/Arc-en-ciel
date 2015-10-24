package fr.polytech.si5.arc_en_ciel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by Hugo on 24/10/2015.
 */
public class OnDrawingClickListener implements View.OnClickListener{
    private Integer imageId;
    private Mode mode;
    private Context context;

    public OnDrawingClickListener(Context context, int imageId, Mode mode){
        this.imageId = imageId;
        this.context = context;
        this.mode = mode;
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(context, FillColorActivity.class);
        intent.putExtra("imageId", imageId);
        intent.putExtra("mode", mode);
        context.startActivity(intent);
    }
}
