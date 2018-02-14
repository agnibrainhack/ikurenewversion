package com.example.hp.ikurenewedition.dataclass;

/**
 * Created by hp on 02-02-2018.
 */

public class Data_class_four {
    private String fasting = "";
    private String pp ="";
    private String random ="";
    private String time="";

    public Data_class_four(String fasting, String pp, String random, String time) {
        this.fasting = fasting;
        this.pp = pp;
        this.random = random;
        this.time=time;

    }


    public String getfasting() {
        return fasting;
    }
    public String getpp() {
        return pp;
    }
    public String getrandom() {
        return random;
    }
    public String gettime(){return time;}
}
