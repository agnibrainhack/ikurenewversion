package com.example.hp.ikurenewedition;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.example.hp.ikurenewedition.pojodatamodels.ShowTheEcg;
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

//import com.github.barteksc.pdfviewer.PDFView;
//import com.github.barteksc.pdfviewer.util.FitPolicy;

/**
 * Created by hp on 11-01-2018.
 */

public class DisplayEcgActivity extends AppCompatActivity {
    Intent i;
    String id;
    ProgressDialog progressDialog;
    public File ecgpdf;
    //PDFView pdfView;
    ImageView imageView;
    Button save,print;

    byte[] imageByteArray;
    String timestamp;
    String downurl;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showecg);
        i = getIntent();
        id = i.getStringExtra("id");
        imageView = (ImageView)findViewById(R.id.pdfView);
        save =  (Button)findViewById(R.id.save);
       floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        save.setVisibility(View.INVISIBLE);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadthefile(downurl,timestamp);
            }
        });
        //Toast.makeText(DisplayEcgActivity.this, id, Toast.LENGTH_LONG).show();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadandprint(downurl,timestamp);

            }
        });


        init();
    }

    public void bullshit() {
        Toast.makeText(DisplayEcgActivity.this, "Sorry Network Error Couldn't Download the file \nTry again", Toast.LENGTH_SHORT).show();
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
        Call<ShowTheEcg> call = apiService.getDetails6(id);
        call.enqueue(new Callback<ShowTheEcg>() {
            @Override
            public void onResponse(Call<ShowTheEcg> call, Response<ShowTheEcg> response) {
                progressDialog.dismiss();
                if(response.body().getError()){
                    bullshit();
                }
                else{
                    progressDialog.dismiss();
                    downurl = response.body().getEcgUrl();
                    timestamp = response.body().getTimestamp();
                    //imageByteArray = Base64.decode(response.body().getEcgUrl(), Base64.DEFAULT);

                    Glide.with(getBaseContext())
                            .load(response.body().getEcgUrl())
                            .placeholder(R.drawable.ikurelogo)
                            .into(imageView);
                        save.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onFailure(Call<ShowTheEcg> call, Throwable t) {
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
                        Bitmap bitmap = BitmapFactory.decodeFile(ecgpdf.getAbsolutePath());
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
                        Uri photoURI = FileProvider.getUriForFile(getBaseContext(), getBaseContext().getApplicationContext().getPackageName() + ".com.example.root.ikure.provider", ecgpdf);

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
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
                Toast.makeText(DisplayEcgActivity.this,"File cannot be downloaded",Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String timestamp) {
        try {


            // todo change the file location/name according to your needs
            ecgpdf = new File(getExternalFilesDir(null) + File.separator + "ECGReports"+timestamp+".jpeg");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[8192];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(ecgpdf);
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

    private String getFileDestinationPath(){
        String generatedFilename = String.valueOf(System.currentTimeMillis());
        String filePathEnvironment = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        File directoryFolder = new File(filePathEnvironment + "/pictures/");
        if(!directoryFolder.exists()){
            directoryFolder.mkdir();
        }
        //Log.d(TAG, "Full path " + filePathEnvironment + "/video/" + generatedFilename + ".mp4");
        return filePathEnvironment + "/pictures/" + generatedFilename + ".jpeg";
    }




}