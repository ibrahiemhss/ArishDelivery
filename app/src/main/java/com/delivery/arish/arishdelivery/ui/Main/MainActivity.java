package com.delivery.arish.arishdelivery.ui.Main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import com.delivery.arish.arishdelivery.base.BaseActivity;
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
public class MainActivity extends BaseActivity {


    //bind RecyclerView
    @BindView(R.id.recycler_view)
    protected RecyclerView mRv;
    @BindView(R.id.drawer_layout)
    protected DrawerLayout mDrawerLayout;
    @BindView(R.id.maintoolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.nav_view)
    protected NavigationView mNavigationView;


    @Override
    protected int getResourceLayout() {
        return R.layout.activity_main;
    }
    
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void init() {
        LayoutInflater layoutInflater=getLayoutInflater();
        MainPresenter.GetListByScreenSize(this,mRv,layoutInflater, null);
        setSupportActionBar(mToolbar);
        mToolbar.setLogoDescription(getResources().getString(R.string.app_name));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        mDrawerLayout.addDrawerListener( toggle );
        toggle.syncState();
        MainPresenter.initNavigationDrawer(this,mNavigationView);

        }

    @Override
    protected void setListener() {

    }
    

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen( GravityCompat.START )) {
            mDrawerLayout.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

}
