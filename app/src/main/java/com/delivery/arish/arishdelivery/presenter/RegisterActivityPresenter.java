package com.delivery.arish.arishdelivery.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.data.Contract;
import com.delivery.arish.arishdelivery.internet.BaseApiService;
import com.delivery.arish.arishdelivery.internet.UtilsApi;
import com.delivery.arish.arishdelivery.internet.model.ResponseApiModel;
import com.delivery.arish.arishdelivery.ui.log_in.LogInActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivityPresenter {

    private Context mCtx;
    private BaseApiService mApiService;

    private ProgressDialog mLoading;

    public RegisterActivityPresenter(Context context) {

        mCtx = context;
        mApiService = UtilsApi.getAPIService();
        this.mApiService = mApiService;
        mLoading = ProgressDialog.show(mCtx, null, mCtx.getResources().getString(R.string.registring), true, false);

    }

    public void requestRegisterWithPhoto(String part_image,
                                         File file,
                                         String nameval,
                                         String emailval,
                                         String passval,
                                         String phoneval) {


        File imagefile = new File(part_image);

        try {
            imagefile = new Compressor(mCtx)
                    .setMaxWidth(640)
                    .setMaxHeight(480)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFile(file);


        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestBody reqBody = RequestBody.create(MediaType.parse(Contract.MULTIPART_FILE_PATH), imagefile);

        MultipartBody.Part partImage = MultipartBody.Part.createFormData(Contract.PIC_TO_LOAD, imagefile.getName(), reqBody);
        RequestBody name = createPartFromString(nameval);
        RequestBody email = createPartFromString(emailval);
        RequestBody password = createPartFromString(passval);
        RequestBody phone = createPartFromString(phoneval);

        HashMap<String, RequestBody> map = new HashMap<>();
        map.put(Contract.NAME_COL, name);
        map.put(Contract.EMAIL_COL, email);
        map.put(Contract.PASSWORD_COL, password);
        map.put(Contract.PHONE_COL, phone);


        Call<ResponseApiModel> upload = mApiService.uploadImage(map, partImage);
        upload.enqueue(new Callback<ResponseApiModel>() {
            @Override
            public void onResponse(Call<ResponseApiModel> call, final Response<ResponseApiModel> response) {
                if (response.body().getEerror().equals(Contract.FALSE_VAL)) {
                    mLoading.setMessage(response.body().getError_msg());
                    Toast.makeText(mCtx, response.body().getError_msg(), Toast.LENGTH_SHORT).show();
                    mCtx.startActivity(new Intent(mCtx, LogInActivity.class));
                    Log.d("RETRO", "ON SUCCESS : " + response.body().getError_msg());

                } else {
                    Log.d("RETRO", "ON ERRORR : " + response.body().getError_msg());
                    //Toast.makeText(mContext, response.body().getError_msg(), Toast.LENGTH_SHORT).show();
                    mLoading.setMessage(response.body().getError_msg());

                }

                mLoading.dismiss();

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
                phoneval)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Log.i("JSONSdebug", "onResponse:start ");
                            try {
                                String remoteResponse = response.body().string();

                                JSONObject jsonRESULTS = new JSONObject(remoteResponse);
                                Log.d("JSONString", remoteResponse);



                                if (jsonRESULTS.getString(Contract.ERROR).equals(Contract.FALSE_VAL)) {
                                    mLoading.setMessage(jsonRESULTS.getString(Contract.ERROR_MSG));
                                    Toast.makeText(mCtx, jsonRESULTS.getString(Contract.ERROR_MSG), Toast.LENGTH_SHORT).show();

                                    mCtx.startActivity(new Intent(mCtx, LogInActivity.class));
                                    mLoading.dismiss();

                                } else {
                                    String error_message = jsonRESULTS.getString(Contract.ERROR_MSG);
                                    Toast.makeText(mCtx, error_message, Toast.LENGTH_SHORT).show();
                                    mLoading.setMessage(error_message);
                                    mLoading.dismiss();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.i("JSONSdebug", "onResponse: GA BERHASIL");

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
