package com.delivery.arish.arishdelivery.ui.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.base.BaseActivity;
import com.delivery.arish.arishdelivery.mvp.presenter.MainPresenter;

import butterknife.BindView;

@SuppressLint("Registered")
public class MainActivity extends BaseActivity {

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.recycler_view)
    protected RecyclerView mRv;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.drawer_layout)
    protected DrawerLayout mDrawerLayout;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.maintoolbar)
    protected Toolbar mToolbar;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.nav_view)
    protected NavigationView mNavigationView;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);


    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void init() {
        MainPresenter mainPresenter = new MainPresenter(this);

        LayoutInflater layoutInflater = getLayoutInflater();
        mainPresenter.getCategoriesArrayList( mRv, layoutInflater);

        setSupportActionBar(mToolbar);
        mToolbar.setLogoDescription(getResources().getString(R.string.app_name));//to show title inside  toolbar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                //to make animation with action with close and open NavigationView with DrawerLayout
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mainPresenter.initNavigationDrawer(this, mNavigationView);

    }

    @Override
    protected void setListener() {

    }


    @Override
    public void onBackPressed() {//to make arrow back icon in toolbar to open and close DrawerLayout

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
