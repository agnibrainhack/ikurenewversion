package com.example.hp.ikurenewedition;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hp.ikurenewedition.adapters.CheckupAdapter;
import com.example.hp.ikurenewedition.dataclass.Data_class_six;
import com.example.hp.ikurenewedition.pojodatamodels.CheckupDetails;
import com.example.hp.ikurenewedition.pojodatamodels.DataUpload;
import com.example.hp.ikurenewedition.pojodatamodels.SendData;
import com.example.hp.ikurenewedition.rest.ApiClient;
import com.example.hp.ikurenewedition.rest.ApiInterface;
import com.example.hp.ikurenewedition.rest.ApiInterface1;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hp on 22-02-2018.
 */

public class RequestService extends AppCompatActivity {

    Button date1, date2, date3, date4, date5;
    Button time1, time2, time3, time4, time5;
    Button submit1, submit2, submit3, submit4, submit5;  //for any developer who is going to work on this project, plz use butterknife for this implementation
    CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6;
    Calendar dateSelected = Calendar.getInstance();
    Button selected;
    private Intent i;
    private String pid;
    private ProgressDialog progressDialog;
    private String timestamp;
    private String request_type_send;
    private DatePickerDialog datePickerDialog;
    private DateTimeFormatter dateFormatter;
    private  int year,month,day;
    private Calendar calendar;
    private String[] pending = new String[5];
    private int globalvarday;
    private String glovarall;
    private DatePickerDialog.OnDateSetListener myDateListener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int ar1, int ar2, int arg3) {


            showDate(ar1, ar2, arg3);


            arg0.updateDate(ar1, ar2, arg3);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);
        i = getIntent();
        pid = i.getStringExtra("pid");
        //pending = i.getStringArrayExtra("block");
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
        cardView6 = findViewById(R.id.card6);
        init();


        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_date = date1.getText().toString();
                String user_time = time1.getText().toString();
                String combo = user_date + " " + user_time;
                if (Objects.equals(user_date, "Select Date")) {
                    Toast.makeText(RequestService.this, "Please select a Date before requesting service", Toast.LENGTH_LONG).show();
                    return;
                }

                if (Objects.equals(user_time, "Set time") || Objects.equals(user_time, "Select Time again")) {
                    Toast.makeText(RequestService.this, "Please select a Time before requesting service", Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    Date parsedDate = dateFormat.parse(combo);
                    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                    //String str = String.valueOf(timestamp.getTime());
                    uploadToServer("ECG", String.valueOf(timestamp.getTime()), cardView1);
                    if (cardView1.getVisibility() == View.GONE && cardView2.getVisibility() == View.GONE && cardView3.getVisibility() == View.GONE
                            && cardView4.getVisibility() == View.GONE && cardView5.getVisibility() == View.GONE) {
                        cardView6.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) { //this generic but you can control another types of exception
                    // look the origin of excption
                }

            }
        });
        submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_date = date2.getText().toString();
                String user_time = time2.getText().toString();
                String combo = user_date + " " + user_time;
                if (Objects.equals(user_date, "Select Date")) {
                    Toast.makeText(RequestService.this, "Please select a Date before requesting service", Toast.LENGTH_LONG).show();
                    return;
                }

                if (Objects.equals(user_time, "Set time") || Objects.equals(user_time, "Select Time again")) {
                    Toast.makeText(RequestService.this, "Please select a Time before requesting service", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    Date parsedDate = dateFormat.parse(combo);
                    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                    uploadToServer("Blood Pressure", String.valueOf(timestamp.getTime()), cardView2);
                    if (cardView1.getVisibility() == View.GONE && cardView2.getVisibility() == View.GONE && cardView3.getVisibility() == View.GONE
                            && cardView4.getVisibility() == View.GONE && cardView5.getVisibility() == View.GONE) {
                        cardView6.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) { //this generic but you can control another types of exception
                    // look the origin of excption
                }

            }
        });

        submit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_date = date3.getText().toString();
                String user_time = time3.getText().toString();
                String combo = user_date + " " + user_time;
                if (Objects.equals(user_date, "Select Date")) {
                    Toast.makeText(RequestService.this, "Please select a Date before requesting service", Toast.LENGTH_LONG).show();
                    return;
                }

                if (Objects.equals(user_time, "Set time") || Objects.equals(user_time, "Select Time again")) {
                    Toast.makeText(RequestService.this, "Please select a Time before requesting service", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    Date parsedDate = dateFormat.parse(combo);
                    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                    uploadToServer("Sugar Test", String.valueOf(timestamp.getTime()), cardView3);
                    if (cardView1.getVisibility() == View.GONE && cardView2.getVisibility() == View.GONE && cardView3.getVisibility() == View.GONE
                            && cardView4.getVisibility() == View.GONE && cardView5.getVisibility() == View.GONE) {
                        cardView6.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) { //this generic but you can control another types of exception
                    // look the origin of excption
                }

            }
        });

        submit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_date = date4.getText().toString();
                String user_time = time4.getText().toString();
                String combo = user_date + " " + user_time;
                if (Objects.equals(user_date, "Select Date")) {
                    Toast.makeText(RequestService.this, "Please select a Date before requesting service", Toast.LENGTH_LONG).show();
                    return;
                }

                if (Objects.equals(user_time, "Set time") || Objects.equals(user_time, "Select Time again")) {
                    Toast.makeText(RequestService.this, "Please select a Time before requesting service", Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    Date parsedDate = dateFormat.parse(combo);
                    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                    uploadToServer("Vitals", String.valueOf(timestamp.getTime()), cardView4);
                    if (cardView1.getVisibility() == View.GONE && cardView2.getVisibility() == View.GONE && cardView3.getVisibility() == View.GONE
                            && cardView4.getVisibility() == View.GONE && cardView5.getVisibility() == View.GONE) {
                        cardView6.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) { //this generic but you can control another types of exception
                    // look the origin of excption
                }

            }
        });

        submit5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_date = date5.getText().toString();
                String user_time = time5.getText().toString();
                String combo = user_date + " " + user_time;
                if (Objects.equals(user_date, "Select Date")) {
                    Toast.makeText(RequestService.this, "Please select a Date before requesting service", Toast.LENGTH_LONG).show();
                    return;
                }

                if (Objects.equals(user_time, "Set time") || Objects.equals(user_time, "Select Time again")) {
                    Toast.makeText(RequestService.this, "Please select a Time before requesting service", Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    Date parsedDate = dateFormat.parse(combo);
                    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                    uploadToServer("Haemoglobin", String.valueOf(timestamp.getTime()), cardView5);
                    if (cardView1.getVisibility() == View.GONE && cardView2.getVisibility() == View.GONE && cardView3.getVisibility() == View.GONE
                            && cardView4.getVisibility() == View.GONE && cardView5.getVisibility() == View.GONE) {
                        cardView6.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) { //this generic but you can control another types of exception
                    // look the origin of excption
                }

            }
        });

        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time2.setText("Select Time");
                time3.setText("Select Time");
                time4.setText("Select Time");
                time5.setText("Select Time");
                if (!Objects.equals(date1.getText().toString(), "Select Date"))
                    getTimeFromUser(time1);
                else
                    Toast.makeText(RequestService.this, "Sorry Enter Date First", Toast.LENGTH_LONG).show();


            }
        });

        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time1.setText("Select Time");
                time3.setText("Select Time");
                time4.setText("Select Time");
                time5.setText("Select Time");
                if (!Objects.equals(date2.getText().toString(), "Select Date"))
                    getTimeFromUser(time2);
                else
                    Toast.makeText(RequestService.this, "Sorry Enter Date First", Toast.LENGTH_LONG).show();

            }
        });
        time3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time2.setText("Select Time");
                time1.setText("Select Time");
                time4.setText("Select Time");
                time5.setText("Select Time");
                if (!Objects.equals(date3.getText().toString(), "Select Date"))
                    getTimeFromUser(time3);
                else
                    Toast.makeText(RequestService.this, "Sorry Enter Date First", Toast.LENGTH_LONG).show();

            }
        });

        time4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time2.setText("Select Time");
                time3.setText("Select Time");
                time1.setText("Select Time");
                time5.setText("Select Time");
                if (!Objects.equals(date4.getText().toString(), "Select Date"))
                    getTimeFromUser(time4);
                else
                    Toast.makeText(RequestService.this, "Sorry Enter Date First", Toast.LENGTH_LONG).show();

            }
        });
        time5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time2.setText("Select Time");
                time3.setText("Select Time");
                time4.setText("Select Time");
                time1.setText("Select Time");
                if (!Objects.equals(date5.getText().toString(), "Select Date"))
                    getTimeFromUser(time5);
                else
                    Toast.makeText(RequestService.this, "Sorry Enter Date First", Toast.LENGTH_LONG).show();

            }
        });
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

    private void checktoblockviews() {
        for (int i = 0; i < pending.length; i++) {
            if (Objects.equals(pending[i], "ECG")) {
                cardView1.setVisibility(View.GONE);
            }
            if (Objects.equals(pending[i], "Blood Pressure")) {
                cardView2.setVisibility(View.GONE);
            }
            if (Objects.equals(pending[i], "Sugar Test")) {
                cardView3.setVisibility(View.GONE);
            }
            if (Objects.equals(pending[i], "Vitals")) {
                cardView4.setVisibility(View.GONE);
            }
            if (Objects.equals(pending[i], "Haemoglobin")) {
                cardView5.setVisibility(View.GONE);
            }

        }
        if (cardView1.getVisibility() == View.GONE && cardView2.getVisibility() == View.GONE && cardView3.getVisibility() == View.GONE
                && cardView4.getVisibility() == View.GONE && cardView5.getVisibility() == View.GONE) {
            cardView6.setVisibility(View.VISIBLE);
        }
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

    public void getTimeFromUser(Button getbutton) {
        final Button button;
        button = getbutton;

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("HH");
        DateFormat date2 = new SimpleDateFormat("d");
        DateFormat date3 = new SimpleDateFormat("SS");

        date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        date2.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        date3.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));

        String localTime = date.format(currentLocalTime);
        String localdate = date2.format(currentLocalTime);
        String localmin = date3.format(currentLocalTime);

        final int localhour = Integer.parseInt(localTime);
        final int localday = Integer.parseInt(localdate);
        final int localmiute = Integer.parseInt(localmin);









        final int[] selectedHourthis = new int[1];
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String hour1 = String.valueOf(selectedHour);
                String minute1 = String.valueOf(selectedMinute);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                Date parsedDate = null;
                try {
                    parsedDate = dateFormat.parse(glovarall + " " + hour1 + ":" + minute1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                long timenowselected = timestamp.getTime();



                if (hour1.length() == 1) {
                    hour1 = "0" + hour1;
                }
                if (minute1.length() == 1) {
                    minute1 = "0" + minute1;
                }
                button.setText(hour1 + ":" + minute1);


                selectedHourthis[0] = selectedHour;
                if (selectedHourthis[0] < 10 || selectedHourthis[0] > 18) {
                    Toast.makeText(RequestService.this, "Please Select a time between 10am to 6pm", Toast.LENGTH_LONG).show();
                    button.setText("Select Time again");
                }
                if (selectedHourthis[0] < localhour && localday == globalvarday) {
                    Toast.makeText(RequestService.this, "Please Select a date in Future", Toast.LENGTH_LONG).show();
                    button.setText("Select Time again");
                }
                if (timenowselected < System.currentTimeMillis()) {
                    Toast.makeText(RequestService.this, "Please Select a date in Future", Toast.LENGTH_LONG).show();
                    button.setText("Select Time again");
                }

            }
        }, hour, minute, false);
        //mTimePicker.setTitle("");
        mTimePicker.setTitle("Please select a time between 10AM and 6PM");
        mTimePicker.show();

    }

    private void showDate(int year,int month,int day){
        globalvarday = day;

        selected.setText(new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day));
        glovarall = selected.getText().toString();

    }

    //uploadToServer();


    public void bullshit(){
        //Toast.makeText(RequestService.this,"Network Error \nTry again",Toast.LENGTH_SHORT).show();
        //Intent i=new Intent(RequestService.this,NetworkActivity.class);
        //finish();
        //startActivity(i);

    }

    private void uploadToServer(String type, String timestampsend, final CardView cardView) {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...."+'\n'+"We are figuring things out");
        progressDialog.setCancelable(false);
        //cardView1.setVisibility(View.GONE);
        progressDialog.show();
        SendData snd = new SendData();
        snd.setId(pid);
        snd.setTimestamp(timestampsend);
        snd.settype(type);
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
                    cardView.setVisibility(View.GONE);
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

    private void init() {
        //retrofitRepository=new RetrofitRepository();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...." + '\n' + "We are figuring things out");
        progressDialog.setCancelable(false);
        callAPI1();
    }


    private void callAPI1() {
        progressDialog.show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<CheckupDetails> call = apiService.getDetails11(pid);
        call.enqueue(new Callback<CheckupDetails>() {
            @Override
            public void onResponse(Call<CheckupDetails> call, final Response<CheckupDetails> result) {
                progressDialog.dismiss();
                if (result.body().getError()) {
                    bullshit();
                } else {
                    if (result.body().getCheckupreqlist().size() == 0) {
                        bullshit();
                    }
                    if (result != null) {
                        if (result.body().getCheckupreqlist().size() > 0) {
                            for (int i = 0; i < result.body().getPendinglist().size(); i++) {
                                pending[i] = result.body().getPendinglist().get(i).getType();

                                //Toast.makeText(RequestService.this,pending[i],Toast.LENGTH_SHORT).show();
                            }
                            checktoblockviews();

                        }
                    }


                }

            }

            @Override
            public void onFailure(Call<CheckupDetails> call, Throwable t) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                bullshit();
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
