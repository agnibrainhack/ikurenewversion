package com.example.hp.ikurenewedition.pojodatamodels;

/**
 * Created by hp on 11-01-2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EcgListDetail {

        @SerializedName("error")
        @Expose
        private Boolean error;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("ecglist")
        @Expose
        private List<Ecglist> ecglist = null;

        /**
         * No args constructor for use in serialization
         *
         */
        public EcgListDetail() {
        }

        /**
         *
         * @param message
         * @param error
         * @param ecglist
         */
        public EcgListDetail(Boolean error, String message, List<Ecglist> ecglist) {
            super();
            this.error = error;
            this.message = message;
            this.ecglist = ecglist;
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

        public List<Ecglist> getEcglist() {
            return ecglist;
        }

        public void setEcglist(List<Ecglist> ecglist) {
            this.ecglist = ecglist;
        }

    }

