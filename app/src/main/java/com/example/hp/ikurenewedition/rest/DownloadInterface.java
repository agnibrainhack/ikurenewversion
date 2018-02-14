package com.example.hp.ikurenewedition.rest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by hp on 15-01-2018.
 */

public interface DownloadInterface {
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);
}
