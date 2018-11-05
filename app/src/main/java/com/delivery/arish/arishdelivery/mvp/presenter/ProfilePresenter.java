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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.data.Contract;
import com.delivery.arish.arishdelivery.data.SharedPrefManager;
import com.delivery.arish.arishdelivery.internet.BaseApiService;
import com.delivery.arish.arishdelivery.internet.UtilsApi;
import com.delivery.arish.arishdelivery.internet.model.ResponseApiModel;
import com.delivery.arish.arishdelivery.ui.Main.MainActivity;
import com.delivery.arish.arishdelivery.ui.log_in.LogInActivity;
import com.delivery.arish.arishdelivery.util.DynamicHeightNetworkImageView;
import com.delivery.arish.arishdelivery.util.ImageLoaderHelper;
import com.delivery.arish.arishdelivery.util.LangUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    public void getUserInfo(final TextView tvName, final TextView tvEnmail, final TextView tvPhone,final CircleImageView circlImg) {

        mLoading = ProgressDialog.show(mCtx, null, mCtx.getResources().getString(R.string.creating_new), true, false);

        String emailVal
                =SharedPrefManager.getInstance(mCtx).getEmailOfUsers();
        Log.e(TAG, "emailValue_in_profile 4= " + emailVal);

        mApiService.getUserInfo(emailVal,LangUtil.getCurrentLanguage(mCtx))

                // ,SharedPrefManager.getInstance( this ).getDeviceToken())
                .enqueue(new Callback<ResponseBody>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            mLoading.dismiss();
                            try {
                                String remoteResponse = Objects.requireNonNull(response.body()).string();

                                Log.d("JSONStringGetInfo", remoteResponse);

                                JSONObject jsonRESULTS = new JSONObject(remoteResponse);

                                if (jsonRESULTS.optString(Contract.ERROR).equals(Contract.FALSE_VAL)) {
                                    mLoading.dismiss();

                                    String name = jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.NAME_COL);
                                    String email = jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.EMAIL_COL);
                                    String phone = jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.PHONE_COL);
                                    String imgUrl=jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.IMAGE_COL);
                                    SharedPrefManager.getInstance(mCtx).saveNamesOfUsers(name);
                                    SharedPrefManager.getInstance(mCtx).saveEmailOfUsers(email);
                                    SharedPrefManager.getInstance(mCtx).savePhonefUsers(phone);

                                    tvEnmail.setText(email);
                                    tvName.setText(name);
                                    tvPhone.setText(phone);

                                    if (imgUrl != null) {

                                        //  Picasso.get().load(imgUrl).into(circleImageView);
                                        Toast.makeText(mCtx,
                                                imgUrl,
                                                Toast.LENGTH_LONG).show();

                                        Glide.with(mCtx).load(imgUrl).into(circlImg);
                                        Log.d(TAG,"JSONStringPrfImagUrl ="+imgUrl);


                                    }else {
                                        mLoading.dismiss();

                                        circlImg.setImageResource(R.drawable.blank_profile_picture);
                                    }


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
    public void updateUserInfo(final String pass, final String newName, final String newEmail, final String newPhone,
                               final TextView tvName, final TextView tvEnmail, final TextView tvPhone,final CircleImageView circlImg) {

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
                            try {
                                String remoteResponse = Objects.requireNonNull(response.body()).string();

                                Log.d("JSONStringPrf", remoteResponse);

                                final JSONObject jsonRESULTS = new JSONObject(remoteResponse);

                                if (jsonRESULTS.getString(Contract.ERROR).equals(Contract.FALSE_VAL)) {
                                    mLoading.dismiss();

                                    Toast.makeText(mCtx, jsonRESULTS.optString(Contract.ERROR_MSG), Toast.LENGTH_SHORT).show();

                                    SharedPrefManager.getInstance(mCtx).
                                            saveEmailOfUsers(
                                                    newEmail.trim()
                                            );
                                    String name = jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.NAME_COL);
                                    String email = jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.EMAIL_COL);
                                    String phone = jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.PHONE_COL);
                                    String imgUrl=jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.IMAGE_COL);
                                    SharedPrefManager.getInstance(mCtx).saveNamesOfUsers(name);
                                    SharedPrefManager.getInstance(mCtx).saveEmailOfUsers(email);
                                    SharedPrefManager.getInstance(mCtx).savePhonefUsers(phone);

                                    tvEnmail.setText(email);
                                    tvName.setText(name);
                                    tvPhone.setText(phone);
                                    if (imgUrl != null) {

                                        //  Picasso.get().load(imgUrl).into(circleImageView);
                                        Toast.makeText(mCtx,
                                                imgUrl,
                                                Toast.LENGTH_LONG).show();

                                        Glide.with(mCtx).load(imgUrl).into(circlImg);
                                        Log.d(TAG,"JSONStringPrfImagUrl ="+imgUrl);


                                    }else {
                                        mLoading.dismiss();

                                        circlImg.setImageResource(R.drawable.blank_profile_picture);
                                    }


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
    public void requestUpdateInfoWithImg(
            String old_part_img,
            File myfile,
            String passval,
            String new_nameval,
            String new_emailval,
            String _new_phoneval,
            final TextView tvName, final TextView tvEnmail, final TextView tvPhone,final CircleImageView circlImg
            ) {
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

        String emailVal=SharedPrefManager.getInstance(mCtx).getEmailOfUsers();

        RequestBody reqBody = RequestBody.create(MediaType.parse(Contract.MULTIPART_FILE_PATH), imagefile);
        MultipartBody.Part partImage = MultipartBody.Part.createFormData(Contract.PIC_TO_LOAD, imagefile.getName(), reqBody);
        RequestBody email = createPartFromString(emailVal);
        RequestBody password = createPartFromString(passval);
        RequestBody new_name = createPartFromString(new_nameval);
        RequestBody new_email = createPartFromString(new_emailval);
        RequestBody new_phone = createPartFromString(_new_phoneval);
        RequestBody lang = createPartFromString(LangUtil.getCurrentLanguage(mCtx));


        HashMap<String, RequestBody> map = new HashMap<>();
        map.put(Contract.EMAIL_COL,email );
        map.put(Contract.PASSWORD_COL, password);
        map.put(Contract.NEW_NAME_COL, new_name);
        map.put(Contract.NEW_EMAIL_COL, new_email);
        map.put(Contract.NEW_PHONE_COL, new_phone);
        map.put(Contract.LANG_COL, lang);

        Call<ResponseApiModel> upload = mApiService.updateUserInfoWithImg(map, partImage);
        upload.enqueue(new Callback<ResponseApiModel>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<ResponseApiModel> call, @NonNull final Response<ResponseApiModel> response) {

                Log.d(TAG, "JSONStringPrfImg = : " +
                        Objects.requireNonNull(response.body()).toString());

                if (Objects.requireNonNull(response.body()).getError().equals(Contract.FALSE_VAL)) {
                    //   mLoading.setMessage(response.body().getError_msg());

                    mLoading.dismiss();

                    Log.d(TAG, "server_message_PrfImg : " + Objects.requireNonNull(response.body()).getError_msg()
                            + "\n" + Objects.requireNonNull(response.body()).getSuccess_msg());

                    //    mLoading.dismiss();
                    if (Objects.requireNonNull(response.body()).getSuccess_msg().equals(Contract.SUCCESS_MSG_VALUE)) {
                        mLoading.dismiss();

                        String name = Objects.requireNonNull(response.body()).getName();
                        String email = Objects.requireNonNull(response.body()).getEmail();
                        String phone = Objects.requireNonNull(response.body()).getPhone();
                        String imgUrl=Objects.requireNonNull(response.body()).getImg_url();
                        SharedPrefManager.getInstance(mCtx).saveNamesOfUsers(name);
                        SharedPrefManager.getInstance(mCtx).saveEmailOfUsers(email);
                        SharedPrefManager.getInstance(mCtx).savePhonefUsers(phone);

                        tvEnmail.setText(email);
                        tvName.setText(name);
                        tvPhone.setText(phone);
                        if (imgUrl != null) {

                            //  Picasso.get().load(imgUrl).into(circleImageView);
                            Toast.makeText(mCtx,
                                    imgUrl,
                                    Toast.LENGTH_LONG).show();

                            Glide.with(mCtx).load(imgUrl).into(circlImg);
                            Log.d(TAG,"JSONStringPrfImagUrl ="+imgUrl);


                        }else {
                            mLoading.dismiss();

                            circlImg.setImageResource(R.drawable.blank_profile_picture);
                        }


                    }


                } else   {
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


}
