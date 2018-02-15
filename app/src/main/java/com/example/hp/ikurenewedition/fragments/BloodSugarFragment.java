package com.example.hp.ikurenewedition.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hp.ikurenewedition.DiabetesGraphActivity;
import com.example.hp.ikurenewedition.Diabetes_Graph_PP;
import com.example.hp.ikurenewedition.Diabetes_Graph_Random;
import com.example.hp.ikurenewedition.R;
import com.example.hp.ikurenewedition.adapters.SugarAdapter;
import com.example.hp.ikurenewedition.dataclass.Data_class_four;
import com.example.hp.ikurenewedition.earthquakeModel.SugarDetail;
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

public class BloodSugarFragment extends android.support.v4.app.Fragment {
    View rootView;
    String pid;
    RelativeLayout relativeLayout;
    ArrayList<String> diab_fasting = new ArrayList<>(1);
    ArrayList<String> diab_fasting_date = new ArrayList<>(1);
    ArrayList<String> diab_pp = new ArrayList<>(1);
    ArrayList<String> diab_pp_date = new ArrayList<>(1);
    ArrayList<String> diab_random = new ArrayList<>(1);
    ArrayList<String> diab_random_date = new ArrayList<>(1);
    int k1, k2, k3;
    String[] f1 = new String[5];
    String[] f2 = new String[5];
    String[] f3 = new String[5];
    private ProgressDialog progressDialog;
    private ListView SugarListView;
    private ArrayList<Data_class_four> dy = new ArrayList<Data_class_four>();
    private SugarAdapter sugarAdapter;
   // private PassingThrough obj;
    private String[] f_d = new String[5];
    private View floatingActionButtonfasting;
    private View floatingActionButtonpp;
    private String[] p_d = new String[5];
    private View floatingActionButtonrandom;
    private String[] r_d = new String[5];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_sugar, container, false);
        pid = getActivity().getIntent().getStringExtra("patient");
        floatingActionButtonfasting = rootView.findViewById(R.id.fast);
        floatingActionButtonpp = rootView.findViewById(R.id.pp_render);
        k1 = k2 = k3 = 0;
        relativeLayout = rootView.findViewById(R.id.changerelative);
        floatingActionButtonrandom = rootView.findViewById(R.id.random_render);

        floatingActionButtonrandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(getActivity(), Diabetes_Graph_Random.class);

                k.putExtra("random", diab_random);
                k.putExtra("random_date", diab_random_date);
                k.putExtra("pid", pid);
                startActivity(k);

            }
        });


        floatingActionButtonpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(getActivity(), Diabetes_Graph_PP.class);

                k.putExtra("pp", diab_pp);
                k.putExtra("pp_date", diab_pp_date);
                k.putExtra("pid", pid);
                startActivity(k);

            }
        });


        floatingActionButtonfasting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(getActivity(), DiabetesGraphActivity.class);
                //Collections.reverse(diab_fasting);

                k.putExtra("fasting", diab_fasting);
                k.putExtra("fasting_date", diab_fasting_date);
                k.putExtra("pid", pid);
                startActivity(k);
            }
        });
        init();

        return rootView;
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
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<SugarDetail> call = apiService.getDetails9(pid);
        call.enqueue(new Callback<SugarDetail>() {
            @Override
            public void onResponse(Call<SugarDetail> call, final Response<SugarDetail> result) {
                progressDialog.dismiss();
                if(result.body().getError()){
                    bullshit();
                }
                else {
                    if (result.body().getSugarlist().size() == 0) {
                        bullshit();
                    }
                    if (result != null) {
                        if (result.body().getSugarlist().size() > 0) {
                            for (int i = 0; i < result.body().getSugarlist().size(); i++) {
                                dy.add(i, new Data_class_four(result.body().getSugarlist().get(i).getSugarFirst(),
                                        result.body().getSugarlist().get(i).getSugarPp(),
                                        result.body().getSugarlist().get(i).getSugarRandom(),
                                        convert(result.body().getSugarlist().get(i).getTimestamp())));

                                if (!Objects.equals(result.body().getSugarlist().get(i).getSugarFirst(), "NIL")) {
                                    diab_fasting.add(k1, result.body().getSugarlist().get(i).getSugarFirst());
                                    diab_fasting_date.add(k1, result.body().getSugarlist().get(i).getTimestamp());
                                    k1++;
                                }
                                if (!Objects.equals(result.body().getSugarlist().get(i).getSugarPp(), "NIL")) {
                                    diab_pp.add(k2, result.body().getSugarlist().get(i).getSugarPp());
                                    diab_pp_date.add(k2, result.body().getSugarlist().get(i).getTimestamp());
                                    k2++;
                                }
                                if (!Objects.equals(result.body().getSugarlist().get(i).getSugarRandom(), "NIL")) {
                                    diab_random.add(k3, result.body().getSugarlist().get(i).getSugarRandom());
                                    diab_random_date.add(k3, result.body().getSugarlist().get(i).getTimestamp());
                                    k3++;
                                }

                            }
                            //obj = new PassingThrough(diab_fasting, diab_fasting_date, diab_pp, diab_pp_date, diab_random, diab_random_date);
                            sugarAdapter = new SugarAdapter(getContext(), dy);

                            SugarListView = (ListView) rootView.findViewById(R.id.list_of_sugar);
                            try {
                                SugarListView.setAdapter(sugarAdapter);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (result.body().getSugarlist().size() != 0) {
                        SugarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
            public void onFailure(Call<SugarDetail> call, Throwable t) {
                progressDialog.dismiss();
                bullshit();
            }
        });

    }
    private String convert(String time) {
        long tim = Long.parseLong(time);
        tim = tim *1000;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("d MMM yyyy" + "\n" + "HH:mm:ss");
        return formatter.format(tim);

        //return date;
    }
}
