package com.example.hp.ikurenewedition.pojodatamodels;

/**
 * Created by hp on 31-01-2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VitalTime {

    @SerializedName("vitallist")
    @Expose
    private List<Vitallist> vitallist = null;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Vitallist> getVitallist() {
        return vitallist;
    }

    public void setVitallist(List<Vitallist> vitallist) {
        this.vitallist = vitallist;
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