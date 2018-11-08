package com.delivery.arish.arishdelivery.internet;


import com.delivery.arish.arishdelivery.data.Contract;
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
 * Class uses Queries  the first part of the Internet address
 **named BASE_URL that found in {@linkplain com.delivery.arish.arishdelivery.data.Contract}
 **to go to {@linkplain com.delivery.arish.arishdelivery.internet.RetrofitClient}
 **here tare these Urls here to build final second part of urls here for every request of url wanted
 * */
public interface BaseApiService {

    @FormUrlEncoded
    @POST(Contract.LOGIN_URL)
    Call<ResponseBody> loginRequest(
            @Field(Contract.EMAIL_COL) String email,
            @Field(Contract.PASSWORD_COL) String password,
            @Field(Contract.TOKEN_COL) String token,
            @Field(Contract.LANG_COL) String lang);


    @FormUrlEncoded
    @POST(Contract.REGISTER_URL)
    Call<ResponseBody> registerRequest(
            @Field(Contract.NAME_COL) String name,
            @Field(Contract.EMAIL_COL) String email,
            @Field(Contract.PASSWORD_COL) String password,
            @Field(Contract.PHONE_COL) String phone,
            @Field(Contract.LANG_COL) String lang);


    @Multipart
    @POST(Contract.REGISTER_WITH_IMAGE_URL)
    Call<ResponseApiModel> uploadImage(
            @PartMap() Map<String, RequestBody> partMap,
            @Part MultipartBody.Part image);


    @FormUrlEncoded
    @POST(Contract.FORGET_PASSWORD_URL)
    Call<ResponseBody> sendCodeToMail(
            @Field(Contract.EMAIL_COL) String email,
            @Field(Contract.LANG_COL) String lang);

    @FormUrlEncoded
    @POST(Contract.UPDATE_PASSWORD_URL)
    Call<ResponseBody> updatePass(
            @Field(Contract.EMAIL_COL) String email,
            @Field(Contract.PASSWORD_COL) String password,
            @Field(Contract.CODE_COL) String phone,
            @Field(Contract.LANG_COL) String lang);

    @FormUrlEncoded
    @POST(Contract.PROFILE_INFO_URL)
    Call<ResponseBody> getUserInfo(
            @Field(Contract.EMAIL_COL) String email,
            @Field(Contract.LANG_COL) String lang);

    @FormUrlEncoded
    @POST(Contract.UPDATE_INFO_URL)
    Call<ResponseBody> updateUserInfo(
           @Field(Contract.EMAIL_COL) String email,
           @Field(Contract.PASSWORD_COL) String password,
           @Field(Contract.NEW_NAME_COL) String new_name,
           @Field(Contract.NEW_EMAIL_COL) String new_mail,
           @Field(Contract.NEW_PHONE_COL) String new_phone,
           @Field(Contract.LANG_COL) String lang);

    @Multipart
    @POST(Contract.UPDATE_INFO_WITH_IMAGE_URL)
    Call<ResponseApiModel> updateUserInfoWithImg(
            @PartMap() Map<String, RequestBody> partMap,
            @Part MultipartBody.Part image);






}
