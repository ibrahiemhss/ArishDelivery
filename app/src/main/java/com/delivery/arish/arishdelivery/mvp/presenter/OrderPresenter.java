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
import com.delivery.arish.arishdelivery.ui.details.DetailsActivity;
import com.delivery.arish.arishdelivery.ui.order.OrdersActivity;
import com.delivery.arish.arishdelivery.util.LangUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings({"WeakerAccess", "unused"})
public class OrderPresenter {
    private final Context mCtx;
    private final BaseApiService mApiService;
    private ProgressDialog mLoading;

    private String orderId;
    public OrderPresenter(Context context) {
        mCtx = context;
        mApiService = UtilsApi.getAPIService();
    }

    @SuppressWarnings("DanglingJavadoc")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//book base order...............................................................................................
    public void requestAddOrder(String order) {
        //initialize ProgressDialog status message
        mLoading = ProgressDialog.show(mCtx, null, mCtx.getResources().getString(R.string.adding_order), true, false);



        mApiService.addOrder(order, SharedPrefManager.getInstance(mCtx).getUserId(),
                SharedPrefManager.getInstance(mCtx).getCategoryId(), LangUtil.getCurrentLanguage(mCtx))

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

                                Log.d("JSONStringAddOrder", remoteResponse);

                                JSONObject jsonRESULTS = new JSONObject(remoteResponse);

                                if (jsonRESULTS.getString(Contract.ERROR).equals(Contract.FALSE_VAL)) {
                                    Toast.makeText(mCtx, jsonRESULTS.optString(Contract.ERROR_MSG), Toast.LENGTH_SHORT).show();
                                    String id = jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.ID_COL);
                                    requestSendOrderMessag(id);
                                    Intent intent = new Intent(mCtx, MainActivity.class);
                                    mCtx.startActivity(intent);


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
    @SuppressWarnings("DanglingJavadoc")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    public void requestSendOrderMessag(String id) {
        //initialize ProgressDialog status message
//wants a new order
        String title=mCtx.getResources().getString(R.string.app_name);
        String userName=SharedPrefManager.getInstance(mCtx).getNamesOfUsers();
        String message=userName+" :"+mCtx.getResources().getString(R.string.order_message);
        String email=SharedPrefManager.getInstance(mCtx).getEmailOfUsers();


        mApiService.sendOrderMessage(id,title, message)

                // ,SharedPrefManager.getInstance( this ).getDeviceToken())
                .enqueue(new Callback<ResponseBody>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            //  mLoading.dismiss();
                            /*get value from JsonObjects come from server*/

                            try {
                                String remoteResponse = Objects.requireNonNull(response.body()).string();

                                Log.d("JSONStringMessage", remoteResponse);

                                JSONObject jsonRESULTS = new JSONObject(remoteResponse);

                                if (jsonRESULTS.optInt(Contract.SUCCESS_MSG_VALUE)==1 ){
                                    Toast.makeText(mCtx, mCtx.getResources().getString(R.string.success_sent), Toast.LENGTH_SHORT).show();

                                }else {
                                    Toast.makeText(mCtx, mCtx.getResources().getString(R.string.error_sent), Toast.LENGTH_SHORT).show();
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

    //book restaurant order...............................................................................................
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void requestAddRestOrder(String order) {
        //initialize ProgressDialog status message
        mLoading = ProgressDialog.show(mCtx, null, mCtx.getResources().getString(R.string.adding_order), true, false);



        mApiService.addRestOrder(order, SharedPrefManager.getInstance(mCtx).getUserId(),
                SharedPrefManager.getInstance(mCtx).getRestId(),
                SharedPrefManager.getInstance(mCtx).getCategoryId(), LangUtil.getCurrentLanguage(mCtx))

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

                                Log.d("JSONStringRestAddOrder", remoteResponse);

                                JSONObject jsonRESULTS = new JSONObject(remoteResponse);

                                if (jsonRESULTS.getString(Contract.ERROR).equals(Contract.FALSE_VAL)) {
                                    Toast.makeText(mCtx, jsonRESULTS.optString(Contract.ERROR_MSG), Toast.LENGTH_SHORT).show();
                                    String id = jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.ID_COL);
                                    String restaurant_id=jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.RESTAURANT_ID_COL);
                                    requestSendRestOrderMessag(id,restaurant_id);
                                    Intent intent = new Intent(mCtx, DetailsActivity.class);
                                    mCtx.startActivity(intent);

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
    @SuppressWarnings("DanglingJavadoc")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    public void requestSendRestOrderMessag(String id,String restaurant_id) {
        //initialize ProgressDialog status message
//wants a new order
        String title=mCtx.getResources().getString(R.string.app_name);
        String userName=SharedPrefManager.getInstance(mCtx).getNamesOfUsers();
        String message=userName+" :"+mCtx.getResources().getString(R.string.order_message);
        String email=SharedPrefManager.getInstance(mCtx).getEmailOfUsers();


        mApiService.sendOrderRestMessage(id,restaurant_id,title, message)

                // ,SharedPrefManager.getInstance( this ).getDeviceToken())
                .enqueue(new Callback<ResponseBody>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            //  mLoading.dismiss();
                            /*get value from JsonObjects come from server*/

                            try {
                                String remoteResponse = Objects.requireNonNull(response.body()).string();

                                Log.d("JSONStringMessage", remoteResponse);

                                JSONObject jsonRESULTS = new JSONObject(remoteResponse);

                                if (jsonRESULTS.optInt(Contract.SUCCESS_MSG_VALUE)==1 ){
                                    Toast.makeText(mCtx, mCtx.getResources().getString(R.string.success_sent), Toast.LENGTH_SHORT).show();

                                }else {
                                    Toast.makeText(mCtx, mCtx.getResources().getString(R.string.error_sent), Toast.LENGTH_SHORT).show();
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
