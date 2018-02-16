package com.example.hp.ikurenewedition;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.ikurenewedition.adapters.VitalsAdapter;
import com.example.hp.ikurenewedition.dataclass.Data_class_three;
import com.example.hp.ikurenewedition.pojodatamodels.DifferentVitals;
import com.example.hp.ikurenewedition.rest.ApiClient;
import com.example.hp.ikurenewedition.rest.ApiInterface;


import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hp on 31-01-2018.
 */

public class VitalsDetailsActivity extends AppCompatActivity {


    private String timestampquery;
    private String pid;
    private ProgressDialog progressDialog;
    private ArrayList<Data_class_three> dy = new ArrayList<Data_class_three>();
    private VitalsAdapter vitalAdapter;
    private ListView vitalListView;
    FloatingActionButton floatingActionButton;
    private String weight;
    private String height;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital_details);
        Intent i = getIntent();
        pid = i.getStringExtra("pid");
        timestampquery = i.getStringExtra("timestamp");
        //Toast.makeText(VitalsDetailsActivity.this, pid + " " + timestampquery, Toast.LENGTH_LONG).show();
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floating);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(height != null && weight!= null) {
                    Intent k = new Intent(VitalsDetailsActivity.this, BMICALC.class);
                    k.putExtra("height", height);
                    k.putExtra("weight", weight);
                    startActivity(k);
                }
                else
                    Toast.makeText(VitalsDetailsActivity.this, "Sorry We don't have your Height & Weight Details", Toast.LENGTH_LONG).show();

            }
        });
        init();
    }

    private void init() {
        //retrofitRepository=new RetrofitRepository();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...." + '\n' + "We are figuring things out");
        progressDialog.setCancelable(false);
        callAPI1();
    }

    public void bullshit() {
        Toast.makeText(VitalsDetailsActivity.this, "No record Found \nIf You have taken any test then wait for 24hrs", Toast.LENGTH_LONG).show();
        //Intent i=new Intent(NetworkActivity.this,MainActivity.class);
        finish();
        //startActivity(i);
    }

    private void callAPI1() {
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DifferentVitals> call = apiService.getDetails8(pid, timestampquery);
        call.enqueue(new Callback<DifferentVitals>() {
            @Override
            public void onResponse(Call<DifferentVitals> call, final Response<DifferentVitals> result) {
                progressDialog.dismiss();
                if(result.body().getError()){
                    bullshit();
                }
                else {
                    if (result.body().getVitaldetails().size() == 0) {
                        bullshit();
                    }
                    if (result != null) {
                        if (result.body().getVitaldetails().size() > 0) {
                            for (int i = 0; i < result.body().getVitaldetails().size(); i++) {
                                dy.add(i, new Data_class_three(result.body().getVitaldetails().get(i).getType(),
                                        " ",
                                        result.body().getVitaldetails().get(i).getValue()));
                                try{
                                    if(Objects.equals(result.body().getVitaldetails().get(i).getType(), "HEIGHT"))
                                        height = result.body().getVitaldetails().get(i).getValue();
                                    if(Objects.equals(result.body().getVitaldetails().get(i).getType(), "WEIGHT"))
                                        weight = result.body().getVitaldetails().get(i).getValue();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                            vitalAdapter = new VitalsAdapter(getBaseContext(), dy);

                            vitalListView = (ListView) findViewById(R.id.list_of_vitals);
                            try {

                                vitalListView.setAdapter(vitalAdapter);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (result.body().getVitaldetails().size() != 0) {
                        vitalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //Toast.makeText(List_display.this,"Hello",Toast.LENGTH_SHORT).show();
                                //String url = result.body().getVitallist().get(position).getTimestamp();
                                //Intent k = new Intent(VitalsActivity.this, VitalsDetailsActivity.class);
                                //String str = Integer.toString(position);
                                //k.putExtra("pid", pid);
                                //k.putExtra("timestamp", url);
                                //startActivity(k);
                                // pass the intent here
                            }
                        });
                    }
                }

            }

            @Override
            public void onFailure(Call<DifferentVitals> call, Throwable t) {
                progressDialog.dismiss();
                bullshit();
            }
        });

    }





}
