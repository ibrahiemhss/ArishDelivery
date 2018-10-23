package com.delivery.arish.arishdelivery.internet;


import com.delivery.arish.arishdelivery.internet.model.ResponseApiModel;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;


/**
 * Created by ibrahim on 19/01/18.
 */

public interface BaseApiService {

    @FormUrlEncoded
    @POST("logInUser/loginUser.php")
    Call<ResponseBody> loginRequest(
            @Field("email") String email,
            @Field("password") String password,
            @Field("token") String token);

    @FormUrlEncoded
    @POST("logInDriver/loginDriver.php")
    Call<ResponseBody> loginRequestDriver(
            @Field("email") String email,
            @Field("password") String password,
            @Field("token") String token);

    @FormUrlEncoded
    @POST("logInUser/registerUser.php")
    Call<ResponseBody> registerRequest(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone);

    @Multipart
    @POST("logInUser/registerUserWithImage.php")
    Call<ResponseApiModel> uploadImage(
            @PartMap() Map<String, RequestBody> partMap,
            @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("ResetPassword/")
    Call<ResponseBody> resetPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("InsertData/insert_booking.php")
    Call<ResponseBody> bookRequest(
            @Field("user_id") String user_id,
            @Field("face_id") String face_id,
            @Field("car_id") String car_id,
            @Field("traveTime") String traveTime,
            @Field("start_latitude") String start_latitude,
            @Field("start_longitude") String start_longitude,
            @Field("end_latitude") String end_latitude,
            @Field("end_longtude") String end_longtude,
            @Field("start_location") String start_location,
            @Field("end_location") String end_location,
            @Field("status") String status);

    @FormUrlEncoded
    @POST ("FCM/sendSinglePush.php")
    Call<ResponseBody> sentMessagToDriverRequest(
            @Field("title") String title,
            @Field("from") String from,
            @Field("to") String to,
            @Field("name") String name);
}
