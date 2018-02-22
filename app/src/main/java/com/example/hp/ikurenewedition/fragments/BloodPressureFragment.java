package com.example.hp.ikurenewedition.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.ikurenewedition.Blood_Pressure_Graph;
import com.example.hp.ikurenewedition.R;
import com.example.hp.ikurenewedition.adapters.BpAdapter;
import com.example.hp.ikurenewedition.dataclass.Data_class_five;
import com.example.hp.ikurenewedition.pojodatamodels.BPDetails;
import com.example.hp.ikurenewedition.rest.ApiClient;
import com.example.hp.ikurenewedition.rest.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 15/2/18.
 */

public class BloodPressureFragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener {
    View rootView;
    int k1;
    SwipeRefreshLayout swipeRefreshLayout;
    private String pid;
    private ProgressDialog progressDialog;
    private ArrayList<Data_class_five> dy = new ArrayList<>();
    private BpAdapter bpAdapter;
    private ListView BpListView;
    private ArrayList<String> systolic = new ArrayList<>();
    private ArrayList<String> diastolic = new ArrayList<>();
    private ArrayList<String> timestamp = new ArrayList<>();
    private View floatingactionbutton;
    private boolean start;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.activity_blood_pressure, container, false);
        k1 = 0;
        pid = getActivity().getIntent().getStringExtra("patient");
        floatingactionbutton = rootView.findViewById(R.id.pressure_graph);
        floatingactionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(getActivity(), Blood_Pressure_Graph.class);

                k.putExtra("systolic", systolic);
                k.putExtra("diastolic", diastolic);
                k.putExtra("timestamp", timestamp);
                k.putExtra("pid", pid);
                startActivity(k);
            }
        });
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimaryDark,
                R.color.colorred,
                R.color.colorAccent);

        init();
         return rootView;
    }

    @Override
    public void onRefresh() {
        //fetchMovies();
        start = true;
        dy.clear();
        BpListView.setAdapter(null);
        callAPI1();

    }

    private void init() {
        //retrofitRepository=new RetrofitRepository();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait...." + '\n' + "We are figuring things out");
        progressDialog.setCancelable(false);
        callAPI1();
    }

    public void bullshit() {
        Toast.makeText(getActivity(), "No record Found \nIf You have taken any test then wait for 24hrs", Toast.LENGTH_LONG).show();
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
        Call<BPDetails> call = apiService.getDetails10(pid);
        call.enqueue(new Callback<BPDetails>() {
            @Override
            public void onResponse(Call<BPDetails> call, final Response<BPDetails> result) {
                if (!start)
                    progressDialog.dismiss();
                else
                    swipeRefreshLayout.setRefreshing(false);
                if (result.body().getError()) {
                    bullshit();
                } else {
                    if (result.body().getBplist().size() == 0) {
                        bullshit();
                    }
                    if (result != null) {
                        if (result.body().getBplist().size() > 0) {
                            for (int i = 0; i < result.body().getBplist().size(); i++) {
                                dy.add(i, new Data_class_five(convert1(result.body().getBplist().get(i).getTimestamp()),
                                        convert(result.body().getBplist().get(i).getTimestamp()),
                                        result.body().getBplist().get(i).getSys(),
                                        result.body().getBplist().get(i).getDia()));

                                if (!Objects.equals(result.body().getBplist().get(i).getSys(), null) &&
                                        !Objects.equals(result.body().getBplist().get(i).getDia(), null)) {
                                    systolic.add(k1, result.body().getBplist().get(i).getSys());
                                    diastolic.add(k1, result.body().getBplist().get(i).getDia());
                                    timestamp.add(k1, result.body().getBplist().get(i).getTimestamp());
                                    k1++;
                                }


                            }
                            //obj = new PassingThrough(diab_fasting, diab_fasting_date, diab_pp, diab_pp_date, diab_random, diab_random_date);
                            bpAdapter = new BpAdapter(getContext(), dy);

                            BpListView = (ListView) rootView.findViewById(R.id.list_of_pressure);
                            try {
                                BpListView.setAdapter(bpAdapter);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (result.body().getBplist().size() != 0) {
                        BpListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
            public void onFailure(Call<BPDetails> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                else
                    swipeRefreshLayout.setRefreshing(false);
                bullshit();
            }
        });

    }

    private String convert1(String time) {
        long tim = Long.parseLong(time);
        tim = tim * 1000;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("d MMM yyyy");
        return formatter.format(tim);

        //return date;
    }

    private String convert(String time) {
        long tim = Long.parseLong(time);
        tim = tim * 1000;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(tim);

        //return date;
    }
}
