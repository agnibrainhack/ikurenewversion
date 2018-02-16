package com.example.hp.ikurenewedition.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hp.ikurenewedition.R;
import com.example.hp.ikurenewedition.dataclass.Data_class_five;
import com.example.hp.ikurenewedition.dataclass.Data_class_six;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 15-02-2018.
 */

public class CheckupAdapter extends ArrayAdapter<Data_class_six> {

    private List<Data_class_six> noteList;
    private Context context;

    public CheckupAdapter(Context context, ArrayList<Data_class_six> noteList) {
        super(context, R.layout.each_checkup, noteList);
        this.noteList = noteList;
        this.context = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.each_checkup, null);
        }

        final Data_class_six note = noteList.get(position);

        if (note != null) {
            TextView date = (TextView) v.findViewById(R.id.txt2);


            if (date != null) {
                date.setText(note.getDate());

            }



        }

        return v;
    }
}