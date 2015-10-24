package fr.polytech.si5.arc_en_ciel;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class CategorySelectorActivity extends Activity {
    private Mode mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selector);

        mode = (Mode)getIntent().getSerializableExtra("mode");

        ImageButton imageAnimals = (ImageButton) findViewById(R.id.image_animals);
        ImageButton imageGarden = (ImageButton) findViewById(R.id.image_garden);
        ImageButton imageSeasons = (ImageButton) findViewById(R.id.image_seasons);

        imageAnimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAnimals(v);
            }
        });

        imageGarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGarden(v);
            }
        });

        imageSeasons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSeasons(v);
            }
        });

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
