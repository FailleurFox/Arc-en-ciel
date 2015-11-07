package fr.polytech.si5.arc_en_ciel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by Hugo on 07/11/2015.
 */
public class OnBackButtonClickListener implements View.OnClickListener {
    private Activity context;

    public OnBackButtonClickListener(Activity context){
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        context.finish();
    }
}
