package fr.polytech.si5.arc_en_ciel;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates the items in the feed lists
 */
public class FillDrawingsGridAdapter extends ArrayAdapter<Integer> {
    private static Map<Integer, String> namesFromId;

    public FillDrawingsGridAdapter(Context context, List<Integer> imageIds) {
        super(context, 0, imageIds);
        namesFromId = new HashMap<>();
        namesFromId.put(R.drawable.tortue, "La tortue");
        namesFromId.put(R.drawable.poisson, "Le poisson");
        namesFromId.put(R.drawable.girafe, "La girafe");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Integer imageId = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.drawing_component, parent, false);
        }

        if (convertView != null) {
            ((ImageView)convertView.findViewById(R.id.drawing_thumbnail)).setImageResource(imageId);
            ((TextView)convertView.findViewById(R.id.drawing_name)).setText(namesFromId.get(imageId));
        }

        return convertView;
    }
}
