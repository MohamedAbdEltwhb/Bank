package com.example.mm.bank.data.rest;

import com.example.mm.bank.data.model.blood_type.BloodType;
import com.example.mm.bank.data.model.cities.Cities;
import com.example.mm.bank.data.model.contact.ContactUs;
import com.example.mm.bank.data.model.donation.donation_create_request.DonationCreateRequest;
import com.example.mm.bank.data.model.donation.donation_details.DonationDetails;
import com.example.mm.bank.data.model.donation.donation_requests.DonationRequests;
import com.example.mm.bank.data.model.donation.donation_requests_filter.DonationRequestsFilter;
import com.example.mm.bank.data.model.governorates.Governorates;
import com.example.mm.bank.data.model.notifications.notifications_list.NotificationsList;
import com.example.mm.bank.data.model.notifications.notifications_settings.NotificationsSettings;
import com.example.mm.bank.data.model.posts.post.Posts;
import com.example.mm.bank.data.model.posts.post_favourites_list.PostFavouriteList;
import com.example.mm.bank.data.model.posts.post_filter.PostFilter;
import com.example.mm.bank.data.model.posts.post_toggle_favourite.PostToggleFavourite;
import com.example.mm.bank.data.model.user.profile.edit_profile_data.EditProfileData;
import com.example.mm.bank.data.model.user.login.Login;
import com.example.mm.bank.data.model.user.profile.get_profile_data.GetProfileData;
import com.example.mm.bank.data.model.user.reset_password.newPassword.NewPassword;
import com.example.mm.bank.data.model.posts.posts_details.PostsDetails;
import com.example.mm.bank.data.model.user.regester.Register;
import com.example.mm.bank.data.model.user.reset_password.resetone.ResetPassword;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("blood-types")
    Call<BloodType> getBloodTypes();

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
                                       @Field("city_id") int city_id,
                                       @Field("phone") String phone,
                                       @Field("donation_last_date") String donation_last_date,
                                       @Field("password") String password,
                                       @Field("password_confirmation") String password_confirmation,
                                       @Field("blood_type_id") int blood_type);

    @POST("login")
    @FormUrlEncoded
    Call<Login> doUserLogin(@Field("phone") String phone,
                            @Field("password") String password);

    @POST("profile")
    @FormUrlEncoded
    Call<GetProfileData> getProfileData(@Field("api_token") String apiToken);

    @POST("profile")
    @FormUrlEncoded
    Call<EditProfileData> editProfileData(@Field("name") String name,
                                          @Field("email") String email,
                                          @Field("birth_date") String birth_date,
                                          @Field("city_id") int city_id,
                                          @Field("phone") String phone,
                                          @Field("donation_last_date") String donation_last_date,
                                          @Field("password") String password,
                                          @Field("password_confirmation") String password_confirmation,
                                          @Field("blood_type_id") int blood_type,
                                          @Field("api_token") String apiToken);


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
    Call<Posts> getAllPosts(@Query("api_token") String api_token,
                            @Query("page") int page);

    @GET("post")
    Call<PostsDetails> getPostDetails(@Query("api_token") String apiToken,
                                      @Query("post_id") Integer post_id,
                                      @Query("page") int page);

    @POST("post-toggle-favourite")
    @FormUrlEncoded
    Call<PostToggleFavourite> postToggleFavourite(@Field("post_id") int postId,
                                                  @Field("api_token") String apiToken);

    @GET("my-favourites")
    Call<PostFavouriteList> getFavouritesList(@Query("api_token") String apiToken);

    @GET("posts")
    Call<PostFilter> getPostFilter(@Query("api_token")String api_token,
                                   @Query("page")int page,
                                   @Query("keyword")String keyword,
                                   @Query("category_id")int category_id);


    /**
     * Donation Call
     */
    @GET("donation-requests")
    Call<DonationRequests> donationRequestsCall(@Query("api_token") String apiToken,
                                                @Query("page") int page);

    @GET("donation-requests")
    Call<DonationRequestsFilter> donationRequestsFilterCall(@Query("api_token") String apiToken,
                                                            @Query("blood_type")String blood_type,
                                                            @Query("city_id")String city_id,
                                                            @Query("page") int page);

    @GET("donation-request")
    Call<DonationDetails> getDonationDetails(@Query("api_token") String apiToken,
                                             @Query("donation_id") int donationId);

    @POST("donation-request/create")
    @FormUrlEncoded
    Call<DonationCreateRequest> donationRequestCreate(@Field("api_token") String apiToken,
                                                      @Field("patient_name") String patient_name,
                                                      @Field("patient_age") String patient_age,
                                                      @Field("blood_type_id") int blood_type,
                                                      @Field("bags_num") String bags_num,
                                                      @Field("hospital_name") String hospital_name,
                                                      @Field("hospital_address") String hospital_address,
                                                      @Field("city_id") int city_id,
                                                      @Field("phone") String phone,
                                                      @Field("notes") String notes,
                                                      @Field("latitude") Double latitude,
                                                      @Field("longitude") Double longitude);

    /**
     * Notifications Call
     */
    @GET("notifications")
    Call<NotificationsList> getNotificationsList(@Query("api_token") String apiToken);

    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationsSettings> notificationSettings(@Field("api_token") String apiToken);

    @POST("notifications-settings")
    @FormUrlEncoded
    Call<NotificationsSettings> setNotificationSettings(@Field("api_token") String apiToken);


}
