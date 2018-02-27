package com.example.hp.ikurenewedition.pojodatamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hp on 23-02-2018.
 */

public class DataUpload {
    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("message")
    @Expose
    private String message;


    public boolean  getError(){return error;}
    public String  getMessage(){return message;}


}
