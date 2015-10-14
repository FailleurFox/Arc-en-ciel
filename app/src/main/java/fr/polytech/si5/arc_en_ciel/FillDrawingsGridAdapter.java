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
public class FillDrawingsGridAdapter extends ArrayAdapter<String> {
    private Map<String, Integer> map;

    public FillDrawingsGridAdapter(Context context, List<String> imageName) {
        super(context, 0, imageName);
        map = new HashMap<String, Integer>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String imageName = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.drawing_component, parent, false);
        }

        if (convertView != null) {
            ((ImageView)convertView.findViewById(R.id.drawing_thumbnail)).setImageResource(map.get(imageName));
            ((TextView)convertView.findViewById(R.id.drawing_name)).setText(imageName);
        }

        return convertView;
    }
}
