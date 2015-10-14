package fr.polytech.si5.arc_en_ciel;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class LevelSelectorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector);

        ((TextView)findViewById(R.id.header_text)).setText("Arc-en-ciel");
    }

    public void selectEasyColoring(View v){
        Intent intent = new Intent(this, CategorySelectorActivity.class);
        intent.putExtra("mode", Mode.EASY);
        startActivity(intent);
    }

    public void selectHardColoring(View v){
        Intent intent = new Intent(this, CategorySelectorActivity.class);
        intent.putExtra("mode", Mode.HARD);
        startActivity(intent);
    }

    public void selectEmptyPage(View v){
        Intent intent = new Intent(this, DrawingActivity.class);
        startActivity(intent);
    }
}
