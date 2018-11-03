package com.delivery.arish.arishdelivery.mvp.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.data.Contract;
import com.delivery.arish.arishdelivery.internet.BaseApiService;
import com.delivery.arish.arishdelivery.internet.UtilsApi;
import com.delivery.arish.arishdelivery.ui.log_in.LogInActivity;
import com.delivery.arish.arishdelivery.ui.log_in.resetPassword.FragmentUpdatePassoword;
import com.delivery.arish.arishdelivery.util.LangUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordPresenter {

    private final Context mCtx;
    private final BaseApiService mApiService;
    private ProgressDialog mLoading;

    public ResetPasswordPresenter(Context context) {
        mCtx = context;
        mApiService = UtilsApi.getAPIService();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void requestResetPass(final String emailVal){

        mLoading = ProgressDialog.show(mCtx, null,  mCtx.getResources().getString( R.string.creating_new), true, false);

        mApiService.sendCodeToMail( emailVal, LangUtil.getCurrentLanguage(mCtx))

                // ,SharedPrefManager.getInstance( this ).getDeviceToken())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            mLoading.dismiss();
                            try {
                                String remoteResponse= null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                                    remoteResponse = Objects.requireNonNull(response.body()).string();
                                }


                                JSONObject jsonRESULTS = new JSONObject(remoteResponse);

                                if (jsonRESULTS.getString(Contract.ERROR).equals(Contract.FALSE_VAL)){


                                    Toast.makeText(mCtx, jsonRESULTS.optString(Contract.ERROR_MSG), Toast.LENGTH_SHORT).show();

                                    if(jsonRESULTS.optString(Contract.SUCCESS_MSG).equals(Contract.SUCCESS_MSG_VALUE)){
                                        Log.d("checkValue", jsonRESULTS.optString(Contract.SUCCESS_MSG));

                                        Fragment fragmentUpdatePassword = new FragmentUpdatePassoword();
                                        FragmentTransaction transaction = ((FragmentActivity)mCtx).
                                                getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.contents_container, fragmentUpdatePassword);
                                        transaction.addToBackStack(null);
                                        transaction.commit();

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void requestUpdateWithCode(String email, String newPass, String code){

        mLoading = ProgressDialog.show(mCtx, null,  mCtx.getResources().getString( R.string.creating_new), true, false);

        mApiService.updatePass( email, newPass, code,LangUtil.getCurrentLanguage(mCtx))

                // ,SharedPrefManager.getInstance( this ).getDeviceToken())
                .enqueue(new Callback<ResponseBody>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        String remoteResponse= null;
                        try {
                            remoteResponse = Objects.requireNonNull(response.body()).string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        if (response.isSuccessful()){
                            mLoading.dismiss();
                            try {


                                JSONObject jsonRESULTS = new JSONObject(remoteResponse);

                                Log.d("JSONString", remoteResponse);

                                if (jsonRESULTS.getString(Contract.ERROR).equals(Contract.FALSE_VAL)){


                                    Toast.makeText(mCtx, jsonRESULTS.optString(Contract.ERROR_MSG), Toast.LENGTH_SHORT).show();

                                    if(jsonRESULTS.optString(Contract.SUCCESS_MSG).equals(Contract.SUCCESS_MSG_VALUE)){
                                        Log.d("JSONScheckValue", jsonRESULTS.optString(Contract.SUCCESS_MSG));

                                        mCtx.startActivity(new Intent(mCtx, LogInActivity.class));


                                    }
                                } else {
                                    Toast.makeText(mCtx, jsonRESULTS.optString(Contract.ERROR_MSG), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
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
