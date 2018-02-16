package com.example.hp.ikurenewedition.pojodatamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hp on 10-02-2018.
 */

public class BPDetails {

    @SerializedName("bplist")
    @Expose
    private List<Bplist> bplist = null;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Bplist> getBplist() {
        return bplist;
    }

    public void setBplist(List<Bplist> bplist) {
        this.bplist = bplist;
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