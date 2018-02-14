package com.example.hp.ikurenewedition.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hp.ikurenewedition.R;
import com.example.hp.ikurenewedition.dataclass.Data_class;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nilagnik on 24-09-2017.
 */

public class NotesAdapter extends ArrayAdapter<Data_class> {

    private List<Data_class> noteList;
    private Context context;

    public NotesAdapter(Context context, ArrayList<Data_class> noteList) {
        super(context, R.layout.each_display, noteList);
        this.noteList = noteList;
        this.context = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.each_display, null);
        }

        final Data_class note = noteList.get(position);

        if (note != null) {
            TextView title = (TextView) v.findViewById(R.id.txt1);
            TextView description = (TextView) v.findViewById(R.id.txt2);
            TextView time = (TextView) v.findViewById(R.id.txt3);

            if (title != null) {
                title.setText(note.getTitle());

            }
            if (description != null) {
                description.setText(note.getDescription());
            }
            if (time != null) {
                time.setText(note.gettime());
            }

        }

        return v;
    }
}