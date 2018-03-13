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

import com.example.hp.ikurenewedition.R;
import com.example.hp.ikurenewedition.VitalsDetailsActivity;
import com.example.hp.ikurenewedition.adapters.EcgAdapter;
import com.example.hp.ikurenewedition.dataclass.Data_class_three;
import com.example.hp.ikurenewedition.pojodatamodels.VitalTime;
import com.example.hp.ikurenewedition.rest.ApiClient;
import com.example.hp.ikurenewedition.rest.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 15/2/18.
 */

public class VitalsFragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener {

    String pid;
    ArrayList<Data_class_three> dy = new ArrayList<Data_class_three>();
    View rootView;
    SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog progressDialog;
    private EcgAdapter ecgAdapter;
    private ListView EcgListView;
    private boolean start;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_vitals, container, false);

        pid = getActivity().getIntent().getStringExtra("patient");
        EcgListView = (ListView) rootView.findViewById(R.id.list_of_ecg);
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
        EcgListView.setAdapter(null);
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
        //Toast.makeText(getActivity(), "No Vitals record Found \nIf You have taken any test then wait for 24hrs", Toast.LENGTH_SHORT).show();
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
        Call<VitalTime> call = apiService.getDetails7(pid);
        call.enqueue(new Callback<VitalTime>() {
            @Override
            public void onResponse(Call<VitalTime> call, final Response<VitalTime> result) {
                if (!start)
                    progressDialog.dismiss();
                else
                    swipeRefreshLayout.setRefreshing(false);
                if(result.body().getError()){
                    bullshit();
                }
                else {
                    if (result.body().getVitallist().size() == 0) {
                        bullshit();
                    }
                    if (result != null) {
                        if (result.body().getVitallist().size() > 0) {
                            for (int i = 0; i < result.body().getVitallist().size(); i++) {
                                dy.add(i, new Data_class_three(result.body().getVitallist().get(i).getPid(),
                                        " ",
                                        convert(result.body().getVitallist().get(i).getTimestamp())));
                            }
                            ecgAdapter = new EcgAdapter(getContext(), dy);


                            try {
                                EcgListView.setAdapter(ecgAdapter);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (result.body().getVitallist().size() != 0) {
                        EcgListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //Toast.makeText(List_display.this,"Hello",Toast.LENGTH_SHORT).show();
                                String url = result.body().getVitallist().get(position).getTimestamp();
                                Intent k = new Intent(getActivity(), VitalsDetailsActivity.class);
                                //String str = Integer.toString(position);
                                k.putExtra("pid", pid);
                                k.putExtra("timestamp", url);
                                startActivity(k);
                                getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                                // pass the intent here
                            }
                        });
                    }
                }

            }

            @Override
            public void onFailure(Call<VitalTime> call, Throwable t) {
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
        tim = tim *1000;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        return formatter.format(tim);

        //return date;
    }


}
