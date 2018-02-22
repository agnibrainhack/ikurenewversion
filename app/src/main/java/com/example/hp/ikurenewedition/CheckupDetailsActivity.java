package com.example.hp.ikurenewedition;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.ikurenewedition.adapters.CheckupStatusAdapter;
import com.example.hp.ikurenewedition.adapters.SugarAdapter;
import com.example.hp.ikurenewedition.dataclass.Data_class_four;
import com.example.hp.ikurenewedition.dataclass.Data_class_seven;
import com.example.hp.ikurenewedition.dataclass.Data_class_six;
import com.example.hp.ikurenewedition.pojodatamodels.CheckupStatus;
import com.example.hp.ikurenewedition.pojodatamodels.SugarDetail;
import com.example.hp.ikurenewedition.rest.ApiClient;
import com.example.hp.ikurenewedition.rest.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hp on 22-02-2018.
 */

public class CheckupDetailsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    private Intent i;
    private String id;
    private String time;
    private ProgressDialog progressDialog;
    private boolean start;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Data_class_seven> dy = new ArrayList<>();
    private ListView checkupListView;
    private CheckupStatusAdapter checkupstatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkupdetails);
        i = getIntent();
        id = i.getStringExtra("id");
        time = i.getStringExtra("time");
        //Toast.makeText(CheckupDetailsActivity.this, id + time,Toast.LENGTH_LONG).show();
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimaryDark,
                R.color.colorred,
                R.color.colorAccent);
        init();
    }


    @Override
    public void onRefresh() {
        start = true;
        dy.clear();
        checkupListView.setAdapter(null);
        callAPI1();

    }

    private void init() {
        //retrofitRepository=new RetrofitRepository();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...." + '\n' + "We are figuring things out");
        progressDialog.setCancelable(false);
        callAPI1();
    }

    public void bullshit() {
        Toast.makeText(this, "No record Found \nIf You have taken any test then wait for 24hrs", Toast.LENGTH_LONG).show();
        //Intent i=new Intent(NetworkActivity.this,MainActivity.class);
        //finish();
        //startActivity(i);
    }


    private void callAPI1() {
        if (!start)
            progressDialog.show();
        else
            swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CheckupStatus> call = apiService.getDetails12(id, time);
        call.enqueue(new Callback<CheckupStatus>() {
            @Override
            public void onResponse(Call<CheckupStatus> call, final Response<CheckupStatus> result) {
                if (!start)
                    progressDialog.dismiss();
                else
                    swipeRefreshLayout.setRefreshing(false);
                if (result.body().getError()) {
                    bullshit();
                } else {
                    if (result.body().getVitalrequestdetails().size() == 0) {
                        bullshit();
                    }
                    if (result != null) {
                        if (result.body().getVitalrequestdetails().size() > 0) {
                            for (int i = 0; i < result.body().getVitalrequestdetails().size(); i++) {
                                dy.add(i, new Data_class_seven(result.body().getVitalrequestdetails().get(i).getType(),
                                        result.body().getVitalrequestdetails().get(i).getStatus()
                                ));


                            }
                            //obj = new PassingThrough(diab_fasting, diab_fasting_date, diab_pp, diab_pp_date, diab_random, diab_random_date);
                            checkupstatAdapter = new CheckupStatusAdapter(getBaseContext(), dy);

                            checkupListView = (ListView) findViewById(R.id.list_of_checkupstats);
                            try {
                                checkupListView.setAdapter(checkupstatAdapter);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (result.body().getVitalrequestdetails().size() != 0) {
                        checkupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
            public void onFailure(Call<CheckupStatus> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                else
                    swipeRefreshLayout.setRefreshing(false);
                bullshit();
            }
        });

    }

    private String convert(String time) {
        long tim = Long.parseLong(time);
        tim = tim * 1000;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("d MMM yyyy" + "\n" + "HH:mm:ss");
        return formatter.format(tim);

        //return date;
    }
}
