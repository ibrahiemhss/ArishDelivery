package com.delivery.arish.arishdelivery.mvp.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.data.Contract;
import com.delivery.arish.arishdelivery.data.SharedPrefManager;
import com.delivery.arish.arishdelivery.internet.BaseApiService;
import com.delivery.arish.arishdelivery.internet.UtilsApi;
import com.delivery.arish.arishdelivery.ui.Main.MainActivity;
import com.delivery.arish.arishdelivery.util.LangUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LogInPresenter {

    private final Context mCtx;
    private final BaseApiService mApiService;
    private ProgressDialog mLoading;

    public LogInPresenter(Context context) {
        mCtx = context;
        mApiService = UtilsApi.getAPIService();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    /**pass path the file of image as string from MainActivity
     *@param  emailVal string  email come from editText from LogInActivity
     *@param  passVal string  email come from editText from LogInActivity
     *@param  tokenVal string  email come from editText from LogInActivity
     */
    public void requestLogin(String emailVal, String passVal, String tokenVal) {
        //initialize ProgressDialog status message
        mLoading = ProgressDialog.show(mCtx, null, mCtx.getResources().getString(R.string.logging_in), true, false);

        mApiService.loginRequest(emailVal, passVal, tokenVal, LangUtil.getCurrentLanguage(mCtx))

                // ,SharedPrefManager.getInstance( this ).getDeviceToken())
                .enqueue(new Callback<ResponseBody>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            mLoading.dismiss();
                            /*get value from JsonObjects come from server*/

                            try {
                                String remoteResponse = Objects.requireNonNull(response.body()).string();

                                Log.d("JSONString", remoteResponse);

                                JSONObject jsonRESULTS = new JSONObject(remoteResponse);

                                if (jsonRESULTS.getString(Contract.ERROR).equals(Contract.FALSE_VAL)) {


                                    SharedPrefManager.getInstance(mCtx.getApplicationContext()).setLoginUser(true);


                                    Toast.makeText(mCtx, jsonRESULTS.optString(Contract.ERROR_MSG), Toast.LENGTH_SHORT).show();

                                    Log.d("JSONSValues", "id=" + jsonRESULTS.optString(Contract.ID_COL)
                                            + "\nuid=" +
                                            jsonRESULTS.optString(Contract.UID_COL)
                                            + "\nname=" +
                                            jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.NAME_COL)
                                            + "\nemail=" +
                                            jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.EMAIL_COL));


                                    String id = jsonRESULTS.optString(Contract.ID_COL);
                                    @SuppressWarnings("unused") String uid = jsonRESULTS.optString(Contract.UID_COL);
                                    String name = jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.NAME_COL);
                                    String email = jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.EMAIL_COL);
                                    String phone = jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.PHONE_COL);
                                    String imageURl = jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.IMAGE_COL);
                                    //noinspection unused
                                    String created_at = jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.CREATED_AT_COL);

                                    SharedPrefManager.getInstance(mCtx).saveUserId(id);
                                    SharedPrefManager.getInstance(mCtx).saveNamesOfUsers(name);
                                    SharedPrefManager.getInstance(mCtx).saveEmailOfUsers(email);
                                    SharedPrefManager.getInstance(mCtx).savePhonefUsers(phone);
                                    SharedPrefManager.getInstance(mCtx).saveImagefUsers(imageURl);
                                    if (jsonRESULTS.optString(Contract.SUCCESS_MSG).equals(Contract.SUCCESS_MSG_VALUE)) {
                                        Intent intent = new Intent(mCtx, MainActivity.class);
                                        mCtx.startActivity(intent);
                                    }
                                } else {
                                    Toast.makeText(mCtx, jsonRESULTS.optString(Contract.ERROR_MSG), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            mLoading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Log.e("debugJSONS", "onFailure: ERROR > " + t.toString());
                    }
                });
    }
}
