package com.delivery.arish.arishdelivery.mvp.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.delivery.arish.arishdelivery.data.Contract;
import com.delivery.arish.arishdelivery.internet.BaseApiService;
import com.delivery.arish.arishdelivery.internet.UtilsApi;
import com.delivery.arish.arishdelivery.mvp.View.OnMainItemListClickListener;
import com.delivery.arish.arishdelivery.mvp.View.OnRetaurantItemClickListner;
import com.delivery.arish.arishdelivery.mvp.model.RestaurantModel;
import com.delivery.arish.arishdelivery.ui.details.DetailsActivity;
import com.delivery.arish.arishdelivery.ui.details.RestaurantsAdapter;
import com.delivery.arish.arishdelivery.ui.order.OrdersActivity;
import com.delivery.arish.arishdelivery.util.JsonUtils;
import com.delivery.arish.arishdelivery.util.LangUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsPresenter {
    private final static String TAG = MainPresenter.class.getSimpleName();
    private final Context mCtx;
    private final BaseApiService mApiService;
    private ProgressDialog mLoading;
    private RecyclerView  mRvRestaurant;
    private ArrayList<RestaurantModel> mRestaurantArrayList;
    private LayoutInflater mLayoutInflater;

    public DetailsPresenter(Context context) {
        mCtx = context;
        mApiService = UtilsApi.getAPIService();
        if (mRestaurantArrayList != null) {
            mRestaurantArrayList.clear();

        }

    }
    private final OnRetaurantItemClickListner onRetaurantItemClickListner = this::launchOrderActivity;

    private void launchOrderActivity( int i, String id,String imgUrl) {

        Intent intent = new Intent(mCtx, OrdersActivity.class);
        Bundle extras = new Bundle();
        extras.putString(Contract.EXTRA_RESTAURANT_ID_ITEM, id);
        extras.putString(Contract.EXTRA_RESTAURANT_IMAGE_URL_ITEM, imgUrl);

        intent.putExtras(extras);
        mCtx.startActivity(intent);
    }


    ////////////////////////////////////////////////getRestaurant  values ArrayList from server////////////////////////////////////////////////////////////////////////
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void getRestaurantArrayList(final RecyclerView rvShow, final LayoutInflater layoutInflater) {


        mApiService.getRestaurant(LangUtil.getCurrentLanguage(mCtx))

                // ,SharedPrefManager.getInstance( this ).getDeviceToken())
                .enqueue(new Callback<ResponseBody>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String remoteResponse = Objects.requireNonNull(response.body()).string();

                                Log.d("JSONStringGetRestaurant", remoteResponse);

                                initialiseListRestaurant(remoteResponse, rvShow, layoutInflater);


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

    private void initialiseListRestaurant(String remoteResponse, RecyclerView rvShowAll, LayoutInflater layoutInflater) {
        mRvRestaurant = rvShowAll;
        mLayoutInflater = layoutInflater;
        if (mRestaurantArrayList != null) {
            mRestaurantArrayList.clear();

        }

        mRestaurantArrayList = JsonUtils.parseRestaurantModel(remoteResponse, Contract.RESTAURANT_COL);
        rvShowAll.setHasFixedSize(true);
        rvShowAll.setLayoutManager(new GridLayoutManager(mCtx, 2,
                GridLayoutManager.VERTICAL, false));
        RestaurantsAdapter mRestaurantsAdapter = new RestaurantsAdapter(
                mRestaurantArrayList
                , layoutInflater);
        mRestaurantsAdapter.setOnRetaurantItemClickListner(onRetaurantItemClickListner);
        rvShowAll.setAdapter(mRestaurantsAdapter);
        mRestaurantsAdapter.notifyDataSetChanged();


    }


}
