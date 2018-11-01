package com.delivery.arish.arishdelivery.ui.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.base.BaseActivity;
import com.delivery.arish.arishdelivery.data.Contract;
import com.delivery.arish.arishdelivery.mvp.model.DetailsModel;
import com.delivery.arish.arishdelivery.ui.Main.MainActivity;
import com.delivery.arish.arishdelivery.ui.details.DetailsActivity;
import com.delivery.arish.arishdelivery.util.CustomImageVIew;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnTouch;
import uk.co.senab.photoview.PhotoViewAttacher;

public class OrdersActivity extends BaseActivity {

    @BindView(R.id.img_menu)
    protected ImageView mImgMenu;
    @BindView(R.id.rv_container_orders)
    protected RelativeLayout mRvContainerOrders;
    @BindView(R.id.btn_add_order)
    protected Button mAddOrder;
    @BindView(R.id.etx_add_order)
    protected TextView mEtxAddOrder;
    @BindView(R.id.rv_direct_call)
    protected RelativeLayout mDirectCall;
    @BindView(R.id.rv_whatsapp)
    protected RelativeLayout mWhatsApp;
    @BindView(R.id.order_toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.arraw_down)
    protected ImageView mArrawDown;
    @BindView(R.id.arraw_upp)
    protected ImageView mArrawUpp;

    private boolean isHide;
    private boolean isInterActivity=true;
    private int mPosition;
    private ArrayList<DetailsModel> mDetailsModelArrayList;

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_orders;
    }

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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void init() {


        Bundle extras = getIntent().getExtras();
        assert extras != null;
        if(extras.containsKey(Contract.EXTRA_DETAILS_LIST_POSITION)
                &&extras.containsKey(Contract.EXTRA_DETAILS_LIST)){
            mPosition = extras.getInt(Contract.EXTRA_DETAILS_LIST_POSITION,0);
            mDetailsModelArrayList=
                    extras.getParcelableArrayList(Contract.EXTRA_DETAILS_LIST);
            mArrawDown.setVisibility(View.VISIBLE);


            mImgMenu.setImageResource(mDetailsModelArrayList.get(mPosition).getImage());
            mRvContainerOrders.setElevation(12);
            mRvContainerOrders.setBackgroundColor(getResources().getColor(R.color.white));
            PhotoViewAttacher photoAttacher;
            photoAttacher= new PhotoViewAttacher(mImgMenu);
            photoAttacher.update();
           // isHide=true;

        }else if(extras.containsKey(Contract.EXTRA_INTER_ACTIVITY)) {
            isInterActivity=extras.getBoolean(Contract.EXTRA_INTER_ACTIVITY);
            mArrawDown.setVisibility(View.GONE);
            mArrawUpp.setVisibility(View.GONE);
        }
    }

    @Override
    protected void setListener() {


        mArrawDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideDown(mRvContainerOrders);
                mArrawUpp.setVisibility(View.VISIBLE);
            }
        });

        mArrawUpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideUp(mRvContainerOrders);
                mArrawUpp.setVisibility(View.GONE);

            }
        });
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
                Intent intent ;
                if(isInterActivity){
                    intent= new Intent( OrdersActivity.this, DetailsActivity.class );
                }else {
                    intent= new Intent( OrdersActivity.this, MainActivity.class );
                }

                startActivity( intent );
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent ;
        if(isInterActivity){
            intent= new Intent( OrdersActivity.this, DetailsActivity.class );
        }else {
            intent= new Intent( OrdersActivity.this, MainActivity.class );
        }

        startActivity( intent );
    }


    public void slideUp(View view){
        view.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.push_up_in));
        mRvContainerOrders.setVisibility(View.VISIBLE);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view){
        view.startAnimation(AnimationUtils.loadAnimation(this,
                R.anim.push_down_out));
        mRvContainerOrders.setVisibility(View.GONE);

    }


}
