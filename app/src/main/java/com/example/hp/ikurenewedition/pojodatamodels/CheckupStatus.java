package com.example.hp.ikurenewedition.pojodatamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hp on 22-02-2018.
 */

public class CheckupStatus {

    @SerializedName("vitalrequestdetails")
    @Expose
    private List<Vitalrequestdetail> vitalrequestdetails = null;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Vitalrequestdetail> getVitalrequestdetails() {
        return vitalrequestdetails;
    }

    public void setVitalrequestdetails(List<Vitalrequestdetail> vitalrequestdetails) {
        this.vitalrequestdetails = vitalrequestdetails;
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
