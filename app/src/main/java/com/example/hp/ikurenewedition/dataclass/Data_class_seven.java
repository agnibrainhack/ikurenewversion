package com.example.hp.ikurenewedition.dataclass;

/**
 * Created by hp on 22-02-2018.
 */

public class Data_class_seven {

    private String checkuptype = "";
    private String status = "";


    public Data_class_seven(String checkuptype, String status) {
        this.checkuptype = checkuptype;
        this.status = status;


    }


    public String getCheckuptype() {
        return checkuptype;
    }

    public String getStatus() {
        return status;
    }

}
