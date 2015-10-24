package fr.polytech.si5.arc_en_ciel;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DrawingSelectorActivity extends Activity {
    public static Map<Category, List<Integer>> imageIdsByCategory;
    private Mode mode;
    private Category category;
    private FillDrawingsGridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_selector);

        List<Integer> animals = new ArrayList<>();
        animals.add(R.drawable.tortue);
        animals.add(R.drawable.poisson);
        animals.add(R.drawable.girafe);

        imageIdsByCategory = new HashMap<>();
        imageIdsByCategory.put(Category.ANIMALS, animals);

        mode = (Mode)getIntent().getSerializableExtra("mode");
        category = (Category)getIntent().getSerializableExtra("category");


        List<Integer> array = imageIdsByCategory.get(category);

        ((TextView)findViewById(R.id.header_text)).setText("Choisis un dessin !");

        GridView gridView = (GridView) findViewById(R.id.grid_view);
        adapter = new FillDrawingsGridAdapter(this, array, mode);
        gridView.setAdapter(adapter);

        ImageView imageview;
    }
}
