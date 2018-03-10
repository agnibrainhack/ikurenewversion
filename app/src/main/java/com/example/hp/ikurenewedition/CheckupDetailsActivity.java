package com.example.hp.ikurenewedition;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import com.example.hp.ikurenewedition.pojodatamodels.ConfirmService;
import com.example.hp.ikurenewedition.pojodatamodels.DataUpload;
import com.example.hp.ikurenewedition.pojodatamodels.SendData;
import com.example.hp.ikurenewedition.pojodatamodels.SugarDetail;
import com.example.hp.ikurenewedition.rest.ApiClient;
import com.example.hp.ikurenewedition.rest.ApiInterface;
import com.example.hp.ikurenewedition.rest.ApiInterface1;

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
                            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {
                                final String id_upload = result.body().getVitalrequestdetails().get(position).getId();
                                AlertDialog.Builder adb = new AlertDialog.Builder(
                                        CheckupDetailsActivity.this);
                                adb.setTitle("Service Provided?");
                                adb.setMessage(" Select Yes if the service is provided");   //parent.getItemAtPosition(position)
                                adb.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        uploadToServer(id_upload);
                                    }
                                });
                                adb.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                adb.show();
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


    private void uploadToServer(String id) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...." + '\n' + "We are figuring things out");
        progressDialog.setCancelable(false);
        //cardView1.setVisibility(View.GONE);
        progressDialog.show();
        ConfirmService snd = new ConfirmService();
        snd.setId(id);
        ApiInterface1 apiService = ApiClient.getClient().create(ApiInterface1.class);
        Call<DataUpload> call = apiService.savePost2(snd);
        call.enqueue(new Callback<DataUpload>() {
            @Override
            public void onResponse(Call<DataUpload> call, Response<DataUpload> response) {
                if (response.body().getError()) {
                    progressDialog.dismiss();
                    Toast.makeText(CheckupDetailsActivity.this, "Couldn't be uploaded Try again", Toast.LENGTH_LONG).show();
                } else if (!response.body().getError()) {
                    progressDialog.dismiss();
                    Toast.makeText(CheckupDetailsActivity.this, "Uploaded successfully", Toast.LENGTH_LONG).show();
                    dy.clear();
                    checkupListView.setAdapter(null);
                    init();


                }

            }

            @Override
            public void onFailure(Call<DataUpload> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CheckupDetailsActivity.this, "Image couldn't be uploaded Try again", Toast.LENGTH_LONG).show();

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
