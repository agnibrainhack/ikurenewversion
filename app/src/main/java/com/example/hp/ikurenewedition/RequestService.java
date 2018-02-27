package com.example.hp.ikurenewedition;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.hp.ikurenewedition.pojodatamodels.DataUpload;
import com.example.hp.ikurenewedition.pojodatamodels.SendData;
import com.example.hp.ikurenewedition.rest.ApiClient;
import com.example.hp.ikurenewedition.rest.ApiInterface1;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hp on 22-02-2018.
 */

public class RequestService extends AppCompatActivity {

    private Intent i;
    private String pid;
    private ProgressDialog progressDialog;
    private String timestamp;
    private String request_type_send;
    Button date1,date2,date3,date4,date5;
    Button time1,time2,time3,time4,time5;
    Button submit1,submit2,submit3,submit4,submit5;  //for any developer who is going to work on this project, plz use butterknife for this implementation
    CardView cardView1,cardView2,cardView3,cardView4,cardView5;
    Calendar dateSelected = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;
    private DateTimeFormatter dateFormatter;
    private  int year,month,day;
    private Calendar calendar;
    Button selected ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);
        i = getIntent();
        pid = i.getStringExtra("pid");
        //Toast.makeText(RequestService.this, pid, Toast.LENGTH_LONG).show();
        calendar=Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
        date1 = findViewById(R.id.selectdate1);
        date2 = findViewById(R.id.selectdate2);
        date3 = findViewById(R.id.selectdate3);
        date4 = findViewById(R.id.selectdate4);
        date5 = findViewById(R.id.selectdate5);

        time1 = findViewById(R.id.selecttime1);
        time2 = findViewById(R.id.selecttime2);
        time3 = findViewById(R.id.selecttime3);
        time4 = findViewById(R.id.selecttime4);
        time5 = findViewById(R.id.selecttime5);

        submit1 = findViewById(R.id.submit1);
        submit2 = findViewById(R.id.submit2);
        submit3 = findViewById(R.id.submit3);
        submit4 = findViewById(R.id.submit4);
        submit5 = findViewById(R.id.submit5);

        cardView1 = findViewById(R.id.card1);
        cardView2 = findViewById(R.id.card2);
        cardView3 = findViewById(R.id.card3);
        cardView4 = findViewById(R.id.card4);
        cardView5 = findViewById(R.id.card5);

        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date2.setText("Select Date");
                date3.setText("Select Date");
                date4.setText("Select Date");
                date5.setText("Select Date");
                selected = date1;
                showDialog(999);

            }
        });

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date1.setText("Select Date");
                date3.setText("Select Date");
                date4.setText("Select Date");
                date5.setText("Select Date");

                selected = date2;
                showDialog(999);

            }
        });

        date3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date1.setText("Select Date");
                date2.setText("Select Date");
                date4.setText("Select Date");
                date5.setText("Select Date");

                selected = date3;
                showDialog(999);

            }
        });
        date4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date2.setText("Select Date");
                date3.setText("Select Date");
                date1.setText("Select Date");
                date5.setText("Select Date");

                selected = date4;
                showDialog(999);

            }
        });
        date5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date2.setText("Select Date");
                date3.setText("Select Date");
                date4.setText("Select Date");
                date1.setText("Select Date");

                selected = date5;
                showDialog(999);

            }
        });


    }

    @Override
    protected Dialog onCreateDialog(int id){
        if(id==999){

            DatePickerDialog dpk=new DatePickerDialog(this,myDateListener1,year,month,day);
            //java.text.SimpleDateFormat format=new java.text.SimpleDateFormat("yyyy-MM-dd");
            final Calendar cal=Calendar.getInstance();
            cal.add(Calendar.DATE,0);
            //format.format(cal.getTime());
            String str = "999999999";

            Calendar cal1 = Calendar.getInstance();
            cal1.add(Calendar.MONTH, 1);
            dpk.getDatePicker().setMaxDate(cal1.getTimeInMillis());
            dpk.getDatePicker().setMinDate(cal.getTimeInMillis());

            return dpk;
        }

        return  null;
    }


    private DatePickerDialog.OnDateSetListener myDateListener1=new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker arg0,int ar1,int ar2,int arg3 ){


            showDate(ar1, ar2, arg3);


            arg0.updateDate(ar1,ar2,arg3);
        }
    };

    private void showDate(int year,int month,int day){
            selected.setText(new StringBuilder().append(year).append("-").append(month+1).append("-").append(day));


    }

    //uploadToServer();


    public void bullshit(){
        Toast.makeText(RequestService.this,"Network Error \nTry again",Toast.LENGTH_SHORT).show();
        //Intent i=new Intent(RequestService.this,NetworkActivity.class);
        //finish();
        //startActivity(i);

    }

    private void uploadToServer(){
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...."+'\n'+"We are figuring things out");
        progressDialog.setCancelable(false);

        progressDialog.show();
        SendData snd = new SendData();
        snd.setId(pid);
        snd.setTimestamp(timestamp);
        snd.settype(request_type_send);
        ApiInterface1 apiService = ApiClient.getClient().create(ApiInterface1.class);
        Call<DataUpload> call = apiService.savePost(snd);
        call.enqueue(new Callback<DataUpload>() {
            @Override
            public void onResponse(Call<DataUpload> call, Response<DataUpload> response) {
                if(response.body().getError()){
                    progressDialog.dismiss();
                    Toast.makeText(RequestService.this,"Couldn't be uploaded Try again",Toast.LENGTH_LONG).show();
                }
                else if(!response.body().getError()) {
                    progressDialog.dismiss();
                    Toast.makeText(RequestService.this, "Uploaded successfully", Toast.LENGTH_LONG).show();
                    //Intent i = new Intent(OnePerson.this, AfterSplash.class);
                    //finish();
                    //startActivity(i);
                }

            }

            @Override
            public void onFailure(Call<DataUpload> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RequestService.this,"Image couldn't be uploaded Try again",Toast.LENGTH_LONG).show();

            }
        });
    }
    private String convert(String time) {
        long tim = Long.parseLong(time);
        tim = tim *1000;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("d MMM yyyy");
        return formatter.format(tim);

        //return date;
    }
}
