package com.example.hp.ikurenewedition.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hp.ikurenewedition.R;
import com.example.hp.ikurenewedition.dataclass.Data_class_three;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by hp on 11-01-2018.
 */

public class EcgAdapter extends ArrayAdapter<Data_class_three> {

    private List<Data_class_three> noteList;
    private Context context;

    public EcgAdapter(Context context, ArrayList<Data_class_three> noteList) {
        super(context, R.layout.each_ecg, noteList);
        this.noteList = noteList;
        this.context = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.each_ecg, null);
        }

        final Data_class_three note = noteList.get(position);

        if (note != null) {
            // TextView title = (TextView) v.findViewById(R.id.txt1);
            TextView description = (TextView) v.findViewById(R.id.txt2);
            TextView day = (TextView) v.findViewById(R.id.day_of_vital);
            //TextView time = (TextView) v.findViewById(R.id.txt3);

           /* if (title != null) {
                title.setText(note.getTitle());

            }*/
            if (description != null) {
                description.setText(note.gettime());
            }
            if (day != null) {
                String disp = note.gettime().substring(0, 3);
                day.setText(disp);
                if (Objects.equals(disp, "Sun"))
                    day.setBackgroundResource(R.drawable.circ_red);
                if (Objects.equals(disp, "Mon"))
                    day.setBackgroundResource(R.drawable.circ_lightyellow);
                if (Objects.equals(disp, "Tue"))
                    day.setBackgroundResource(R.drawable.circ_red);
                if (Objects.equals(disp, "Wed"))
                    day.setBackgroundResource(R.drawable.circ_res);
                if (Objects.equals(disp, "Thu"))
                    day.setBackgroundResource(R.drawable.color_darkyellow);
                if (Objects.equals(disp, "Fri"))
                    day.setBackgroundResource(R.drawable.circ_lightblue);
                if (Objects.equals(disp, "Sat"))
                    day.setBackgroundResource(R.drawable.circ_darkblue);

            }

        }

        return v;
    }
}