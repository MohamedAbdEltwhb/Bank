package com.example.mm.bank.data.rest;

import com.example.mm.bank.data.model.cities.Cities;
import com.example.mm.bank.data.model.governorates.Governorates;
import com.example.mm.bank.data.model.register.Register;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("governorates")
    Call<Governorates> getGovernments();

    @GET("cities")
    Call<Cities> getCities(@Query("governorate_id") int government_id);

    @POST("register")
    @FormUrlEncoded
    Call<Register> addUserRegisteration (@Field("name") String Register,
                                         @Field("email") String email,
                                         @Field("birth_date") String birth_date,
                                         @Field("city_id") String city_id,
                                         @Field("phone") String phone,
                                         @Field("donation_last_date") String donation_last_date,
                                         @Field("password") String password,
                                         @Field("password_confirmation") String password_confirmation,
                                         @Field("blood_type") String blood_type);


//    @GET("governorates")
//    Call<> getGovernorates ();
//
//    @GET("cities")
//    Call<> getCities (@Query("governorate_id") int governorate_id);
//
//    @POST("report")
//    @FormUrlEncoded
//    Call<> addReport (@Field("api_token") String api_token,
//                      @Field("massage") String massage);
//
//    @POST("report")
//    @Multipart
//    Call<> addReport (@Part("api_token") RequestBody api_token,
//                      @Part MultipartBody.Part massage);
}
