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
 * Created by hp on 31-01-2018.
 */

public class VitalsAdapter extends ArrayAdapter<Data_class_three> {

    private List<Data_class_three> noteList;
    private Context context;

    public VitalsAdapter(Context context, ArrayList<Data_class_three> noteList) {
        super(context, R.layout.each_vitals_details, noteList);
        this.noteList = noteList;
        this.context = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.each_vitals_details, null);
        }

        final Data_class_three note = noteList.get(position);

        if (note != null) {
             TextView title = (TextView) v.findViewById(R.id.type);
            TextView description = (TextView) v.findViewById(R.id.txt2);
            TextView time = (TextView) v.findViewById(R.id.txt3);

            if (title != null) {
                title.setText(note.getTitle());

            }
            if (description != null) {
                description.setText(note.gettime());
            }
            /*if (time != null) {
                time.setText(note.gettime());
            }*/

            if(Objects.equals(note.getTitle(), "WEIGHT")){
                time.setText("kg");
            }
            if(Objects.equals(note.getTitle(), "HEIGHT")){
                time.setText("cms");
            }
            if(Objects.equals(note.getTitle(), "TEMPERATURE")){
                time.setText((char)176 + "F");
            }
            if(Objects.equals(note.getTitle(), "HEMOGLOBIN")){
                title.setText("HAEMOGLOBIN");
                time.setText("mg/dl");
            }
            if(Objects.equals(note.getTitle(), "PULSE")){
                time.setText("/min");
            }
            if(Objects.equals(note.getTitle(), "RESPIRATORY")){
                time.setText("/min");
            }
            if(Objects.equals(note.getTitle(), "OXYGEN")){
                time.setText("%");
            }

        }

        return v;
    }
}