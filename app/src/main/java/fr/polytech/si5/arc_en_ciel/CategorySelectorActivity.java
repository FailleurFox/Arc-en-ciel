package fr.polytech.si5.arc_en_ciel;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class CategorySelectorActivity extends Activity {
    private Mode mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selector);

        mode = (Mode)getIntent().getSerializableExtra("mode");

        ((TextView)findViewById(R.id.header_text)).setText("Choisis un thème !");
    }

    public void selectSeasons(View v){
        Intent intent = new Intent(this, DrawingSelectorActivity.class);
        intent.putExtra("mode", mode);
        intent.putExtra("category", Category.SEASONS);
        startActivity(intent);
    }

    public void selectAnimals(View v){
        Intent intent = new Intent(this, DrawingSelectorActivity.class);
        intent.putExtra("mode", mode);
        intent.putExtra("category", Category.ANIMALS);
        startActivity(intent);
    }

    public void selectGarden(View v){
        Intent intent = new Intent(this, DrawingSelectorActivity.class);
        intent.putExtra("mode", mode);
        intent.putExtra("category", Category.GARDEN);
        startActivity(intent);
    }
}
