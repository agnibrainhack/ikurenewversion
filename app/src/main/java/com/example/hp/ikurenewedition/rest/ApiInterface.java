package com.example.hp.ikurenewedition.rest;


import com.example.hp.ikurenewedition.earthquakeModel.BPDetails;
import com.example.hp.ikurenewedition.earthquakeModel.CardDetails;
import com.example.hp.ikurenewedition.earthquakeModel.DifferentVitals;
import com.example.hp.ikurenewedition.earthquakeModel.EcgListDetail;
import com.example.hp.ikurenewedition.earthquakeModel.PatientDetails;
import com.example.hp.ikurenewedition.earthquakeModel.PresListDetail;
import com.example.hp.ikurenewedition.earthquakeModel.ShowTheEcg;
import com.example.hp.ikurenewedition.earthquakeModel.ShowTheImage;
import com.example.hp.ikurenewedition.earthquakeModel.SugarDetail;
import com.example.hp.ikurenewedition.earthquakeModel.VitalTime;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//import com.example.root.ikure.pojo.earthquakeModel.Earthquake;

/**
 * Created by Gyalpo on 1/4/2018.
 */

public interface ApiInterface {
    @GET("patient/patientlist")
//    @FormUrlEncoded
    Call<CardDetails> getDetails(@Query("card") String format);

    @GET("patient/patientdetails")
    Call<PatientDetails> getDetails2(@Query("pid") String pid);

    @GET("prescription/prescriptionlist")
    Call<PresListDetail> getDetails3(@Query("pid") String pid);

    @GET("prescription/prescriptiondetails")
    Call<ShowTheImage> getDetails4(@Query("id") String pid);

    @GET("ecg/ecglist")
    Call<EcgListDetail> getDetails5(@Query("pid") String pid);

    @GET("ecg/ecgdetails")
    Call<ShowTheEcg> getDetails6(@Query("id") String pid);

    @GET("vital/vitallist")
    Call<VitalTime> getDetails7(@Query("pid") String pid);

    @GET("vital/vitaldetails")
    Call<DifferentVitals> getDetails8(@Query("pid") String pid,
                                      @Query("timestamp") String timestamp);

    @GET("vital/sugarlist")
    Call<SugarDetail> getDetails9(@Query("pid") String pid);

    @GET("vital/bplist")
    Call<BPDetails> getDetails10(@Query("pid") String pid);




}
