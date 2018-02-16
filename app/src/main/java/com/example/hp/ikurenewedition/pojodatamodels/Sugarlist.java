package com.example.hp.ikurenewedition.pojodatamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hp on 02-02-2018.
 */

public class Sugarlist {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("sugar_first")
    @Expose
    private String sugarFirst;
    @SerializedName("sugar_pp")
    @Expose
    private String sugarPp;
    @SerializedName("sugar_random")
    @Expose
    private String sugarRandom;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSugarFirst() {
        return sugarFirst;
    }

    public void setSugarFirst(String sugarFirst) {
        this.sugarFirst = sugarFirst;
    }

    public String getSugarPp() {
        return sugarPp;
    }

    public void setSugarPp(String sugarPp) {
        this.sugarPp = sugarPp;
    }

    public String getSugarRandom() {
        return sugarRandom;
    }

    public void setSugarRandom(String sugarRandom) {
        this.sugarRandom = sugarRandom;
    }

}