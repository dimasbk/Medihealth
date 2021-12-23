package com.example.medihealth;

import com.example.medihealth.model.Model_tb_dokter;

import java.util.ArrayList;

import com.example.medihealth.model.Model_tb_dokter;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DokterAPI {

    @GET("makanan")
    Call<ArrayList<Model_tb_dokter>> getDokter();

    @FormUrlEncoded
    @POST("makanan/tambah")
    Call<Model_tb_dokter> insertDokterAPI(
            @Field("nama_makanan") String inputNamaMakanan,
            @Field("satuan") String inputSatuan,
            @Field("kalori") Integer inputKalori,
            @Field("protein") Integer inputProtein,
            @Field("lemak") Integer inputLemak
    );

//    @FormUrlEncoded
//    @POST("/makanan/{id}/update")
//    Call<Model_tb_makanan> updatMakananAPI(
//      @Path("id") Integer idMakanan,
//      @Field("nama_makanan") String inputNamaMakanan,
//      @Field("satuan") String inputSatuan,
//      @Field("kalori") Integer inputKalori,
//      @Field("protein") Integer inputProtein,
//      @Field("lemak") Integer inputLemak
//    );

}
