package com.delivery.arish.arishdelivery.internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ibrahim on 23/12/17.
 */

public class CheckInternetConnection {

    boolean connected;
    public boolean CheckInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
        if (connectivityManager.getNetworkInfo( ConnectivityManager.TYPE_MOBILE ).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo( ConnectivityManager.TYPE_WIFI ).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
        }
        return connected;
    }
}
