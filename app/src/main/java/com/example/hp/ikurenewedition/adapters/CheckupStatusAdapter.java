package com.example.hp.ikurenewedition.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hp.ikurenewedition.R;

import com.example.hp.ikurenewedition.dataclass.Data_class_seven;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by hp on 02-02-2018.
 */

public class CheckupStatusAdapter extends ArrayAdapter<Data_class_seven> {

    private List<Data_class_seven> noteList;
    private Context context;

    public CheckupStatusAdapter(Context context, ArrayList<Data_class_seven> noteList) {
        super(context, R.layout.each_checkdetail, noteList);
        this.noteList = noteList;
        this.context = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.each_checkdetail, null);
        }

        final Data_class_seven note = noteList.get(position);

        if (note != null) {
            TextView service = (TextView) v.findViewById(R.id.service);
            TextView status = (TextView) v.findViewById(R.id.status);

            if (service != null) {
                service.setText(note.getCheckuptype());

            }
            if (status != null) {
                status.setText(note.getStatus());
                if (Objects.equals(note.getStatus(), "Done")) {
                    status.setTextColor(Color.GREEN);
                } else
                    status.setTextColor(Color.RED);
            }


        }

        return v;
    }
}