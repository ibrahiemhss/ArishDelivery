package com.delivery.arish.arishdelivery.ui.details;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Surface;
import android.view.WindowManager;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.base.BaseActivity;
import com.delivery.arish.arishdelivery.data.Contract;
import com.delivery.arish.arishdelivery.mvp.View.OnItemListClickListener;
import com.delivery.arish.arishdelivery.mvp.model.DetailsModel;
import com.delivery.arish.arishdelivery.mvp.presenter.DetailsPresenter;
import com.delivery.arish.arishdelivery.ui.Main.MainActivity;
import com.delivery.arish.arishdelivery.ui.order.OrdersActivity;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends BaseActivity {


    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.details_recycler_view)
    protected RecyclerView mRv;
    private ArrayList<DetailsModel> mDetailsModelArrayList;
    private DetailsAdapter mDetailsAdapter;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.detailstoolbar)
    protected Toolbar mToolbar;


    private final OnItemListClickListener onItemListClickListener = new OnItemListClickListener() {
        @Override
        public void onlItemClick(int pos) {

            launchOrderActivity(pos);
        }


    };

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


    @Override
    protected int getResourceLayout() {
        return R.layout.activity_details;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void init() {

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        if (extras.containsKey(Contract.EXTRA_MAIN_LIST_POSITION)) {
            int id = extras.getInt(Contract.EXTRA_MAIN_LIST_POSITION, 0);
            mDetailsModelArrayList =
                    extras.getParcelableArrayList(Contract.EXTRA_DETAILS_LIST);
            if (id > 0) {
                Bundle extras2 = new Bundle();
                extras2.putBoolean(Contract.EXTRA_INTER_ACTIVITY, false);
                Intent intent = new Intent(DetailsActivity.this, OrdersActivity.class);
                intent.putExtras(extras2);
                startActivity(intent);
                finish();

            }
        }
        mDetailsModelArrayList = DetailsPresenter.getDetailsModel();

        GetListByScreenSize();
    }

    @Override
    protected void setListener() {

    }

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void GetListByScreenSize() {

        assert this.getSystemService(Context.WINDOW_SERVICE) != null;
        final int rotation = ((WindowManager) Objects.requireNonNull(this.getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay().getOrientation();
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

    private boolean isTablet() {
        return (DetailsActivity.this.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    private void initialiseListWithPhoneScreen() {

        ButterKnife.bind(this);
        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(new GridLayoutManager(this, 2,
                GridLayoutManager.VERTICAL, false));
        mDetailsAdapter = new DetailsAdapter(mDetailsModelArrayList, getLayoutInflater());
        mDetailsAdapter.setOnItemListClickListener(onItemListClickListener);
        mRv.setAdapter(mDetailsAdapter);
        mDetailsAdapter.notifyDataSetChanged();
    }

    private void initialiseListWithsLargeSize() {

        ButterKnife.bind(this);
        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(new GridLayoutManager(this, 3,
                GridLayoutManager.VERTICAL, false));
        mDetailsAdapter = new DetailsAdapter(mDetailsModelArrayList, getLayoutInflater());
        mDetailsAdapter.setOnItemListClickListener(onItemListClickListener);
        mRv.setAdapter(mDetailsAdapter);
        mDetailsAdapter.notifyDataSetChanged();

    }

    private void launchOrderActivity(int position) {
        Intent intent = new Intent(DetailsActivity.this, OrdersActivity.class);
        Bundle extras = new Bundle();
        extras.putInt(Contract.EXTRA_DETAILS_LIST_POSITION, position);
        extras.putParcelableArrayList(Contract.EXTRA_DETAILS_LIST, mDetailsModelArrayList);
        // SharedPrefManager.getInstance(this).setPrefBakePosition(position);
        // String name = mMainModelArrayList.get(position).getName();
        // extras.putString(Contract.EXTRA_BAKE_NAME, name);
        // SharedPrefManager.getInstance(this).setPrefDetailsPosition(position);
        // SharedPrefManager.getInstance(this).setPrefBakeName(name);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
