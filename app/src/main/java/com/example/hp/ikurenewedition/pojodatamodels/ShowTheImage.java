package com.example.hp.ikurenewedition.pojodatamodels;

/**
 * Created by hp on 10-01-2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowTheImage {

        @SerializedName("error")
        @Expose
        private Boolean error;
        @SerializedName("timestamp")
        @Expose
        private String timestamp;
        @SerializedName("prescription_image")
        @Expose
        private String prescriptionImage;

        /**
         * No args constructor for use in serialization
         *
         */
        public ShowTheImage() {
        }

        /**
         *
         * @param timestamp
         * @param error
         * @param prescriptionImage
         */
        public ShowTheImage(Boolean error, String timestamp, String prescriptionImage) {
            super();
            this.error = error;
            this.timestamp = timestamp;
            this.prescriptionImage = prescriptionImage;
        }

        public Boolean getError() {
            return error;
        }

        public void setError(Boolean error) {
            this.error = error;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getPrescriptionImage() {
            return prescriptionImage;
        }

        public void setPrescriptionImage(String prescriptionImage) {
            this.prescriptionImage = prescriptionImage;
        }

    }

