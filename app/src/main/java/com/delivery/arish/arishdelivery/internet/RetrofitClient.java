package com.delivery.arish.arishdelivery.internet;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ibrahim on 19/01/18.
 */

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl( baseUrl )
                    .addConverterFactory( GsonConverterFactory.create())
                    .build();
        }
            return retrofit;
        }



}