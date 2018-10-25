package com.delivery.arish.arishdelivery.ui.Main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Surface;
import android.view.WindowManager;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.mvp.View.OnItemListClickListener;
import com.delivery.arish.arishdelivery.mvp.model.MainModel;
import com.delivery.arish.arishdelivery.data.Contract;
import com.delivery.arish.arishdelivery.mvp.presenter.MainPresenter;
import com.delivery.arish.arishdelivery.ui.details.DetailsActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("Registered")
public class MainActivity extends AppCompatActivity  {


    //bind RecyclerView
    @BindView(R.id.recycler_view)
    protected RecyclerView mRv;
    private ArrayList<MainModel> mMainModelArrayList;
    private MainListAdapter mMainListAdapter;
    protected MainPresenter mPresenter;


    private final OnItemListClickListener onBakeClickListener = new OnItemListClickListener() {
        @Override
        public void onlItemClick(int pos) {
            launchDetailActivity(pos);

        }


    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            GetListByScreenSize();

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



    //initialiseList to show values inside mBake_list
    private void initialiseListWithPhoneScreen() {


        ButterKnife.bind(this);
        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        //Pass a list of images with inflater ​​in adapter
        mMainListAdapter = new MainListAdapter(MainPresenter.getMainModel(this), getLayoutInflater());

        mMainListAdapter.setBakeClickListener(onBakeClickListener);

        mRv.setAdapter(mMainListAdapter);
    }

    //initialiseList to show values inside mBake_list
    private void initialiseListWithsLargeSize() {


        ButterKnife.bind(this);
        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(new GridLayoutManager(this, 2,
                GridLayoutManager.VERTICAL, false));
        //Pass a list of images with inflater ​​in adapter
        mMainListAdapter = new MainListAdapter(MainPresenter.getMainModel(this), getLayoutInflater());

        mMainListAdapter.setBakeClickListener(onBakeClickListener);

        mRv.setAdapter(mMainListAdapter);
    }

}
