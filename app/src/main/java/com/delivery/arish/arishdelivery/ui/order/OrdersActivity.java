package com.delivery.arish.arishdelivery.ui.order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.data.Contract;
import com.delivery.arish.arishdelivery.mvp.model.DetailsModel;

import java.util.ArrayList;

import butterknife.BindView;

public class OrdersActivity extends AppCompatActivity {

    @BindView(R.id.img_menu)
    protected ImageView mImgMenu;
    @BindView(R.id.btn_add_order)
    protected Button mAddOrder;
    @BindView(R.id.etx_add_order)
    protected TextView mEtxAddOrder;
    @BindView(R.id.rv_direct_call)
    protected RelativeLayout mDirectCall;
    @BindView(R.id.rv_whatsapp)
    protected RelativeLayout mWhatsApp;

    private ArrayList<DetailsModel> mDetailsModelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        Intent intent = getIntent();
        if(intent==null){

        }else {
            int id = intent.getIntExtra(Contract.EXTRA_MAIN_LIST_POSITION,0);
            mDetailsModelArrayList=intent.getParcelableArrayListExtra(Contract.EXTRA_DETAILS_LIST);
            if(id==0){
                mImgMenu.setVisibility(View.VISIBLE);
                mImgMenu.setImageResource(mDetailsModelArrayList.get(id).getImage());
            }
        }


    }
}
