package com.delivery.arish.arishdelivery.internet;


import static com.delivery.arish.arishdelivery.data.Contract.BAS_LOGIN;

/**
 * Created by ibrahim on 19/01/18.
 */

public class UtilsApi {
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BAS_LOGIN).create(BaseApiService.class);
    }    }