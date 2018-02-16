package com.example.hp.ikurenewedition.pojodatamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gyalpo on 1/5/2018.
 */

public class CardDetails {


        @SerializedName("error")
        @Expose
        private Boolean error;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("patientlist")
        @Expose
        private ArrayList<Patientlist> patientlist = new ArrayList<>();

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

        public List<Patientlist> getPatientlist() {
            return patientlist;
        }

        public void setPatientlist(ArrayList<Patientlist> patientlist) {
            this.patientlist = patientlist;
        }


    }




