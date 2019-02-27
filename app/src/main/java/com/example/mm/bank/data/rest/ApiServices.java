package com.example.mm.bank.data.rest;

import com.example.mm.bank.data.model.cities.Cities;
import com.example.mm.bank.data.model.governorates.Governorates;
import com.example.mm.bank.data.model.login.Login;
import com.example.mm.bank.data.model.newPassword.NewPassword;
import com.example.mm.bank.data.model.posts.Posts;
import com.example.mm.bank.data.model.regester.Register;
import com.example.mm.bank.data.model.resetone.ResetPassword;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("governorates")
    Call<Governorates> getGovernments();

    @GET("cities")
    Call<Cities> getCities(@Query("governorate_id") String government_id);

    @POST("register")
    @FormUrlEncoded
    Call<Register> addUserRegistration(@Field("name") String name,
                                       @Field("email") String email,
                                       @Field("birth_date") String birth_date,
                                       @Field("city_id") String city_id,
                                       @Field("phone") String phone,
                                       @Field("donation_last_date") String donation_last_date,
                                       @Field("password") String password,
                                       @Field("password_confirmation") String password_confirmation,
                                       @Field("blood_type") String blood_type);

    @POST("login")
    @FormUrlEncoded
    Call<Register> doUserLogin(@Field("phone") String phone,
                               @Field("password") String password);

    @POST("reset-password")
    @FormUrlEncoded
    Call<ResetPassword> resetPasswordStep1(@Field("phone") String phone);

    @POST("new-password")
    @FormUrlEncoded
    Call<NewPassword> addNewPassword(@Field("password") String password,
                                     @Field("password_confirmation") String passwordConfirmation,
                                     @Field("pin_code") String pinCode,
                                     @Field("phone") String phone);

    @GET("posts")
    Call<Posts> getPosts(@Query("api_token") String api_token,
                         @Query("page") int page);


}
