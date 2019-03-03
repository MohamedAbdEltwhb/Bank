package com.example.mm.bank.data.rest;

import com.example.mm.bank.data.model.cities.Cities;
import com.example.mm.bank.data.model.contact.ContactUs;
import com.example.mm.bank.data.model.donation.donation_create_request.DonationCreateRequest;
import com.example.mm.bank.data.model.donation.donation_details.DonationDetails;
import com.example.mm.bank.data.model.donation.donation_requests.DonationRequests;
import com.example.mm.bank.data.model.favourite_posts.FavouritePosts;
import com.example.mm.bank.data.model.favourites_list.FavouritesList;
import com.example.mm.bank.data.model.governorates.Governorates;
import com.example.mm.bank.data.model.notifications.get_notifications_list.GetNotificationsList;
import com.example.mm.bank.data.model.notifications.notifications_settings.NotificationsSettings;
import com.example.mm.bank.data.model.user.get_profile_data.GetProfileData;
import com.example.mm.bank.data.model.user.newPassword.NewPassword;
import com.example.mm.bank.data.model.posts.Posts;
import com.example.mm.bank.data.model.posts_details.PostsDetails;
import com.example.mm.bank.data.model.user.regester.Register;
import com.example.mm.bank.data.model.user.resetone.ResetPassword;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {

    /**
     * Governments Call
     */
    @GET("governorates")
    Call<Governorates> getGovernments();

    /**
     * Cities Call
     */
    @GET("cities")
    Call<Cities> getCities(@Query("governorate_id") String government_id);

    /**
     * Contact Us Call
     */
    @POST("contact")
    @FormUrlEncoded
    Call<ContactUs> contactUs(@Field("api_token") String apiToken,
                              @Field("title") String title,
                              @Field("message") String message);

    /**
     * User Call
     */
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

//    @POST("profile")
//    @FormUrlEncoded
//    Call<GetProfileData> getProfileData(@Field("api_token") String apiToken);

//    @POST("profile")
//    @FormUrlEncoded
//    Call<> editProfileData(@Field("name") String name,
//                           @Field("email") String email,
//                           @Field("birth_date") String birth_date,
//                           @Field("city_id") String city_id,
//                           @Field("phone") String phone,
//                           @Field("donation_last_date") String donation_last_date,
//                           @Field("password") String password,
//                           @Field("password_confirmation") String password_confirmation,
//                           @Field("blood_type") String blood_type,
//                           @Field("api_token") String apiToken);

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

    /**
     * Posts Call
     */
    @GET("posts")
    Call<Posts> getPosts(@Query("api_token") String api_token,
                         @Query("page") int page);

    @GET("post")
    Call<PostsDetails> getPostDetails(@Query("api_token") String apiToken,
                                      @Query("post_id") Integer post_id);

    @GET("my-favourites")
    Call<FavouritesList> getFavouritesList(@Query("api_token") String apiToken);

    @POST("post-toggle-favourite")
    @FormUrlEncoded
    Call<FavouritePosts> postToggleFavourite(@Field("post_id") int postId,
                                             @Field("api_token") String apiToken);

    /**
     * Donation Call
     */
    @GET("donation-requests")
    Call<DonationRequests> donationRequestsCall(@Query("api_token") String apiToken,
                                                @Query("blood_type") String bloodType,
                                                @Query("city_id") String cityId);

    @GET("donation-request")
    Call<DonationDetails> getDonationDetails(@Query("api_token") String apiToken,
                                             @Query("donation_id") String donationId);

    @POST("donation-request/create")
    @FormUrlEncoded
    Call<DonationCreateRequest> donationRequestCreate(@Field("api_token") String apiToken,
                                                      @Field("patient_name") String patient_name,
                                                      @Field("patient_age") String patient_age,
                                                      @Field("blood_type") String blood_type,
                                                      @Field("bags_num") String bags_num,
                                                      @Field("hospital_name") String hospital_name,
                                                      @Field("hospital_address") String hospital_address,
                                                      @Field("city_id") String city_id,
                                                      @Field("phone") String phone,
                                                      @Field("notes") String notes,
                                                      @Field("latitude") Double latitude,
                                                      @Field("longitude") Double longitude);

    /**
     * Notifications Call
     */
    @GET("notifications")
    Call<GetNotificationsList> getNotificationsList(@Query("api_token") String apiToken);

    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationsSettings> notificationSettings(@Field("api_token") String apiToken);

    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationsSettings> setNotificationSettings(@Field("api_token") String apiToken);


}
