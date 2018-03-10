package com.example.hp.ikurenewedition;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hp.ikurenewedition.adapters.NotesAdapter;
import com.example.hp.ikurenewedition.dataclass.Data_class;
import com.example.hp.ikurenewedition.pojodatamodels.CardDetails;
import com.example.hp.ikurenewedition.rest.ApiClient;
import com.example.hp.ikurenewedition.rest.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.example.root.ikure.pojo.earthquakeModel.Earthquake;
//import com.example.root.ikure.retrofit.RetrofitRepository;
//import com.example.root.ikure.retrofit.exception.BaseException;

/**
 * Created by Gyalpo on 1/3/2018.
 */

public class NetworkActivity extends AppCompatActivity implements View.OnClickListener {
   // RetrofitRepository retrofitRepository;
    RelativeLayout relativeLayout;
    ListView NoteListView;
    ProgressDialog progressDialog;
    NotesAdapter notesAdapter;
    String patient;
    TextView card_no;
    int no_of_hits = 0;
    ArrayList<Data_class> dy=new ArrayList<Data_class>();
    private CardView mCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        Intent i = getIntent();
        patient = i.getStringExtra("Patient_no");
         card_no = (TextView)findViewById(R.id.serve1);
        card_no.setText(patient);
        //Toast.makeText(this, patient, Toast.LENGTH_LONG).show();
        //retrofitRepository = new RetrofitRepository();
        //callAPI();


        init();

    }
    private void init(){
        //retrofitRepository=new RetrofitRepository();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...."+'\n'+"We are figuring things out");
        progressDialog.setCancelable(false);
        callAPI1();
    }
    public void bullshit(){
        Toast.makeText(NetworkActivity.this,"No Registered Patients found \nCheck Your Connection And Try again",Toast.LENGTH_LONG).show();
        Intent i=new Intent(NetworkActivity.this,MainActivity.class);
        finish();
        startActivity(i);

    }
    private void callAPI1(){
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CardDetails> call = apiService.getDetails(patient);
        call.enqueue(new Callback<CardDetails>() {
            @Override
            public void onResponse(Call<CardDetails> call, final Response<CardDetails> result) {
                progressDialog.dismiss();
                if(result.body().getPatientlist().size()==0){
                    //Toast.makeText(List_display.this,"Nothing to show",Toast.LENGTH_LONG).show();
                    //Intent i=new Intent(List_display.this,MainActivity.class);
                    //startActivity(i);
                    // return;
                    bullshit();

                }
                if(result!=null){
                    if(result.body().getPatientlist().size()>0){
                        for(int i=0;i<result.body().getPatientlist().size();i++){
                            dy.add(i,new Data_class(result.body().getPatientlist().get(i).getPid(),
                                    result.body().getPatientlist().get(i).getName(),
                                    result.body().getPatientlist().get(i).getAge()));
                        }
                        notesAdapter = new NotesAdapter(getBaseContext(), dy);

                        NoteListView = (ListView) findViewById(R.id.listview);
                        try {
                            NoteListView.setAdapter(notesAdapter);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                }

                if(result.body().getPatientlist().size()!=0){
                    NoteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //Toast.makeText(List_display.this,"Hello",Toast.LENGTH_SHORT).show();
                            String url=result.body().getPatientlist().get(position).getPid();
                            String name = result.body().getPatientlist().get(position).getName();
                            Intent k=new Intent( NetworkActivity.this,OnePerson.class);
                            //String str = Integer.toString(position);
                            k.putExtra("patient",url);
                            k.putExtra("card_no",patient);
                            k.putExtra("patient_name", name);
                            startActivity(k);
                            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                            // pass the intent here
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CardDetails> call, Throwable t) {
                progressDialog.dismiss();
                //Toast.makeText(NetworkActivity.this,"Network error",Toast.LENGTH_LONG).show();
                bullshit();
            }
        });
    }


    @Override
    public void onClick(View v) {

    }
    @Override
    public void onBackPressed() {
        //finish();
        if(no_of_hits == 0){
            no_of_hits ++;
            Toast.makeText(NetworkActivity.this,"Hit once again to Exit",Toast.LENGTH_SHORT).show();

        }
        else if(no_of_hits > 0) {
            dy = null;
            notesAdapter = null;
            //startActivity(new Intent(this,MainActivity.class));
            //finish();
            super.onBackPressed();
        }

    }
    /*static  String getDateString(long millis){
        SimpleDateFormat format=new SimpleDateFormat("EEE, d MMM yyyy 'at' HH:mm:ss z");
        return format.format(millis);
    }*/


}