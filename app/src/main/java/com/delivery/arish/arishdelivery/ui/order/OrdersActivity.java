package com.delivery.arish.arishdelivery.ui.order;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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

import java.util.ArrayList;

import butterknife.BindView;

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

    private boolean isInterActivity=true;

    private int mPosition;
    private ArrayList<DetailsModel> mDetailsModelArrayList;

    @Override
    protected int getResourceLayout() {
        return R.layout.activity_orders;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void init() {

        setupToolbar();

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        if(extras.containsKey(Contract.EXTRA_DETAILS_LIST_POSITION)
                &&extras.containsKey(Contract.EXTRA_DETAILS_LIST)){
            mPosition = extras.getInt(Contract.EXTRA_DETAILS_LIST_POSITION,0);
            mDetailsModelArrayList=
                    extras.getParcelableArrayList(Contract.EXTRA_DETAILS_LIST);
                mImgMenu.setImageResource(mDetailsModelArrayList.get(mPosition).getImage());
            mRvContainerOrders.setElevation(12);
            mRvContainerOrders.setBackgroundColor(getResources().getColor(R.color.white));


        }else if(extras.containsKey(Contract.EXTRA_INTER_ACTIVITY)) {
            isInterActivity=extras.getBoolean(Contract.EXTRA_INTER_ACTIVITY);
        }


    }
    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            mToolbar.setTitle(getResources().getString(R.string.app_name));


        }
    }
    @Override
    protected void setListener() {

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
}
