package com.example.hp.ikurenewedition;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hp.ikurenewedition.pojodatamodels.ShowTheImage;
import com.example.hp.ikurenewedition.rest.ApiClient;
import com.example.hp.ikurenewedition.rest.ApiInterface;
import com.example.hp.ikurenewedition.rest.DownloadInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hp on 10-01-2018.
 */

public class DisplayPresActivity extends AppCompatActivity {
    public File prespdf;
    public String downurl;
    Intent i;
    String id;
    ImageView img;
    ProgressDialog progressDialog;
    Button print;
    byte[] imageByteArray;
    boolean what_do_you_want_to_do =false;
    String timestamp;
    View floatingActionButton, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showimage);
        i = getIntent();
        id = i.getStringExtra("img");
        img = (ImageView)findViewById(R.id.showimg);
        save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadthefile(downurl,timestamp);
                }


        });
        //Toast.makeText(DisplayPresActivity.this, id, Toast.LENGTH_LONG).show();
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadandprint(downurl,timestamp);

            }
        });

        init();

    }

    public void bullshit() {
        Toast.makeText(DisplayPresActivity.this, "Network Error \nTry again", Toast.LENGTH_SHORT).show();
        //Intent i=new Intent(NetworkActivity.this,MainActivity.class);
        finish();
        //startActivity(i);


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
        Call<ShowTheImage> call = apiService.getDetails4(id);
        call.enqueue(new Callback<ShowTheImage>() {


            @Override
            public void onResponse(Call<ShowTheImage> call, Response<ShowTheImage> response) {
                if(response.body().getError()){
                    //Toast.makeText(List_display.this,"Nothing to show",Toast.LENGTH_LONG).show();
                    //Intent i=new Intent(List_display.this,MainActivity.class);
                    //startActivity(i);
                    // return;
                    bullshit();

                }

                else{
                    //imageByteArray = Base64.decode(response.body().getPrescriptionImage(), Base64.DEFAULT);
                    downurl = response.body().getPrescriptionImage();
                    timestamp = response.body().getTimestamp();
                    Glide.with(getBaseContext())
                            .load(response.body().getPrescriptionImage())
                            .placeholder(R.drawable.ikurelogo)
                            .into(img);
                    progressDialog.dismiss();
                    save.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ShowTheImage> call, Throwable t) {
                progressDialog.dismiss();
                bullshit();
            }
        });

    }


    public void downloadandprint(String url ,final String timestamp){
        progressDialog.show();
        DownloadInterface apiService = ApiClient.getClient().create(DownloadInterface.class);
        Call<ResponseBody> call = apiService.downloadFileWithDynamicUrlSync(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()){
                    boolean writtenToDisk = writeResponseBodyToDisk(response.body(), timestamp);
                    if(writtenToDisk){
                        //Toast.makeText(DisplayEcgActivity.this,"We code hard in this cubicles",Toast.LENGTH_LONG).show();
                        PrintHelper photoPrinter = new PrintHelper(getBaseContext());
                        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
                        Bitmap bitmap = BitmapFactory.decodeFile(prespdf.getAbsolutePath());
                        photoPrinter.printBitmap("droids.jpg - test print", bitmap);

                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                bullshit();
            }
        });
    }









    public void downloadthefile(String url, final String timestamp){
        progressDialog.show();
        DownloadInterface apiService = ApiClient.getClient().create(DownloadInterface.class);
        Call<ResponseBody> call = apiService.downloadFileWithDynamicUrlSync(url);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    boolean writtenToDisk = writeResponseBodyToDisk(response.body(),timestamp);
                    if(writtenToDisk){
                        //renderToScreen();
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        Uri photoURI = FileProvider.getUriForFile(getBaseContext(), getBaseContext().getApplicationContext().getPackageName() + ".com.example.root.ikure.provider", prespdf);
                        intent.setDataAndType(photoURI, "image/*");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intent);

                    }
                }
                else{
                    bullshit();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DisplayPresActivity.this,"File cannot be downloaded",Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String timestamp) {
        try {
            // todo change the file location/name according to your needs
            prespdf = new File(getExternalFilesDir(null) + File.separator + "PrescriptionReports"+timestamp+".jpeg");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[8192];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(prespdf);
                progressDialog.dismiss();
                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d(String.valueOf(this), "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }

    }
}