package com.delivery.arish.arishdelivery.data;

public class Contract {


    public static final String BAS_URL = "https://ibrhimhssss.000webhostapp.com/dvr/";// base url the first part of main url

    /*all the second parts of url will used to fetch data */
    public static final String LOGIN_URL = "login/loginUser.php";
    public static final String REGISTER_URL = "login/registerUser.php";
    public static final String REGISTER_WITH_IMAGE_URL = "login/registerUserWithImage.php";
    public static final String FORGET_PASSWORD_URL = "login/forgot-password.php";
    public static final String UPDATE_PASSWORD_URL = "login/update-password.php";
    public static final String PROFILE_INFO_URL = "login/getUserInfo.php";
    public static final String UPDATE_INFO_URL = "login/updateUserInfo.php";
    public static final String UPDATE_INFO_WITH_IMAGE_URL = "login/updateUserInfoWithImage.php";
    @SuppressWarnings("unused")
    public static final String GET_IMAGE_USER_URL = "login/get_image_user.php";


    /*this json fields come from server as warning message*/
    public static final String ERROR = "error";//JSONObject error
    public static final String FALSE_VAL = "false";//value of error_msg JSONObject
    public static final String ERROR_MSG = "error_msg";//JSONObject error
    public static final String SUCCESS_MSG = "success_msg";//JSONObject error
    public static final String SUCCESS_MSG_VALUE = "success";//value of success_msg JSONObject

    public static final String FCM_DATA = "data";//firebase JSONObject data
    public static final String FCM_TITLE = "title";//firebase notification title inside JSONObject
    public static final String FCM_MESSAGE = "message";//firebase notification message inside JSONObject

    @SuppressWarnings("unused")
    public static final String IMG_MSG = "photo_msg";


    /**this ic column of fields in database
     *server and will used in local app database it and used in requests in retrofit class
     * {@linkplain com.delivery.arish.arishdelivery.internet.BaseApiService}
     * */
    public static final String ID_COL = "id";
    public static final String UID_COL = "uid";
    public static final String USER_COL = "user";
    public static final String NAME_COL = "name";
    public static final String EMAIL_COL = "email";
    public static final String PASSWORD_COL = "password";
    public static final String IMAGE_COL = "image";
    public static final String PHONE_COL = "phone";
    public static final String CREATED_AT_COL = "created_at";
    public static final String TOKEN_COL = "token";
    public static final String CODE_COL = "lost";
    public static final String LANG_COL = "lang";
    public static final String NEW_NAME_COL = "nname";
    public static final String NEW_EMAIL_COL = "nemail";
    public static final String NEW_PHONE_COL = "nphone";
    public static final String PIC_TO_LOAD = "pic";
    public static final String MULTIPART_FILE_PATH = "multipart/form-file";


    /*this final static strings as a value of extra strings passes with bundles
     *to save status of app or sharedpreferences etce..... */
    public static final String EXTRA_MAIN_LIST_POSITION = "main_list_position";//used to save value of position of listview when inter details class
    public static final String EXTRA_DETAILS_LIST_POSITION = "details_list_position";//not used yet
    public static final String EXTRA_INTER_ACTIVITY = "inter_activity";
    public static final String EXTRA_DETAILS_LIST = "details_list";
    /**sharedpreferences keys
     ** {@linkplain com.delivery.arish.arishdelivery.data.SharedPrefManager}*/
    public static final String USER_ID_KEY = "user_id";//key used to save user id in bundle of sharedpreferences
    public static final String NAME_USERS_KEY = "name_users";//key used to save user name in bundle of sharedpreferences
    public static final String EMAIL_USERS_KEY = "email_users";//key used to save email in bundle of  sharedpreferences
    public static final String PHONE_USERS_KEY = "phone_users";//key used to save user phone in bundle of sharedpreferences
    public static final String IMAGE_USERS_KEY = "image_users";//key used to save image in bundle of sharedpreferences
    public static final String SHARED_PREF_NAME = "save_contents";//key main key of  of sharedpreferences
    public static final String KEY_IS_USER_LOGGEDIN = "isUserLoggedIn";//key used to save boolean of log in status in bundle of sharedpreferences
    public static final String KEY_DEVICE_TOKEN = "devic_token";//used to save device token in bundle of sharedpreferences
    public static final String KEY_ACCESS = "key_access";//used to save ............


}
