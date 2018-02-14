package com.example.hp.ikurenewedition.dataclass;

/**
 * Created by hp on 10-01-2018.
 */

public class Data_class_two {
    private String title = "";
    private String description ="";
    private String time="";

    public Data_class_two(String title, String description, String time) {
        this.title=title;
        this.description=description;
        this.time=time;

    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public String gettime(){return time;}
}
