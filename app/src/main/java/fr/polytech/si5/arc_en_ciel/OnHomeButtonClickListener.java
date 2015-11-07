package fr.polytech.si5.arc_en_ciel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by Hugo on 07/11/2015.
 */
public class OnHomeButtonClickListener implements View.OnClickListener {
    private Context context;

    public OnHomeButtonClickListener(Context context){
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        Intent homeIntent = new Intent(context, LevelSelectorActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(homeIntent);
    }
}
