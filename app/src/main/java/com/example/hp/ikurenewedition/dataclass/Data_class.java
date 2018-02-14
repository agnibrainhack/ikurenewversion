package com.example.hp.ikurenewedition.dataclass;

/**
 * Created by Nilagnik on 24-09-2017.
 */

public class Data_class {

    private String title = "";
    private String description = "";
    private String time="";

    public Data_class(String title,String description,String time) {
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
