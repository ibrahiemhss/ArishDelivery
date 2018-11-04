package com.delivery.arish.arishdelivery.mvp.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.TextView;
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

public class ProfilePresenter {
    private static final String TAG = ProfilePresenter.class.getSimpleName();


    private final Context mCtx;
    private final BaseApiService mApiService;
    private ProgressDialog mLoading;

    public ProfilePresenter(Context context) {
        mCtx = context;
        mApiService = UtilsApi.getAPIService();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getUserInfo(final TextView tvName, final TextView tvEnmail, final TextView tvPhone) {

        mLoading = ProgressDialog.show(mCtx, null, mCtx.getResources().getString(R.string.creating_new), true, false);

        String emailVal
                =SharedPrefManager.getInstance(mCtx).getEmailOfUsers();
        Log.e(TAG, "emailValue_in_profile 4= " + emailVal);

        mApiService.getUserInfo(emailVal)

                // ,SharedPrefManager.getInstance( this ).getDeviceToken())
                .enqueue(new Callback<ResponseBody>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            mLoading.dismiss();
                            try {
                                String remoteResponse = Objects.requireNonNull(response.body()).string();

                                Log.d("JSONString", remoteResponse);

                                JSONObject jsonRESULTS = new JSONObject(remoteResponse);

                                if (jsonRESULTS.getString(Contract.ERROR).equals(Contract.FALSE_VAL)) {
                                    String name = jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.NAME_COL);
                                    String email = jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.EMAIL_COL);
                                    String phone = jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.PHONE_COL);


                                    tvEnmail.setText(email);
                                    tvName.setText(name);
                                    tvPhone.setText(phone);

                                    mLoading.dismiss();
                                } else {
                                    Toast.makeText(mCtx, jsonRESULTS.optString(Contract.ERROR_MSG), Toast.LENGTH_SHORT).show();
                                    mLoading.dismiss();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Log.e("debugJSONS", "onFailure: ERROR > " + t.toString());
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void updateUserInfo(final String pass, final String newName, final String newEmail, final String newPhone,final TextView tvName, final TextView tvEnmail, final TextView tvPhone) {

        mLoading = ProgressDialog.show(mCtx, null, mCtx.getResources().getString(R.string.creating_new), true, false);

        String emailVal
                =SharedPrefManager.
                getInstance(mCtx)
                .getEmailOfUsers();


        mApiService.updateUserInfo(emailVal,pass,newName,newEmail,newPhone,LangUtil.getCurrentLanguage(mCtx))

                // ,SharedPrefManager.getInstance( this ).getDeviceToken())
                .enqueue(new Callback<ResponseBody>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            mLoading.dismiss();
                            try {
                                String remoteResponse = Objects.requireNonNull(response.body()).string();

                                Log.d("JSONStringPrf", remoteResponse);

                                final JSONObject jsonRESULTS = new JSONObject(remoteResponse);

                                if (jsonRESULTS.getString(Contract.ERROR).equals(Contract.FALSE_VAL)) {
                                    Toast.makeText(mCtx, jsonRESULTS.optString(Contract.ERROR_MSG), Toast.LENGTH_SHORT).show();
                                    SharedPrefManager.getInstance(mCtx).saveNamesOfUsers(newName);
                                    SharedPrefManager.getInstance(mCtx).
                                            saveEmailOfUsers(
                                                    newEmail.trim()
                                            );
                                    SharedPrefManager.getInstance(mCtx).savePhonefUsers(newPhone);
                                    getUserInfo(tvName,tvEnmail,tvPhone);


                                    mLoading.dismiss();
                                } else {
                                    Toast.makeText(mCtx, jsonRESULTS.optString(Contract.ERROR_MSG), Toast.LENGTH_SHORT).show();
                                    mLoading.dismiss();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Log.e("debugJSONS", "onFailure: ERROR > " + t.toString());
                    }
                });
    }
}
