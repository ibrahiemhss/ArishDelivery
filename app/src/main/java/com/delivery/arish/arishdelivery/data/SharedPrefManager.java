package com.delivery.arish.arishdelivery.data;

import android.content.Context;
import android.content.SharedPreferences;

@SuppressWarnings({"ALL", "UnusedReturnValue"})
public class SharedPrefManager {
    private static final String USER_ID_KEY = "user_id";
    private static final String NAME_USERS_KEY = "name_users";
    private static final String EMAIL_USERS_KEY = "email_users";
    private static final String PHONE_USERS_KEY = "phone_users";
    private static final String IMAGE_USERS_KEY = "image_users";
    private static final String SHARED_PREF_NAME = "save_contents";
    private static final String KEY_IS_USER_LOGGEDIN = "isUserLoggedIn";
    private static final String TAG_TOKEN = "tagtoken";
    private static final String KEY_CCESS = "key_access";

    private static SharedPrefManager mInstance;
    private static Context mCtx;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        mCtx = context;
        pref = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
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
        editor.putString(USER_ID_KEY, userId);
        editor.apply();
        editor.apply();
        return true;
    }

    public String getUserId() {

        return pref.getString(USER_ID_KEY, null);

    }

    public boolean saveNamesOfUsers(String name) {
        editor = pref.edit();
        editor.putString(NAME_USERS_KEY, name);
        editor.apply();
        editor.apply();
        return true;
    }

    public String getNamesOfUsers() {

        return pref.getString(NAME_USERS_KEY, null);

    }

    public boolean saveEmailOfUsers(String email) {
        editor = pref.edit();
        editor.putString(EMAIL_USERS_KEY, email);
        editor.apply();
        editor.apply();
        return true;
    }

    public String getEmailOfUsers() {

        return pref.getString(EMAIL_USERS_KEY, null);

    }

    public boolean saveImagefUsers(String image) {
        editor = pref.edit();
        editor.putString(IMAGE_USERS_KEY, image);
        editor.apply();
        editor.apply();
        return true;
    }

    public String getImageOfUsers() {

        return pref.getString(IMAGE_USERS_KEY, null);

    }

    public boolean savePhonefUsers(String phone) {
        editor = pref.edit();
        editor.putString(PHONE_USERS_KEY, phone);
        editor.apply();
        editor.apply();
        return true;
    }

    public String getPhoneOfUsers() {

        return pref.getString(PHONE_USERS_KEY, null);

    }


    public boolean saveDriverId(String userId) {
        editor = pref.edit();
        editor.putString(USER_ID_KEY, userId);
        editor.apply();
        editor.apply();
        return true;
    }


    public void setLoginUser(boolean isLoggedIn) {
        editor = pref.edit();
        editor.putBoolean(KEY_IS_USER_LOGGEDIN, isLoggedIn);
        editor.apply();
        editor.commit();

    }


    public boolean isUserLoggedIn() {
        return pref.getBoolean(KEY_IS_USER_LOGGEDIN, false);

    }


    public void setIsNotAccess(boolean is) {
        editor = pref.edit();
        editor.putBoolean(KEY_CCESS, is);
        editor.apply();
        editor.commit();

    }


    public boolean isNotAccess() {
        return pref.getBoolean(KEY_CCESS, false);

    }
    //fetch the device token
    public String getDeviceToken() {
        pref = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(TAG_TOKEN, null);
    }
}
