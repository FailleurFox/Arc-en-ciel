package fr.polytech.si5.arc_en_ciel;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class DrawingSelectorActivity extends Activity {
    private Mode mode;
    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_selector);

        mode = (Mode)getIntent().getSerializableExtra("mode");
        category = (Category)getIntent().getSerializableExtra("category");

        List<String> array = new ArrayList<String>();
        switch(category){
            case ANIMALS:
                break;
            case SEASONS :
                break;
            case GARDEN :
                break;
        }

        ((TextView)findViewById(R.id.header_text)).setText("Choisis un dessin !");

        GridView gridView = (GridView) findViewById(R.id.grid_view);
        FillDrawingsGridAdapter adapter = new FillDrawingsGridAdapter(this, array);
        gridView.setAdapter(adapter);

        ImageView imageview;
    }
}
