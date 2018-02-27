package com.example.hp.ikurenewedition.pojodatamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hp on 23-02-2018.
 */

public class SendData {
    @SerializedName("pid")
    @Expose
    private String pid;

    @SerializedName("timestamp")
    @Expose
    private String timestamp;

    @SerializedName("type")
    @Expose
    private String type;


    public String getId() {
        return pid;
    }

    public void setId(String id) {
        this.pid = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String gettype() {
        return type;
    }

    public void settype(String type) {
        this.type = type;
    }



}
