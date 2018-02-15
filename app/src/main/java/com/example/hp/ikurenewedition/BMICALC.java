package com.example.hp.ikurenewedition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


/**
 * Created by hp on 01-02-2018.
 */

public class BMICALC extends AppCompatActivity {

    private String weight;
    private String height;
    TextView txt;

    Double ht, wt;
    private double bmi;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        Intent i = getIntent();
        height = i.getStringExtra("height");
        weight = i.getStringExtra("weight");
        txt = findViewById(R.id.bmitext);
        ht = Double.parseDouble(height);
        wt = Double.parseDouble(weight);
        ht = ht / 100;
        bmi = wt / Math.pow(ht , 2);
         float display = (float) (Math.floor(bmi * 100) / 100);
        txt.setText(String.valueOf(display));

        if(display<=18.5)
        txt.setBackgroundResource(R.drawable.circ_red);
        else if(display>18.5 && display<=25)
           txt.setBackgroundResource(R.drawable.circ_res);
        else if(display>25 && display<=30)
            txt.setBackgroundResource(R.drawable.circ_lightyellow);
        else if(display>30 && display<=40)
            txt.setBackgroundResource(R.drawable.color_darkyellow);
        else
            txt.setBackgroundResource(R.drawable.circ_red);


    }
}
