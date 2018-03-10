package com.example.hp.ikurenewedition.adapters;

import android.content.Context;
import android.graphics.Color;
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
import java.util.Objects;

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
            TextView day = (TextView) v.findViewById(R.id.circle_day);
            TextView req = (TextView) v.findViewById(R.id.service_requested);
            TextView status = v.findViewById(R.id.display_status);



            if (date != null) {
                String str = note.getDate().substring(4);
                date.setText(str);

            }
            if (day != null) {
                String disp = note.getDate().substring(0, 3);
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
            if (req != null) {
                req.setText(note.getType());
            }

            if (status != null) {
                String str = note.getStatus();
                status.setText(note.getStatus());
                if (Objects.equals(str, "Pending"))
                    status.setTextColor(Color.RED);
                else
                    status.setTextColor(Color.parseColor("#228B22"));
            }

        }

        return v;
    }
}