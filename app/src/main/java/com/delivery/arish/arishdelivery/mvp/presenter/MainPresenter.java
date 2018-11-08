package com.delivery.arish.arishdelivery.mvp.presenter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.data.Contract;
import com.delivery.arish.arishdelivery.data.SharedPrefManager;
import com.delivery.arish.arishdelivery.internet.BaseApiService;
import com.delivery.arish.arishdelivery.internet.UtilsApi;
import com.delivery.arish.arishdelivery.mvp.View.OnItemListClickListener;
import com.delivery.arish.arishdelivery.mvp.model.MainModel;
import com.delivery.arish.arishdelivery.ui.Main.MainListAdapter;
import com.delivery.arish.arishdelivery.ui.details.DetailsActivity;
import com.delivery.arish.arishdelivery.ui.log_in.LogInActivity;
import com.delivery.arish.arishdelivery.ui.log_in.ProfileActivity;
import com.delivery.arish.arishdelivery.util.LangUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {
   @SuppressWarnings("unused")
   private final static String TAG = MainPresenter.class.getSimpleName();
    private final Context mCtx;
    private final BaseApiService mApiService;
    private ProgressDialog mLoading;

    public MainPresenter(Context context) {
        mCtx = context;
        mApiService = UtilsApi.getAPIService();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void GetListByScreenSize(Context context, RecyclerView rv, LayoutInflater getLayoutInflater, MainListAdapter mainListAdapter) {

        assert context.getSystemService(Context.WINDOW_SERVICE) != null;
        final int rotation = ((WindowManager) Objects.requireNonNull(context.getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay().getOrientation();
        switch (rotation) {
            case Surface.ROTATION_0:
                if (isTablet(context)) {
                    initialiseListWithsLargeSize( rv, getLayoutInflater, mainListAdapter);
                } else {
                    initialiseListWithPhoneScreen(rv, getLayoutInflater, mainListAdapter);
                }
                break;
            case Surface.ROTATION_90:
                initialiseListWithsLargeSize(rv, getLayoutInflater, mainListAdapter);
                break;
            case Surface.ROTATION_180:
                initialiseListWithPhoneScreen(rv, getLayoutInflater, mainListAdapter);
                break;

            case Surface.ROTATION_270:
                break;
        }
    }

    private  boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    @SuppressWarnings("ParameterCanBeLocal")
    private  void initialiseListWithPhoneScreen(RecyclerView rv, LayoutInflater getLayoutInflater, MainListAdapter mainListAdapter) {

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(mCtx,
                LinearLayoutManager.VERTICAL, false));
        mainListAdapter = new MainListAdapter(getMainModel(mCtx), getLayoutInflater);
        rv.setAdapter(mainListAdapter);
        mainListAdapter.notifyDataSetChanged();

        final OnItemListClickListener onItemListClickListener = new OnItemListClickListener() {
            @SuppressWarnings("unused")
            @Override
            public void onlItemClick(int pos) {
                launchDetailActivity(pos, mCtx);

            }
        };

        mainListAdapter.setOnItemListClickListener(onItemListClickListener);

    }

    @SuppressWarnings("ParameterCanBeLocal")
    private  void initialiseListWithsLargeSize( RecyclerView rv, LayoutInflater getLayoutInflater, MainListAdapter mainListAdapter) {


        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(mCtx, 2,
                GridLayoutManager.VERTICAL, false));
        mainListAdapter = new MainListAdapter(getMainModel(mCtx), getLayoutInflater);
        rv.setAdapter(mainListAdapter);
        mainListAdapter.notifyDataSetChanged();

        final OnItemListClickListener onItemListClickListener = new OnItemListClickListener() {
            @SuppressWarnings("unused")
            @Override
            public void onlItemClick(int pos) {
                launchDetailActivity(pos, mCtx);

            }
        };

        mainListAdapter.setOnItemListClickListener(onItemListClickListener);


    }

    private static void launchDetailActivity(int position, Context context) {
        Intent intent = new Intent(context, DetailsActivity.class);
        Bundle extras = new Bundle();
        extras.putInt(Contract.EXTRA_MAIN_LIST_POSITION, position);
        // SharedPrefManager.getInstance(this).setPrefBakePosition(position);
        // String name = mMainModelArrayList.get(position).getName();
        // extras.putString(Contract.EXTRA_BAKE_NAME, name);
        // SharedPrefManager.getInstance(this).setPrefDetailsPosition(position);
        // SharedPrefManager.getInstance(this).setPrefBakeName(name);
        intent.putExtras(extras);
        context.startActivity(intent);
    }


    private static ArrayList<MainModel> getMainModel(Context context) {


        final String names_values[] = {
                context.getString(R.string.restaurant),
                context.getString(R.string.libraries),
                context.getString(R.string.pharmacies),
                context.getString(R.string.architectural_tools),
                context.getString(R.string.other)


        };

        final int images_values[] = {
                R.drawable.restaurant,
                R.drawable.libraries,
                R.drawable.pharmacies,
                R.drawable.architectural_tools,
                R.drawable.other

        };


        ArrayList<MainModel> mainModelArrayList = new ArrayList<>();


        for (int i = 0; i < names_values.length; i++) {
            MainModel s = new MainModel();
            s.setName(names_values[i]);
            s.setImage(images_values[i]);
            mainModelArrayList.add(s);
        }
        return mainModelArrayList;
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressWarnings("unused")
    public  void initNavigationDrawer(final Context context, final NavigationView navigationView) {

        Configuration configuration = context.getResources().getConfiguration();
        final int screenWidthDp = configuration.screenWidthDp; //The current width of the available screen space, in dp units, corresponding to screen width resource qualifier.
        navigationView.post(new Runnable() {
            @Override
            public void run() {
                Resources resources = context.getResources();
                float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (int) (screenWidthDp*0.83), resources.getDisplayMetrics());
                DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) navigationView.getLayoutParams();
                params.width = (int) (width);
                navigationView.setLayoutParams(params);
            }
        });

        Log.d(TAG,"allWidthOfScreen ="+String.valueOf(screenWidthDp)+"\n with after div ="+String.valueOf((int) (screenWidthDp*0.83)));

        CircleImageView circleImageView = navigationView.findViewById(R.id.nav_image);
        TextView txtName = navigationView.findViewById(R.id.nav_txtname);
        TextView txtEmail = navigationView.findViewById(R.id.nav_txtemail);
        ImageView imgProfile = navigationView.findViewById(R.id.nav_profile);
        ImageView imgShare = navigationView.findViewById(R.id.nav_share);
        ImageView imgLogOut = navigationView.findViewById(R.id.nav_signout);
        getUserImage(circleImageView,txtName,txtEmail);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launching the login activity
                Intent intent = new Intent(context, ProfileActivity.class);
                context.startActivity(intent);
            }
        });

        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        imgLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser(context);
            }
        });


    }

    @SuppressWarnings("unused")
    private static void logoutUser(Context context) {

        SharedPrefManager.getInstance(context).setLoginUser(false);

        // Launching the login activity
        Intent intent = new Intent(context, LogInActivity.class);
        context.startActivity(intent);
    }


    @SuppressWarnings("unused")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private   void getUserImage(final CircleImageView circleImageView, final TextView tvName, final TextView tvEmail) {

        mLoading = ProgressDialog.show(mCtx, null, mCtx.getResources().getString(R.string.loading_user_data), true, false);

        String emailVal
                =SharedPrefManager.
                getInstance(mCtx)
                .getEmailOfUsers();


        mApiService.getUserInfo(emailVal,LangUtil.getCurrentLanguage(mCtx))

                .enqueue(new Callback<ResponseBody>() {
                    @SuppressLint("SetTextI18n")
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()) {

                            try {
                                String remoteResponse = Objects.requireNonNull(response.body()).string();

                                Log.d("JSONStringNav", remoteResponse);

                                final JSONObject jsonRESULTS = new JSONObject(remoteResponse);


                                if (jsonRESULTS.optString(Contract.ERROR).equals(Contract.FALSE_VAL)) {
                                    String name = jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.NAME_COL);
                                    String email = jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.EMAIL_COL);
                                    String imgUrl=jsonRESULTS.getJSONObject(Contract.USER_COL).optString(Contract.IMAGE_COL);
                                    tvEmail.setText(mCtx.getResources().getString(R.string.email)+" :"+email);
                                    tvName.setText(mCtx.getResources().getString(R.string.name)+" :"+name);

                                    if (imgUrl != null) {

                                        Toast.makeText(mCtx,
                                                imgUrl,
                                                Toast.LENGTH_LONG).show();

                                        Glide.with(mCtx).load(imgUrl).into(circleImageView);
                                        Log.d(TAG,"JSONStringPrfImageUrl ="+imgUrl);
                                        mLoading.dismiss();


                                    }else {
                                        circleImageView.setImageResource(R.drawable.blank_profile_picture);
                                    }
                                    Toast.makeText(mCtx, jsonRESULTS.optString(Contract.ERROR_MSG), Toast.LENGTH_SHORT).show();

                                    mLoading.dismiss();
                                } else {
                                    circleImageView.setImageResource(R.drawable.blank_profile_picture);

                                    Toast.makeText(mCtx, jsonRESULTS.optString(Contract.ERROR_MSG), Toast.LENGTH_SHORT).show();
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
                        Log.e("debugJSONS", "onFailure: ERROR > " + t.toString());
                        mLoading.dismiss();

                    }
                });
    }



}
