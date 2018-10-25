package com.delivery.arish.arishdelivery.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.data.Contract;
import com.delivery.arish.arishdelivery.data.SharedPrefManager;
import com.delivery.arish.arishdelivery.internet.BaseApiService;
import com.delivery.arish.arishdelivery.internet.UtilsApi;
import com.delivery.arish.arishdelivery.ui.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LogInActivityPresenter {

    private Context mCtx;
    private BaseApiService mApiService;
    private ProgressDialog mLoading;
    public LogInActivityPresenter(Context context) {
        mCtx = context;
        mApiService = UtilsApi.getAPIService();
        mLoading = ProgressDialog.show(mCtx, null,  mCtx.getResources().getString( R.string.registring), true, false);
    }

 public void requestLogin(String emailVal,String passVal,String tokenVal){


        mApiService.loginRequest( emailVal,passVal,tokenVal)

                // ,SharedPrefManager.getInstance( this ).getDeviceToken())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            mLoading.dismiss();
                            try {
                                String remoteResponse=response.body().string();

                                Log.d("JSONString", remoteResponse);

                                JSONObject jsonRESULTS = new JSONObject(remoteResponse);

                                if (jsonRESULTS.getString(Contract.ERROR).equals(Contract.FALSE_VAL)){


                                    SharedPrefManager.getInstance( mCtx.getApplicationContext() ).setLoginUser(true);
                                    Toast.makeText(mCtx, jsonRESULTS.getString(Contract.ERROR_MSG), Toast.LENGTH_SHORT).show();
                                    String id = jsonRESULTS.getString(Contract.ID_COL);
                                    String uid = jsonRESULTS.getString(Contract.UID_COL);
                                    String name = jsonRESULTS.getJSONObject(Contract.USER_COL).getString(Contract.NAME_COL);
                                    String email = jsonRESULTS.getJSONObject(Contract.USER_COL).getString(Contract.EMAIL_COL);
                                    String phone = jsonRESULTS.getJSONObject(Contract.USER_COL).getString(Contract.PHONE_COL);
                                    String imageURl = jsonRESULTS.getJSONObject(Contract.USER_COL).getString(Contract.IMAGE_COL);
                                    String created_at = jsonRESULTS.getJSONObject(Contract.USER_COL).getString(Contract.CREATED_AT_COL);
                                    String error_message = jsonRESULTS.getString(Contract.ERROR_MSG);

                                    Log.i("tagconvertstr", "["+ jsonRESULTS.getString(Contract.ERROR_MSG)+"]");


                                    Log.e("debugJSONS", "succeess: ERROR > "+error_message );
                                    SharedPrefManager.getInstance( mCtx.getApplicationContext() ).saveUserId( id );
                                    SharedPrefManager.getInstance( mCtx.getApplicationContext() ).saveNamesOfUsers( name );
                                    SharedPrefManager.getInstance( mCtx.getApplicationContext() ).saveEmailOfUsers( email );
                                    SharedPrefManager.getInstance( mCtx.getApplicationContext() ).savePhonefUsers( phone );
                                    SharedPrefManager.getInstance( mCtx.getApplicationContext() ).saveImagefUsers( imageURl );
                                        Intent intent=new Intent(mCtx, MainActivity.class);
                                        mCtx.startActivity(intent);



                                } else {
                                    // Jika login gagal
                                    Log.e("debugJSONS", "onFailure: ERROR > " );
                                    String error_message = jsonRESULTS.getString("error_msg");

                                    Log.e("debugJSONS", "noAcount: ERROR > "+error_message );

                                    Toast.makeText(mCtx, error_message, Toast.LENGTH_SHORT).show();
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
