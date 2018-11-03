package com.delivery.arish.arishdelivery.mvp.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.data.Contract;
import com.delivery.arish.arishdelivery.internet.BaseApiService;
import com.delivery.arish.arishdelivery.internet.UtilsApi;
import com.delivery.arish.arishdelivery.internet.model.ResponseApiModel;
import com.delivery.arish.arishdelivery.ui.log_in.LogInActivity;
import com.delivery.arish.arishdelivery.util.LangUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPresenter {

    private final Context mCtx;
    private final BaseApiService mApiService;
    private ProgressDialog mLoading;

    private static final String TAG = RegisterPresenter.class.getSimpleName();

    public RegisterPresenter(Context context) {
        mCtx = context;
        mApiService = UtilsApi.getAPIService();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void requestRegisterWithPhoto(
            String old_part_img,
            File myfile,
            String nameval,
            String emailval,
            String passval,
            String phoneval) {
        mLoading = ProgressDialog.show(mCtx, null, mCtx.getResources().getString(R.string.creating_new), true, false);


        File imagefile = new File(old_part_img);
        if (myfile != null) {
            try {
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
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }


        RequestBody reqBody = RequestBody.create(MediaType.parse(Contract.MULTIPART_FILE_PATH), imagefile);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData(Contract.PIC_TO_LOAD, imagefile.getName(), reqBody);
        RequestBody name = createPartFromString(nameval);
        RequestBody email = createPartFromString(emailval);
        RequestBody password = createPartFromString(passval);
        RequestBody phone = createPartFromString(phoneval);
        RequestBody lang = createPartFromString(LangUtil.getCurrentLanguage(mCtx));


        HashMap<String, RequestBody> map = new HashMap<>();
        map.put(Contract.NAME_COL, name);
        map.put(Contract.EMAIL_COL, email);
        map.put(Contract.PASSWORD_COL, password);
        map.put(Contract.PHONE_COL, phone);
        map.put(Contract.LANG_COL, lang);

        Call<ResponseApiModel> upload = mApiService.uploadImage(map, partImage);
        upload.enqueue(new Callback<ResponseApiModel>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<ResponseApiModel> call, @NonNull final Response<ResponseApiModel> response) {

                Log.d(TAG, "myjson = : " +
                        Objects.requireNonNull(response.body()).toString());

                if (Objects.requireNonNull(response.body()).getError().equals(Contract.FALSE_VAL)) {
                    //   mLoading.setMessage(response.body().getError_msg());


                    Log.d(TAG, "server_message : " + Objects.requireNonNull(response.body()).getError_msg()
                            + "\n" + Objects.requireNonNull(response.body()).getSuccess_msg());

                    //    mLoading.dismiss();
                    if (Objects.requireNonNull(response.body()).getSuccess_msg().equals(Contract.SUCCESS_MSG_VALUE)) {
                        Toast.makeText(mCtx,
                                Objects.requireNonNull(response.body()).getError_msg()
                                , Toast.LENGTH_SHORT).show();
                        mCtx.startActivity(new Intent(mCtx, LogInActivity.class));
                    }
                    mLoading.dismiss();


                } else if (Objects.requireNonNull(response.body()).getError().equals("true")) {
                    Toast.makeText(mCtx,
                            Objects.requireNonNull(response.body()).getError_msg()
                            , Toast.LENGTH_SHORT).show();
                    mLoading.dismiss();


                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseApiModel> call, @NonNull Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
            }
        });

    }


    private RequestBody createPartFromString(String partString) {
        return RequestBody.create(MultipartBody.FORM, partString);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void requestRegister(String nameval,
                                String emailval,
                                String passval,
                                String phoneval) {

        mLoading = ProgressDialog.show(mCtx, null, mCtx.getResources().getString(R.string.creating_new), true, false);

        mApiService.registerRequest(
                nameval,
                emailval,
                passval,
                phoneval,
                LangUtil.getCurrentLanguage(mCtx))
                .enqueue(new Callback<ResponseBody>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String remoteResponse = Objects.requireNonNull(response.body()).string();
                                JSONObject jsonRESULTS = new JSONObject(remoteResponse);
                                Log.d("JSONString", remoteResponse);

                                if (jsonRESULTS.getString(Contract.ERROR).equals(Contract.FALSE_VAL)) {
                                    mLoading.setMessage(jsonRESULTS.optString(Contract.ERROR_MSG));
                                    Toast.makeText(mCtx, jsonRESULTS.optString(Contract.ERROR_MSG), Toast.LENGTH_SHORT).show();
                                    if (jsonRESULTS.optString(Contract.SUCCESS_MSG).equals(Contract.SUCCESS_MSG_VALUE)) {
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
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Log.e("JSONSdebug", "onFailure: ERROR > " + t.getMessage());
                    }
                });
    }

}
