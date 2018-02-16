package com.example.hp.ikurenewedition.pojodatamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gyalpo on 1/6/2018.
 */

public class PatientDetails {

        @SerializedName("error")
        @Expose
        private Boolean error;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("age")
        @Expose
        private String age;
        @SerializedName("healthcard_no")
        @Expose
        private String healthcardNo;
        @SerializedName("registration_date")
        @Expose
        private String registration_date;
        @SerializedName("patient_image")
        @Expose
        private String patient_image;


        public String getPatient_image(){return patient_image;}

        public void setPatient_image(String patient_image){this.patient_image = patient_image;}

        public Boolean getError() {
            return error;
        }

        public void setError(Boolean error) {
            this.error = error;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getHealthcardNo() {
            return healthcardNo;
        }

        public void setHealthcardNo(String healthcardNo) {
            this.healthcardNo = healthcardNo;
        }

        public String getRegistration_date() {
        return registration_date;
    }

        public void setRegistration_date(String registration_date) {
        this.registration_date = registration_date;
    }

    }

