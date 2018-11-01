package com.delivery.arish.arishdelivery.mvp.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.data.Contract;
import com.delivery.arish.arishdelivery.data.SharedPrefManager;
import com.delivery.arish.arishdelivery.internet.BaseApiService;
import com.delivery.arish.arishdelivery.internet.UtilsApi;
import com.delivery.arish.arishdelivery.ui.Main.MainActivity;
import com.delivery.arish.arishdelivery.ui.log_in.resetPassword.FragmentUpdatePassowrd;
import com.delivery.arish.arishdelivery.util.LangUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordPresenter {

    private Context mCtx;
    private BaseApiService mApiService;
    private ProgressDialog mLoading;
    public boolean isNotSuccess=true;
    public ResetPasswordPresenter(Context context) {
        mCtx = context;
        mApiService = UtilsApi.getAPIService();
        mLoading = ProgressDialog.show(mCtx, null,  mCtx.getResources().getString( R.string.registring), true, false);
    }

    public void requestResetPass(final String emailVal){


        mApiService.sendCodeToMail( emailVal, LangUtil.getCurentLanguage(mCtx))

                // ,SharedPrefManager.getInstance( this ).getDeviceToken())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            mLoading.dismiss();
                            try {
                                String remoteResponse=response.body().string();


                                JSONObject jsonRESULTS = new JSONObject(remoteResponse);

                                if (jsonRESULTS.getString(Contract.ERROR).equals(Contract.FALSE_VAL)){


                                    Toast.makeText(mCtx, jsonRESULTS.optString(Contract.ERROR_MSG), Toast.LENGTH_SHORT).show();

                                    if(jsonRESULTS.optString(Contract.SUCESS_MSG).equals(Contract.SUCESS_MSG_VALUE)){
                                        Log.d("checkValue", jsonRESULTS.optString(Contract.SUCESS_MSG));

                                        Fragment fragmentUpdatePassowrd = new FragmentUpdatePassowrd();
                                        FragmentTransaction transaction = ((FragmentActivity)mCtx).
                                                getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.contents_containerdddddd, fragmentUpdatePassowrd);
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
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debugJSONS", "onFailure: ERROR > " + t.toString());
                    }
                });
    }
}
