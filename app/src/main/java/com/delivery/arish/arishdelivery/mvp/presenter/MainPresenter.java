package com.delivery.arish.arishdelivery.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.data.Contract;
import com.delivery.arish.arishdelivery.data.SharedPrefManager;
import com.delivery.arish.arishdelivery.mvp.View.OnItemListClickListener;
import com.delivery.arish.arishdelivery.mvp.model.MainModel;
import com.delivery.arish.arishdelivery.ui.Main.MainActivity;
import com.delivery.arish.arishdelivery.ui.Main.MainListAdapter;
import com.delivery.arish.arishdelivery.ui.details.DetailsActivity;
import com.delivery.arish.arishdelivery.ui.log_in.LogInActivity;
import com.delivery.arish.arishdelivery.ui.log_in.ProfileActivity;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainPresenter {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void GetListByScreenSize(Context context, RecyclerView rv, LayoutInflater getLayoutInflater,MainListAdapter mainListAdapter) {

        assert context.getSystemService(Context.WINDOW_SERVICE) != null;
        final int rotation = ((WindowManager) Objects.requireNonNull(context.getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay().getOrientation();
        switch (rotation) {
            case Surface.ROTATION_0:
                if (isTablet(context)) {
                    initialiseListWithsLargeSize(context,rv,getLayoutInflater,mainListAdapter);
                } else {
                    initialiseListWithPhoneScreen(context,rv,getLayoutInflater,mainListAdapter);
                }
                break;
            case Surface.ROTATION_90:
                initialiseListWithsLargeSize(context,rv,getLayoutInflater,mainListAdapter);
                break;
            case Surface.ROTATION_180:
                initialiseListWithPhoneScreen(context,rv,getLayoutInflater,mainListAdapter);
                break;

            case Surface.ROTATION_270:
                break;
        }
    }

    private static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    private static void initialiseListWithPhoneScreen(final Context context, RecyclerView rv, LayoutInflater getLayoutInflater, MainListAdapter mainListAdapter) {

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false));
        mainListAdapter = new MainListAdapter(getMainModel(context), getLayoutInflater);
        rv.setAdapter(mainListAdapter);
        mainListAdapter.notifyDataSetChanged();

        final OnItemListClickListener onItemListClickListener = new OnItemListClickListener() {
            @Override
            public void onlItemClick(int pos) {
                launchDetailActivity(pos,context);

            }
        };

        mainListAdapter.setOnItemListClickListener(onItemListClickListener);

    }

    private static void initialiseListWithsLargeSize(final Context context, RecyclerView rv, LayoutInflater getLayoutInflater, MainListAdapter mainListAdapter) {


        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(context, 2,
                GridLayoutManager.VERTICAL, false));
        mainListAdapter = new MainListAdapter(getMainModel(context), getLayoutInflater);
        rv.setAdapter(mainListAdapter);
        mainListAdapter.notifyDataSetChanged();

        final OnItemListClickListener onItemListClickListener = new OnItemListClickListener() {
            @Override
            public void onlItemClick(int pos) {
                launchDetailActivity(pos,context);

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



    private static   ArrayList<MainModel> getMainModel(Context context)
    {


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


        ArrayList<MainModel> mainModelArrayList=new ArrayList<>();


        for(int i=0;i<names_values.length;i++){
            MainModel s=new MainModel();
            s.setName(names_values[i]);
            s.setImage(images_values[i]);
            mainModelArrayList.add(s);
        }
        return mainModelArrayList;
    }



    public static void initNavigationDrawer(final Context context, NavigationView navigationView){



        CircleImageView circleImageView =navigationView.findViewById(R.id.nav_image);
        TextView txtName =navigationView.findViewById(R.id.nav_txtname);
        CircleImageView txtEmail =navigationView.findViewById(R.id.nav_txtemail);
        ImageView imgProfile=navigationView.findViewById(R.id.nav_profile);
        ImageView imgShare=navigationView.findViewById(R.id.nav_share);
        ImageView imgLogOut=navigationView.findViewById(R.id.nav_signout);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launching the login activity
                Intent intent = new Intent( context, ProfileActivity.class );
                context.startActivity( intent );
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

    private static void logoutUser(Context context) {

        SharedPrefManager.getInstance( context ).setLoginUser( false );

        // Launching the login activity
        Intent intent = new Intent( context, LogInActivity.class );
        context.startActivity( intent );
    }
}
