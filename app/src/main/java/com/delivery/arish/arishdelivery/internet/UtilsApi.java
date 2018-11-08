package com.delivery.arish.arishdelivery.internet;


import static com.delivery.arish.arishdelivery.data.Contract.BAS_URL;

/**class just pass first part of Url
 **named BASE_URL that found in {@linkplain com.delivery.arish.arishdelivery.data.Contract}
 * to create RetrofitClient with all second url wanted in BaseApiService
 */

public class UtilsApi {
    public static BaseApiService getAPIService() {
        return RetrofitClient.getClient(BAS_URL).create(BaseApiService.class);
    }
}