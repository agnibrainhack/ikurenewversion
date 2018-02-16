package com.example.hp.ikurenewedition.pojodatamodels;

/**
 * Created by hp on 10-01-2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PresListDetail {



        @SerializedName("error")
        @Expose
        private Boolean error;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("prescriptionlist")
        @Expose
        private List<Prescriptionlist> prescriptionlist = null;

        /**
         * No args constructor for use in serialization
         */
        public PresListDetail() {
        }

        /**
         * @param message
         * @param error
         * @param prescriptionlist
         */
        public PresListDetail(Boolean error, String message, List<Prescriptionlist> prescriptionlist) {
            super();
            this.error = error;
            this.message = message;
            this.prescriptionlist = prescriptionlist;
        }

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

        public List<Prescriptionlist> getPrescriptionlist() {
            return prescriptionlist;
        }

        public void setPrescriptionlist(List<Prescriptionlist> prescriptionlist) {
            this.prescriptionlist = prescriptionlist;
        }

    }

