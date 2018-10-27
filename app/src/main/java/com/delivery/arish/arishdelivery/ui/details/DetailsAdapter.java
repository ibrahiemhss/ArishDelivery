package com.delivery.arish.arishdelivery.ui.details;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.mvp.View.OnItemListClickListener;
import com.delivery.arish.arishdelivery.mvp.model.DetailsModel;
import com.delivery.arish.arishdelivery.mvp.model.MainModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.Holder> {

    private static final String TAG = DetailsAdapter.class.toString();
    private ArrayList<DetailsModel> mDetailsModels=new ArrayList<>();;
    private OnItemListClickListener onItemListClickListener;
    private final LayoutInflater mLayoutInflater;
    private int mMutedColor = 0xFF333333;


    public DetailsAdapter(ArrayList<DetailsModel> detailsModelArrayList, LayoutInflater inflater) {
        this.mDetailsModels=detailsModelArrayList;
        mLayoutInflater = inflater;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.details_list_item, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.bind(mDetailsModels.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mDetailsModels.size();
    }

    //create interface to goo another activity
    public void setOnItemListClickListener(OnItemListClickListener listener) {
        onItemListClickListener = listener;
    }



    @SuppressWarnings("unused")
    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Context mContext;
        @BindView(R.id.name_details_item)
        protected  TextView titleView;
        @BindView(R.id.image_details_item)
        protected ImageView thumbnail;
        @BindView(R.id.LinearListContainer)
        protected LinearLayout LinearListContainer;

        public Holder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void bind(DetailsModel detailsModel, int position) {
            titleView.setText(detailsModel.getName());

            Glide.with(mContext)
                    .load("null value")
                    .apply(new RequestOptions()
                            .placeholder(detailsModel.getImage()))
                    .into(thumbnail);

          thumbnail.buildDrawingCache();
            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),detailsModel.getImage());

        //    Bitmap bitmap = thumbnail.getDrawingCache();
            if (bitmap != null) {
                Palette p = Palette.generate(bitmap, 15000000);
                mMutedColor = p.getDarkMutedColor(0xFF333333);
                LinearListContainer
                        .setBackgroundColor(mMutedColor);



               /* Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        int bgColor = palette.getMutedColor(mContext.getResources().getColor(android.R.color.black));
                        LinearListContainer.setBackgroundColor(bgColor);
                    }
                });*/
            }

        }


        @Override
        public void onClick(View view) {
            if (onItemListClickListener != null) {
                onItemListClickListener.onlItemClick(getAdapterPosition());
            }
        }
    }
}


