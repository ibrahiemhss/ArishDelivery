package com.delivery.arish.arishdelivery.ui.Main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.data.SharedPrefManager;
import com.delivery.arish.arishdelivery.mvp.View.OnItemListClickListener;
import com.delivery.arish.arishdelivery.mvp.model.MainModel;
import com.delivery.arish.arishdelivery.data.Contract;
import com.delivery.arish.arishdelivery.mvp.presenter.MainPresenter;
import com.delivery.arish.arishdelivery.ui.details.DetailsActivity;
import com.delivery.arish.arishdelivery.ui.log_in.LogInActivity;
import com.delivery.arish.arishdelivery.ui.log_in.ProfileActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("Registered")
public class MainActivity extends AppCompatActivity {


    //bind RecyclerView
    @BindView(R.id.recycler_view)
    protected RecyclerView mRv;
    private ArrayList<MainModel> mMainModelArrayList;
    private MainListAdapter mMainListAdapter;
    @BindView(R.id.drawer_layout)
    protected DrawerLayout mDrawerLayout;
    @BindView(R.id.maintoolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.nav_view)
    protected NavigationView mNavigationView;

    private ActionBarDrawerToggle mDrawerToggle;



    private final OnItemListClickListener onItemListClickListener = new OnItemListClickListener() {
        @Override
        public void onlItemClick(int pos) {
            launchDetailActivity(pos);

        }


    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        mMainModelArrayList=MainPresenter.getMainModel(this);
            GetListByScreenSize();


            setSupportActionBar(mToolbar);
            mToolbar.setLogoDescription(getResources().getString(R.string.app_name));

        initNavigationDrawer();


    }

    private void launchDetailActivity(int position) {
        Intent intent = new Intent(this, DetailsActivity.class);
        Bundle extras = new Bundle();
        extras.putInt(Contract.EXTRA_MAIN_LIST_POSITION, position);
       // SharedPrefManager.getInstance(this).setPrefBakePosition(position);
        String name = mMainModelArrayList.get(position).getName();
       // extras.putString(Contract.EXTRA_BAKE_NAME, name);
       // SharedPrefManager.getInstance(this).setPrefDetailsPosition(position);
       // SharedPrefManager.getInstance(this).setPrefBakeName(name);
        intent.putExtras(extras);
        startActivity(intent);
    }


    public void GetListByScreenSize() {

        assert this.getSystemService(Context.WINDOW_SERVICE) != null;
        final int rotation = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (rotation) {
            case Surface.ROTATION_0:
                if (isTablet()) {
                    initialiseListWithsLargeSize();
                } else {
                    initialiseListWithPhoneScreen();
                }
                break;
            case Surface.ROTATION_90:
                initialiseListWithsLargeSize();
                break;
            case Surface.ROTATION_180:
                initialiseListWithPhoneScreen();
                break;

            case Surface.ROTATION_270:
                break;
        }
    }

    public boolean isTablet() {
        return (MainActivity.this.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    private void initialiseListWithPhoneScreen() {

        ButterKnife.bind(this);
        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mMainListAdapter = new MainListAdapter(mMainModelArrayList, getLayoutInflater());
        mMainListAdapter.setOnItemListClickListener(onItemListClickListener);
        mRv.setAdapter(mMainListAdapter);
        mMainListAdapter.notifyDataSetChanged();
    }

    private void initialiseListWithsLargeSize() {

        ButterKnife.bind(this);
        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(new GridLayoutManager(this, 2,
                GridLayoutManager.VERTICAL, false));
        mMainListAdapter = new MainListAdapter(mMainModelArrayList, getLayoutInflater());
        mMainListAdapter.setOnItemListClickListener(onItemListClickListener);
        mRv.setAdapter(mMainListAdapter);
        mMainListAdapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen( GravityCompat.START )) {
            mDrawerLayout.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }




    private void initNavigationDrawer(){

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        mDrawerLayout.addDrawerListener( toggle );
        toggle.syncState();

        CircleImageView circleImageView =mNavigationView.findViewById(R.id.nav_image);
        TextView txtName =mNavigationView.findViewById(R.id.nav_txtname);
        CircleImageView txtEmail =mNavigationView.findViewById(R.id.nav_txtemail);
        ImageView imgProfile=mNavigationView.findViewById(R.id.nav_profile);
        ImageView imgShare=mNavigationView.findViewById(R.id.nav_share);
        ImageView imgLogOut=mNavigationView.findViewById(R.id.nav_signout);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launching the login activity
                Intent intent = new Intent( MainActivity.this, ProfileActivity.class );
                startActivity( intent );
                finish();
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
                logoutUser();
            }
        });




    }

    private void logoutUser() {

        SharedPrefManager.getInstance( this ).setLoginUser( false );

        // Launching the login activity
        Intent intent = new Intent( MainActivity.this, LogInActivity.class );
        startActivity( intent );
        finish();
    }
}
