package fr.polytech.si5.arc_en_ciel;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class DrawingSelectorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_selector);

        ImageButton backButton = (ImageButton) findViewById(R.id.back_button);
        backButton.setOnClickListener(new OnBackButtonClickListener(this));
        backButton.setVisibility(View.VISIBLE);

        ImageButton homeButton = (ImageButton) findViewById(R.id.home_button);
        homeButton.setOnClickListener(new OnHomeButtonClickListener(this));
        homeButton.setVisibility(View.VISIBLE);

        Mode mode = (Mode)getIntent().getSerializableExtra("mode");
        Category category = (Category)getIntent().getSerializableExtra("category");

        List<Drawing> drawings = new ArrayList<>();
        switch(category){
            case ANIMALS:
                drawings.add(new Drawing("La tortue", R.drawable.tortue, R.drawable.tortue_colors, R.drawable.tortue_thumbnail, 11));
                drawings.add(new Drawing("Le poisson", R.drawable.poisson, R.drawable.poisson_colors, R.drawable.poisson_thumbnail, 16));
                drawings.add(new Drawing("La girafe", R.drawable.girafe, R.drawable.girafe_colors, R.drawable.girafe_thumbnail, 8));
                break;
            case GARDEN:
                drawings.add(new Drawing("Les fleurs", R.drawable.fleurs, R.drawable.fleurs_colors, R.drawable.fleurs_thumbnail, 22));
                drawings.add(new Drawing("Le potager", R.drawable.potager, R.drawable.potager_colors, R.drawable.potager_thumbnail, 7));
                drawings.add(new Drawing("L'arrosoire", R.drawable.arrosoire, R.drawable.arrosoire_colors, R.drawable.arrosoire_thumbnail, 11));
                break;
            case SEASONS:
                drawings.add(new Drawing("Le printemps", R.drawable.printemps, R.drawable.printemps_colors, R.drawable.printemps_thumbnail, 13));
                drawings.add(new Drawing("L'été", R.drawable.ete, R.drawable.ete_colors, R.drawable.ete_thumbnail, 8));
                drawings.add(new Drawing("L'automne", R.drawable.automne, R.drawable.automne_colors, R.drawable.automne_thumbnail, 7));
                drawings.add(new Drawing("L'hiver", R.drawable.hiver, R.drawable.hiver_colors, R.drawable.hiver_thumbnail, 8));
                break;
        }

        ((TextView)findViewById(R.id.header_text)).setText("Choisis un dessin !");

        GridView gridView = (GridView) findViewById(R.id.grid_view);
        FillDrawingsGridAdapter adapter = new FillDrawingsGridAdapter(this, drawings, mode);
        gridView.setAdapter(adapter);
    }
}
