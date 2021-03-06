package com.delivery.arish.arishdelivery.ui.order;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.base.BaseActivity;
import com.delivery.arish.arishdelivery.data.Contract;
import com.delivery.arish.arishdelivery.data.SharedPrefManager;
import com.delivery.arish.arishdelivery.mvp.model.DetailsModel;
import com.delivery.arish.arishdelivery.mvp.presenter.OrderPresenter;
import com.delivery.arish.arishdelivery.ui.Main.MainActivity;
import com.delivery.arish.arishdelivery.ui.details.DetailsActivity;
import com.delivery.arish.arishdelivery.util.EditTextUtil;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class OrdersActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = OrdersActivity.class.getSimpleName();

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.img_menu)
    protected ImageView mImgMenu;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.rv_container_orders)
    protected RelativeLayout mRvContainerOrders;
    @SuppressWarnings({"WeakerAccess", "unused"})
    @BindView(R.id.btn_add_order)
    protected Button mAddOrder;
    @SuppressWarnings({"WeakerAccess", "unused"})
    @BindView(R.id.etx_add_order)
    protected TextView mEtxAddOrder;
    @SuppressWarnings({"WeakerAccess", "unused"})
    @BindView(R.id.rv_direct_call)
    protected RelativeLayout mDirectCall;
    @SuppressWarnings({"WeakerAccess", "unused"})
    @BindView(R.id.rv_whatsapp)
    protected RelativeLayout mWhatsApp;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.order_toolbar)
    protected Toolbar mToolbar;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.arrow_down)
    protected ImageView mArrowDown;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.arrow_upp)
    protected ImageView mArrowUpp;

    private OrderPresenter mOrderPresenter;


    private boolean isInterActivity = false;
    @SuppressWarnings("SameReturnValue")
    @Override
    protected int getResourceLayout() {
        return R.layout.activity_orders;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        mOrderPresenter=new OrderPresenter(this);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mToolbar.setTitle(getResources().getString(R.string.app_name));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void init() {


        Bundle extras = getIntent().getExtras();
        assert extras != null;
        if (extras.containsKey(Contract.EXTRA_RESTAURANT_ID_ITEM)
                && extras.containsKey(Contract.EXTRA_RESTAURANT_IMAGE_URL_ITEM)) {

            mArrowDown.setVisibility(View.VISIBLE);

           // mService_id= extras.getString(Contract.EXTRA_RESTAURANT_ID_ITEM);
          //  mCustomer_id= SharedPrefManager.getInstance(this).getUserId();
            String imgUrl=extras.getString(Contract.EXTRA_RESTAURANT_IMAGE_URL_ITEM);
            Toast.makeText(this, imgUrl, Toast.LENGTH_SHORT).show();

            if (imgUrl != null) {
                Glide.with(this).load(imgUrl).into(mImgMenu);
                mRvContainerOrders.setElevation(12);
                mRvContainerOrders.setBackgroundColor(getResources().getColor(R.color.white));
                PhotoViewAttacher photoAttacher;
                photoAttacher = new PhotoViewAttacher(mImgMenu);
                photoAttacher.update();
            }

            mArrowDown.setVisibility(View.VISIBLE);
            mArrowUpp.setVisibility(View.VISIBLE);

            // isHide=true;

        } else if (extras.containsKey(Contract.EXTRA_INTER_FROM_MAIN_ACTIVITY)) {
            isInterActivity = extras.getBoolean(Contract.EXTRA_INTER_FROM_MAIN_ACTIVITY);
            mArrowDown.setVisibility(View.GONE);
            mArrowUpp.setVisibility(View.GONE);
        }
    }

    @Override
    protected void setListener() {


        mArrowDown.setOnClickListener(this);
        mArrowUpp.setOnClickListener(this);
        mDirectCall.setOnClickListener(this);
        mWhatsApp.setOnClickListener(this);
        mAddOrder.setOnClickListener(this);
        mArrowUpp.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent;
                if (isInterActivity) {
                    intent = new Intent(OrdersActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(OrdersActivity.this, DetailsActivity.class);

                }

                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent;
        if (isInterActivity) {
            intent = new Intent(OrdersActivity.this, MainActivity.class);
        } else {
            intent = new Intent(OrdersActivity.this, DetailsActivity.class);
        }

        startActivity(intent);
    }


    private void slideUp(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.push_up_in));
        mRvContainerOrders.setVisibility(View.VISIBLE);
    }

    // slide the view from its current position to below itself
    private void slideDown(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.push_down_out));
        mRvContainerOrders.setVisibility(View.GONE);

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.arrow_down:
                slideDown(mRvContainerOrders);
                mArrowUpp.setVisibility(View.VISIBLE);
                break;
            case R.id.arrow_upp:
                slideUp(mRvContainerOrders);
                mArrowUpp.setVisibility(View.GONE);

                break;
            case R.id.btn_add_order:


                    if (EditTextUtil.isNameCase(mEtxAddOrder.getText().toString()) == 1) {
                        mEtxAddOrder.setError(getResources().getString(R.string.name_error));
                        return;

                    }

                    if (EditTextUtil.isNameCase(mEtxAddOrder.getText().toString()) == 2) {
                        mEtxAddOrder.setError(getResources().getString(R.string.name_error_char));
                        return;

                    }

                if (isInterActivity) {
                        Log.d(TAG,"requestOtder Base ");

                  mOrderPresenter.requestAddOrder(mEtxAddOrder.getText().toString());

                }else {
                    Log.d(TAG,"requestOtder Rest ");

                    mOrderPresenter.requestAddRestOrder(mEtxAddOrder.getText().toString());

                }


                 //   mOrderPresenter.requestSendOrderMessag();


                break;
            case R.id.rv_direct_call:

                break;
            case R.id.rv_whatsapp:

                break;

        }
    }
}
