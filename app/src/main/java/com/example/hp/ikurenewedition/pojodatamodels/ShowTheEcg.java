package com.example.hp.ikurenewedition.pojodatamodels;

/**
 * Created by hp on 11-01-2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowTheEcg {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("ecg_url")
    @Expose
    private String ecgUrl;

    /**
     * No args constructor for use in serialization
     *
     */
    public ShowTheEcg() {
    }

    /**
     *
     * @param timestamp
     * @param error
     * @param ecgUrl
     */
    public ShowTheEcg(Boolean error, String timestamp, String ecgUrl) {
        super();
        this.error = error;
        this.timestamp = timestamp;
        this.ecgUrl = ecgUrl;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getEcgUrl() {
        return ecgUrl;
    }

    public void setEcgUrl(String ecgUrl) {
        this.ecgUrl = ecgUrl;
    }

}