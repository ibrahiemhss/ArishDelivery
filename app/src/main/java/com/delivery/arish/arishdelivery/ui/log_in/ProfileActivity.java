package com.delivery.arish.arishdelivery.ui.log_in;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.base.BaseActivity;
import com.delivery.arish.arishdelivery.ui.Main.MainActivity;

import butterknife.BindView;

public class ProfileActivity extends BaseActivity {
    private static final String TAG = ProfileActivity.class.getSimpleName();

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.profile_toolbar)
    protected Toolbar mToolbar;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.profile_collapsing_toolbar)
    protected CollapsingToolbarLayout mCollapsingToolbarLayout;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.profile_pp_bar)
    protected AppBarLayout mAppBarLayout;
    private String mLocale;


    private boolean isTablet;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        isTablet = getResources().getBoolean(R.bool.isTablet);

        setupToolbar();
       // AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) mCollapsingToolbarLayout.getLayoutParams();

    }

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_profile;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mLocale = getResources().getConfiguration().locale.getDisplayName();
        Log.d(TAG, "LanguageDevice onRestart is  "+mLocale);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocale = getResources().getConfiguration().locale.getDisplayName();
        Log.d(TAG, "LanguageDevice onRestart is  "+mLocale);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocale = getResources().getConfiguration().locale.getDisplayName();
        Log.d(TAG, "LanguageDevice onRestart is  "+mLocale);

    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            mCollapsingToolbarLayout.setTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mToolbar.setTitle(getResources().getString(R.string.app_name));
            if (!isTablet) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

        }


}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent( ProfileActivity.this, MainActivity.class );
                startActivity( intent );
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

}
