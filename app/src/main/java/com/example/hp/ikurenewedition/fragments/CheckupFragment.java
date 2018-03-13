package com.example.hp.ikurenewedition.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hp.ikurenewedition.CheckupDetailsActivity;
import com.example.hp.ikurenewedition.R;
import com.example.hp.ikurenewedition.RequestService;
import com.example.hp.ikurenewedition.adapters.CheckupAdapter;
import com.example.hp.ikurenewedition.dataclass.Data_class_six;
import com.example.hp.ikurenewedition.pojodatamodels.CheckupDetails;
import com.example.hp.ikurenewedition.pojodatamodels.ConfirmService;
import com.example.hp.ikurenewedition.pojodatamodels.DataUpload;
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
 * Created by root on 15/2/18.
 */

public class CheckupFragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener {
    String pid;
    ProgressDialog progressDialog;
    View rootView;
    SwipeRefreshLayout swipeRefreshLayout;
    View floatingact;
    String[] pending = new String[5];
    private ArrayList<Data_class_six> dy = new ArrayList<>();
    private CheckupAdapter checkupAdapter;
    private ListView checkListView;
    private boolean start;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_checkup, container, false);
        pid = getActivity().getIntent().getStringExtra("patient");
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);
        checkListView = (ListView) rootView.findViewById(R.id.list_of_checkups);
        floatingact = rootView.findViewById(R.id.service_request);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimaryDark,
                R.color.colorred,
                R.color.colorAccent);
        floatingact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RequestService.class);
                intent.putExtra("pid", pid);
                intent.putExtra("block", pending);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });
        init();
        return rootView;
    }

    @Override
    public void onRefresh() {
        //fetchMovies();
        start = true;
        dy.clear();
        checkListView.setAdapter(null);
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
        //Toast.makeText(getActivity(), "No Checkup record Found \nIf You have taken any test then wait for 24hrs", Toast.LENGTH_SHORT).show();
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
        Call<CheckupDetails> call = apiService.getDetails11(pid);
        call.enqueue(new Callback<CheckupDetails>() {
            @Override
            public void onResponse(Call<CheckupDetails> call, final Response<CheckupDetails> result) {
                if (!start)
                    progressDialog.dismiss();
                else
                    swipeRefreshLayout.setRefreshing(false);
                if(result.body().getError()){
                    bullshit();
                } else {
                    if (result.body().getCheckupreqlist().size() == 0) {
                        bullshit();
                    }
                    if (result != null) {
                        if (result.body().getCheckupreqlist().size() > 0) {
                            for (int i = 0; i < result.body().getCheckupreqlist().size(); i++) {
                                dy.add(i, new Data_class_six(convert(result.body().getCheckupreqlist().get(i).getTimestamp()),
                                        result.body().getCheckupreqlist().get(i).getType(), result.body().getCheckupreqlist().get(i).getStatus()

                                ));
                                //Toast.makeText(getActivity(),result.body().getCheckupreqlist().get(i).getType(),Toast.LENGTH_SHORT).show();
                            }
                            for (int i = 0; i < result.body().getPendinglist().size(); i++) {
                                pending[i] = result.body().getPendinglist().get(i).getType();
                            }
                            checkupAdapter = new CheckupAdapter(getContext(), dy);


                            try {
                                checkListView.setAdapter(checkupAdapter);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    if (result.body().getCheckupreqlist().size() != 0) {
                        checkListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //Toast.makeText(List_display.this,"Hello",Toast.LENGTH_SHORT).show();
                                if (!Objects.equals(result.body().getCheckupreqlist().get(position).getStatus(), "Done")) {
                                    String url = result.body().getCheckupreqlist().get(position).getId();
                                    String time = result.body().getCheckupreqlist().get(position).getTimestamp();
                                    final String id_upload = result.body().getCheckupreqlist().get(position).getId();

                                    AlertDialog.Builder adb = new AlertDialog.Builder(
                                            getActivity());
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
                                    adb.show();// pass the intent here
                                }
                            }
                        });
                    }
                }

            }

            @Override
            public void onFailure(Call<CheckupDetails> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                else
                    swipeRefreshLayout.setRefreshing(false);
                bullshit();
            }
        });

    }

    private void uploadToServer(String id) {
        progressDialog = new ProgressDialog(getActivity());
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
                    Toast.makeText(getActivity(), "Couldn't be uploaded Try again", Toast.LENGTH_LONG).show();
                } else if (!response.body().getError()) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Uploaded successfully", Toast.LENGTH_LONG).show();
                    dy.clear();
                    checkListView.setAdapter(null);
                    init();


                }

            }

            @Override
            public void onFailure(Call<DataUpload> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Image couldn't be uploaded Try again", Toast.LENGTH_LONG).show();

            }
        });
    }
    private String convert(String time) {
        long tim = Long.parseLong(time);
        //tim = tim *1000;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        return formatter.format(tim);

        //return date;
    }
}

