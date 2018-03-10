package com.example.hp.ikurenewedition.dataclass;

/**
 * Created by hp on 02-02-2018.
 */

public class Data_class_six {
    private String date = "";
    private String type = "";
    private String status = "";


    public Data_class_six(String date, String type, String status) {
        this.date = date;
        this.type = type;
        this.status = status;


    }


    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

}
