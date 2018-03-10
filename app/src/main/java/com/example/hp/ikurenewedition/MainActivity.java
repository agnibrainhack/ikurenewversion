package com.example.hp.ikurenewedition;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.google.zxing.integration.android.IntentIntegrator;

public class MainActivity extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 1500;
    Button buttonScan;
    IntentIntegrator qrScan;
    int times =0;
    Intent i ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        i = getIntent();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                Intent mainIntent = new Intent(MainActivity.this, AfterSplash.class);
                times++;
                startActivity(mainIntent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }


}




