package com.example.hp.ikurenewedition.dataclass;

/**
 * Created by hp on 02-02-2018.
 */

public class Data_class_five {
    private String date = "";
    private String time = "";
    private String systolic = "";
    private String diastolic = "";

    public Data_class_five(String date, String time, String systolic, String diastolic) {
        this.date = date;
        this.time = time;
        this.systolic = systolic;
        this.diastolic = diastolic;

    }


    public String getDate() {
        return date;
    }

    public String getSystolic() {
        return systolic;
    }

    public String getDiastolic() {
        return diastolic;
    }

    public String gettime() {
        return time;
    }
}
