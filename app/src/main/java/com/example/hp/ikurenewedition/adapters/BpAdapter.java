package com.example.hp.ikurenewedition.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hp.ikurenewedition.R;
import com.example.hp.ikurenewedition.dataclass.Data_class_five;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 10-02-2018.
 */

public class BpAdapter extends ArrayAdapter<Data_class_five> {

    private List<Data_class_five> noteList;
    private Context context;

    public BpAdapter(Context context, ArrayList<Data_class_five> noteList) {
        super(context, R.layout.each_blood_pressure, noteList);
        this.noteList = noteList;
        this.context = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.each_blood_pressure, null);
        }

        final Data_class_five note = noteList.get(position);

        if (note != null) {
            TextView date = (TextView) v.findViewById(R.id.date);
            TextView time = (TextView) v.findViewById(R.id.time);
            TextView systolic = (TextView) v.findViewById(R.id.systolic);
            TextView diastolic = (TextView) v.findViewById(R.id.diastolic);

            if (date != null) {
                date.setText(note.getDate());

            }
            if (time != null) {
                time.setText(note.gettime());
            }
            if (systolic != null) {
                systolic.setText(note.getSystolic());
            }
            if (diastolic != null) {
                diastolic.setText(note.getDiastolic());
            }


        }

        return v;
    }
}