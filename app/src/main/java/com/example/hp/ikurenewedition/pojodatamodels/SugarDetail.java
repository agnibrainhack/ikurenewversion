package com.example.hp.ikurenewedition.pojodatamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hp on 02-02-2018.
 */

public class SugarDetail {

    @SerializedName("sugarlist")
    @Expose
    private List<Sugarlist> sugarlist = null;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Sugarlist> getSugarlist() {
        return sugarlist;
    }

    public void setSugarlist(List<Sugarlist> sugarlist) {
        this.sugarlist = sugarlist;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}