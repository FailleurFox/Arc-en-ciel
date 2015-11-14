package fr.polytech.si5.arc_en_ciel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * Creates the items in the feed lists
 */
public class FillDrawingsGridAdapter extends ArrayAdapter<Drawing> {
    private Mode mode;

    public FillDrawingsGridAdapter(Context context, List<Drawing> drawings, Mode mode) {
        super(context, 0, drawings);
        this.mode = mode;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Drawing drawing = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.drawing_component, parent, false);
        }

        if (convertView != null) {
            ((ImageView)convertView.findViewById(R.id.drawing_thumbnail)).setImageResource(drawing.getThumbnailId());
            ((TextView)convertView.findViewById(R.id.drawing_name)).setText(drawing.getName());
        }
        convertView.findViewById(R.id.drawing_thumbnail).setOnClickListener(
                new OnDrawingClickListener(getContext(), drawing, mode));

        return convertView;
    }
}
