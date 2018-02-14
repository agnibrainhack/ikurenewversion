package com.example.hp.ikurenewedition.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hp.ikurenewedition.R;
import com.example.hp.ikurenewedition.dataclass.Data_class_two;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 10-01-2018.
 */

public class PresAdapter extends ArrayAdapter<Data_class_two> {

    private List<Data_class_two> noteList;
    private Context context;

    public PresAdapter(Context context, ArrayList<Data_class_two> noteList) {
        super(context, R.layout.each_prescription, noteList);
        this.noteList = noteList;
        this.context = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.each_prescription, null);
        }

        final Data_class_two note = noteList.get(position);

        if (note != null) {
            TextView title = (TextView) v.findViewById(R.id.txt3);
            TextView description = (TextView) v.findViewById(R.id.txt2);
            //TextView time = (TextView) v.findViewById(R.id.txt3);

            if (title != null) {
                title.setText(note.getTitle());

            }
            if (description != null) {
                description.setText(note.gettime());
            }
            /*if (time != null) {
                time.setText(note.gettime());
            }*/

        }

        return v;
    }
}