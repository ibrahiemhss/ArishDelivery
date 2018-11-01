package com.delivery.arish.arishdelivery.mvp.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.data.Contract;
import com.delivery.arish.arishdelivery.internet.BaseApiService;
import com.delivery.arish.arishdelivery.internet.UtilsApi;
import com.delivery.arish.arishdelivery.internet.model.ResponseApiModel;
import com.delivery.arish.arishdelivery.ui.log_in.LogInActivity;
import com.delivery.arish.arishdelivery.ui.log_in.RegisterActivity;
import com.delivery.arish.arishdelivery.util.LangUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter {

    private Context mCtx;
    private BaseApiService mApiService;
    private ProgressDialog mLoading;

    private static final String TAG = RegisterPresenter.class.getSimpleName();

    public RegisterPresenter(Context context) {
        mCtx = context;
        mApiService = UtilsApi.getAPIService();
        mLoading = ProgressDialog.show(mCtx, null, mCtx.getResources().getString(R.string.registring), true, false);
    }



    public void requestRegisterWithPhoto(
            String old_part_img,
             File myfile,
            String nameval,
            String emailval,
            String passval,
            String phoneval) {

        File imagefile = new File(old_part_img);
        try {
            imagefile = new Compressor(mCtx)
                    .setMaxWidth(640)
                    .setMaxHeight(480)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFile(myfile);
        } catch (IOException e) {
            e.printStackTrace();
        }


        RequestBody reqBody = RequestBody.create(MediaType.parse(Contract.MULTIPART_FILE_PATH), imagefile);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData(Contract.PIC_TO_LOAD, imagefile.getName(), reqBody);
        RequestBody name = createPartFromString(nameval);
        RequestBody email = createPartFromString(emailval);
        RequestBody password = createPartFromString(passval);
        RequestBody phone = createPartFromString(phoneval);
        RequestBody lang = createPartFromString(LangUtil.getCurentLanguage(mCtx));


        HashMap<String, RequestBody> map = new HashMap<>();
        map.put(Contract.NAME_COL, name);
        map.put(Contract.EMAIL_COL, email);
        map.put(Contract.PASSWORD_COL, password);
        map.put(Contract.PHONE_COL, phone);
        map.put(Contract.LANG_COL, lang);

        Call<ResponseApiModel> upload = mApiService.uploadImage(map, partImage);
        upload.enqueue(new Callback<ResponseApiModel>() {
            @Override
            public void onResponse(Call<ResponseApiModel> call, final Response<ResponseApiModel> response) {

                Log.d(TAG,"myjson = : " +
                        response.body().toString());

                if (response.body().getEerror().equals(Contract.FALSE_VAL)) {
                    //   mLoading.setMessage(response.body().getError_msg());



                    Log.d(TAG, "server_message : " + response.body().getError_msg().toString()
                                                   +"\n"+response.body().getSuccess_msg().toString());

                    //    mLoading.dismiss();
                    if(response.body().getSuccess_msg().equals(Contract.SUCESS_MSG_VALUE)){
                        Toast.makeText(mCtx,
                                response.body().getError_msg()
                                , Toast.LENGTH_SHORT).show();
                        mCtx.startActivity(new Intent(mCtx, LogInActivity.class));
                    }
                    mLoading.dismiss();


                } else if (response.body().getEerror().equals("true")){
                    Toast.makeText(mCtx,
                            response.body().getError_msg()
                            , Toast.LENGTH_SHORT).show();
                    mLoading.dismiss();


                }
            }

            @Override
            public void onFailure(Call<ResponseApiModel> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
            }
        });

    }


    private RequestBody createPartFromString(String partString) {
        return RequestBody.create(MultipartBody.FORM, partString);
    }




    public void requestRegister(String nameval,
                                String emailval,
                                String passval,
                                String phoneval) {

        mApiService.registerRequest(
                nameval,
                emailval,
                passval,
                phoneval,
                LangUtil.getCurentLanguage(mCtx))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String remoteResponse = response.body().string();
                                JSONObject jsonRESULTS = new JSONObject(remoteResponse);
                                Log.d("JSONString", remoteResponse);

                                if (jsonRESULTS.getString(Contract.ERROR).equals(Contract.FALSE_VAL)) {
                                    mLoading.setMessage(jsonRESULTS.optString(Contract.ERROR_MSG));
                                    Toast.makeText(mCtx, jsonRESULTS.optString(Contract.ERROR_MSG), Toast.LENGTH_SHORT).show();
                                    if(jsonRESULTS.optString(Contract.SUCESS_MSG).equals(Contract.SUCESS_MSG_VALUE)){
                                        mCtx.startActivity(new Intent(mCtx, LogInActivity.class));
                                    }
                                    mLoading.dismiss();

                                } else {
                                    String error_message = jsonRESULTS.optString(Contract.ERROR_MSG);
                                    Toast.makeText(mCtx, error_message, Toast.LENGTH_SHORT).show();
                                    mLoading.setMessage(error_message);
                                    mLoading.dismiss();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        mLoading.dismiss();

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("JSONSdebug", "onFailure: ERROR > " + t.getMessage());
                    }
                });
    }

}
