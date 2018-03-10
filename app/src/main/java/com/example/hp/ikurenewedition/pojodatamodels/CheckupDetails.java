package com.example.hp.ikurenewedition.pojodatamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hp on 15-02-2018.
 */

public class CheckupDetails {
    @SerializedName("checkupreqlist")
    @Expose
    private List<Checkupreqlist> checkupreqlist = null;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("pendinglist")
    @Expose
    private List<Pendinglist> pendinglist = null;

    public List<Checkupreqlist> getCheckupreqlist() {
        return checkupreqlist;
    }

    public void setCheckupreqlist(List<Checkupreqlist> checkupreqlist) {
        this.checkupreqlist = checkupreqlist;
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

    public List<Pendinglist> getPendinglist() {
        return pendinglist;
    }

    public void setPendinglist(List<Pendinglist> pendinglist) {
        this.pendinglist = pendinglist;
    }
}
