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

import com.example.hp.ikurenewedition.DisplayEcgActivity;
import com.example.hp.ikurenewedition.R;
import com.example.hp.ikurenewedition.adapters.EcgAdapter;
import com.example.hp.ikurenewedition.dataclass.Data_class_three;
import com.example.hp.ikurenewedition.pojodatamodels.EcgListDetail;
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

public class ECGFragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener {
    View rootView;
    ProgressDialog progressDialog;
    ArrayList<Data_class_three> dy = new ArrayList<Data_class_three>();
    EcgAdapter ecgAdapter;
    ListView EcgListView;
    SwipeRefreshLayout swipeRefreshLayout;
    private String pid;
    private boolean start;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_ecg, container, false);
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
        //Toast.makeText(getActivity(), "No ECG record Found \nIf You have taken any test then wait for 24hrs", Toast.LENGTH_SHORT).show();
        //Intent i=new Intent(NetworkActivity.this,MainActivity.class);
        //getActivity().finish();
        //startActivity(i);
    }

    private void callAPI1() {
        if (!start)
            progressDialog.show();
        else
            swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<EcgListDetail> call = apiService.getDetails5(pid);
        call.enqueue(new Callback<EcgListDetail>() {
            @Override
            public void onResponse(Call<EcgListDetail> call, final Response<EcgListDetail> result) {
                if (!start)
                    progressDialog.dismiss();
                else
                    swipeRefreshLayout.setRefreshing(false);
                if(result.body().getError()){
                    bullshit();
                }
                else {
                    if (result.body().getEcglist().size() == 0) {
                        bullshit();
                    }
                    if (result != null) {
                        if (result.body().getEcglist().size() > 0) {
                            for (int i = 0; i < result.body().getEcglist().size(); i++) {
                                dy.add(i, new Data_class_three(result.body().getEcglist().get(i).getPid(),
                                        result.body().getEcglist().get(i).getId(),
                                        convert(result.body().getEcglist().get(i).getTimestamp())));
                            }
                            ecgAdapter = new EcgAdapter(getContext(), dy);


                            try {
                                EcgListView.setAdapter(ecgAdapter);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (result.body().getEcglist().size() != 0) {
                        EcgListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //Toast.makeText(List_display.this,"Hello",Toast.LENGTH_SHORT).show();
                                String url = result.body().getEcglist().get(position).getId();
                                Intent k = new Intent(getActivity(), DisplayEcgActivity.class);
                                //String str = Integer.toString(position);
                                k.putExtra("id", url);
                                //k.putExtra("card_no",patient);
                                startActivity(k);
                                getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                                // pass the intent here
                            }
                        });
                    }
                }

            }

            @Override
            public void onFailure(Call<EcgListDetail> call, Throwable t) {
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

