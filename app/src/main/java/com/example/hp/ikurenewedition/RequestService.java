package com.example.hp.ikurenewedition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by hp on 22-02-2018.
 */

public class RequestService extends AppCompatActivity {

    private Intent i;
    private String pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);
        i = getIntent();
        pid = i.getStringExtra("pid");
        Toast.makeText(RequestService.this, pid, Toast.LENGTH_LONG).show();
    }
}
