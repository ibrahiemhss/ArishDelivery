package com.delivery.arish.arishdelivery.ui.details;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.base.BaseActivity;
import com.delivery.arish.arishdelivery.data.Contract;
import com.delivery.arish.arishdelivery.mvp.presenter.DetailsPresenter;
import com.delivery.arish.arishdelivery.ui.Main.MainActivity;
import com.delivery.arish.arishdelivery.ui.order.OrdersActivity;

import butterknife.BindView;

public class DetailsActivity extends BaseActivity {


    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.details_recycler_view)
    protected RecyclerView mRv;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.detailstoolbar)
    protected Toolbar mToolbar;


    private DetailsPresenter mDetailsPresenter;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mToolbar.setTitle(getResources().getString(R.string.app_name));
        }
    }


    @SuppressWarnings("SameReturnValue")
    @Override
    protected int getResourceLayout() {
        return R.layout.activity_details;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void init() {
        mDetailsPresenter=new DetailsPresenter(this);
       // Bundle extras = getIntent().getExtras();
       // assert extras != null;
        mDetailsPresenter.getRestaurantArrayList(mRv, getLayoutInflater());

    }

    @SuppressWarnings("EmptyMethod")
    @Override
    protected void setListener() {

    }

    @SuppressWarnings("SameReturnValue")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);

                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }




}
