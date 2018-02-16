package com.example.hp.ikurenewedition.pojodatamodels;

/**
 * Created by hp on 10-01-2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Prescriptionlist {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;

    /**
     * No args constructor for use in serialization
     *
     */
    public Prescriptionlist() {
    }

    /**
     *
     * @param timestamp
     * @param id
     * @param pid
     */
    public Prescriptionlist(String id, String pid, String timestamp) {
        super();
        this.id = id;
        this.pid = pid;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}

