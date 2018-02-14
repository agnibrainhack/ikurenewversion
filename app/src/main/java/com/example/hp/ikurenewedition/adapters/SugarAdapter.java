package com.example.hp.ikurenewedition.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hp.ikurenewedition.R;
import com.example.hp.ikurenewedition.dataclass.Data_class_four;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 02-02-2018.
 */

public class SugarAdapter extends ArrayAdapter<Data_class_four> {

    private List<Data_class_four> noteList;
    private Context context;

    public SugarAdapter(Context context, ArrayList<Data_class_four> noteList) {
        super(context, R.layout.each_sugar, noteList);
        this.noteList = noteList;
        this.context = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.each_sugar, null);
        }

        final Data_class_four note = noteList.get(position);

        if (note != null) {
            TextView fasting = (TextView) v.findViewById(R.id.field_fasting);
            TextView pp = (TextView) v.findViewById(R.id.field_pp);
            TextView time = (TextView) v.findViewById(R.id.field_date);
            TextView random = (TextView) v.findViewById(R.id.field_random);

            if (fasting != null) {
                fasting.setText(note.getfasting());

            }
            if (pp != null) {
                pp.setText(note.getpp());
            }
            if (time != null) {
                time.setText(note.gettime());
            }
            if(random!= null){
                random.setText(note.getrandom());
            }



        }

        return v;
    }
}