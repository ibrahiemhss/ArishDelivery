package com.delivery.arish.arishdelivery.data;

import android.content.Context;
import android.content.SharedPreferences;

@SuppressWarnings({"ALL", "UnusedReturnValue"})
public class SharedPrefManager {


    private static SharedPrefManager mInstance;
    private static Context mCtx;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        mCtx = context;
        pref = mCtx.getSharedPreferences(Contract.SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);

        }
        return mInstance;
    }


    //TODO==========================USERS SharedPreferences ======================================================
    public boolean saveUserId(String userId) {
        editor = pref.edit();
        editor.putString(Contract.USER_ID_KEY, userId);
        editor.apply();
        editor.apply();
        return true;
    }

    public String getUserId() {

        return pref.getString(Contract.USER_ID_KEY, null);

    }

    public boolean saveNamesOfUsers(String name) {
        editor = pref.edit();
        editor.putString(Contract.NAME_USERS_KEY, name);
        editor.apply();
        editor.apply();
        return true;
    }

    public String getNamesOfUsers() {

        return pref.getString(Contract.NAME_USERS_KEY, null);

    }

    public boolean saveEmailOfUsers(String email) {
        editor = pref.edit();
        editor.putString(Contract.EMAIL_USERS_KEY, email);
        editor.apply();
        editor.apply();
        return true;
    }

    public String getEmailOfUsers() {

        return pref.getString(Contract.EMAIL_USERS_KEY, null);

    }

    public boolean saveImagefUsers(String image) {
        editor = pref.edit();
        editor.putString(Contract.IMAGE_USERS_KEY, image);
        editor.apply();
        editor.apply();
        return true;
    }

    public String getImageOfUsers() {

        return pref.getString(Contract.IMAGE_USERS_KEY, null);

    }

    public boolean savePhonefUsers(String phone) {
        editor = pref.edit();
        editor.putString(Contract.PHONE_USERS_KEY, phone);
        editor.apply();
        editor.apply();
        return true;
    }

    public String getPhoneOfUsers() {

        return pref.getString(Contract.PHONE_USERS_KEY, null);

    }


    public boolean saveDriverId(String userId) {
        editor = pref.edit();
        editor.putString(Contract.USER_ID_KEY, userId);
        editor.apply();
        editor.apply();
        return true;
    }


    public void setLoginUser(boolean isLoggedIn) {
        editor = pref.edit();
        editor.putBoolean(Contract.KEY_IS_USER_LOGGEDIN, isLoggedIn);
        editor.apply();
        editor.commit();

    }


    public boolean isUserLoggedIn() {
        return pref.getBoolean(Contract.KEY_IS_USER_LOGGEDIN, false);

    }


    public void setIsNotAccess(boolean is) {
        editor = pref.edit();
        editor.putBoolean(Contract.KEY_ACCESS, is);
        editor.apply();
        editor.commit();

    }


    public boolean isNotAccess() {
        return pref.getBoolean(Contract.KEY_ACCESS, false);

    }
    //fetch the device token
    public String getDeviceToken() {
        pref = mCtx.getSharedPreferences(Contract.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(Contract.KEY_DEVICE_TOKEN, null);
    }

    public boolean saveDeviceToken(String token) {
        editor = pref.edit();
        editor.putString(Contract.KEY_DEVICE_TOKEN, token);
        editor.apply();
        editor.apply();
        return true;
    }

    public String getCategoryId() {
        pref = mCtx.getSharedPreferences(Contract.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(Contract.KEY_CATEGORY_ID, null);
    }

    public boolean saveCategoryId(String token) {
        editor = pref.edit();
        editor.putString(Contract.KEY_CATEGORY_ID, token);
        editor.apply();
        editor.apply();
        return true;
    }

    public String getRestId() {
        pref = mCtx.getSharedPreferences(Contract.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(Contract.KEY_REST_ID, null);
    }

    public boolean saveRestId(String restaurantId) {
        editor = pref.edit();
        editor.putString(Contract.KEY_REST_ID, restaurantId);
        editor.apply();
        editor.apply();
        return true;
    }
}
