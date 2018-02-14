package com.example.hp.ikurenewedition.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gyalpo on 1/4/2018.
 */

public class ApiClient {
    public static final String BASE_URL = "http://ikurelive.com/app/prescription/api/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        }
        return retrofit;
    }
}
