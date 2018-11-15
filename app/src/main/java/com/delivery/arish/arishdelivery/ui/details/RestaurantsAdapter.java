package com.delivery.arish.arishdelivery.ui.details;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.mvp.View.OnMainItemListClickListener;
import com.delivery.arish.arishdelivery.mvp.View.OnRetaurantItemClickListner;
import com.delivery.arish.arishdelivery.mvp.model.RestaurantModel;
import com.delivery.arish.arishdelivery.util.ImageLoaderHelper;
import com.delivery.arish.arishdelivery.util.LangUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.Holder> {

    // --Commented out by Inspection (03/11/18 02:01 م):private static final String TAG = DetailsAdapter.class.toString();
    private final ArrayList<RestaurantModel> mDetailsModels;
    private OnRetaurantItemClickListner onRetaurantItemClickListner;
    private final LayoutInflater mLayoutInflater;

    public RestaurantsAdapter(ArrayList<RestaurantModel> detailsModelArrayList, LayoutInflater inflater) {
        this.mDetailsModels = detailsModelArrayList;
        mLayoutInflater = inflater;
    }

    @SuppressWarnings("unused")
    @NonNull
    @Override
    public RestaurantsAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, @SuppressWarnings("unused") int viewType) {
        View view = mLayoutInflater.inflate(R.layout.rest_list_item, parent, false);

        return new RestaurantsAdapter.Holder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressWarnings("unused")
    @Override
    public void onBindViewHolder(@NonNull RestaurantsAdapter.Holder holder, int position) {

        holder.bind(mDetailsModels.get(position), position);
    }

    @SuppressWarnings("unused")
    @Override
    public int getItemCount() {
        return mDetailsModels.size();
    }

    //create interface to goo another activity
    public void setOnRetaurantItemClickListner(OnRetaurantItemClickListner listener) {
        onRetaurantItemClickListner = listener;
    }


    @SuppressWarnings("unused")
    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Context mContext;
        @BindView(R.id.name_rest_item)
        protected TextView titleView;
        @BindView(R.id.image_rest_item)
        protected ImageView mNetworkImageView;
        @BindView(R.id.LinearListRestContainer)
        protected LinearLayout mLinearListContainer;


        public Holder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @SuppressWarnings("deprecation")
        public void bind(RestaurantModel detailsModel, int position) {
            String lang = LangUtil.getCurrentLanguage(mContext);
            if (lang.equals("ar")) {
                titleView.setText(detailsModel.getAr_name());

            } else {
                titleView.setText(detailsModel.getEn_name());

            }
            if (detailsModel.getImage_url() != null) {
                ImageLoaderHelper.getInstance(mContext).getImageLoader()
                        .get(detailsModel.getImage_url(), new ImageLoader.ImageListener() {
                            @SuppressWarnings("deprecation")
                            @Override
                            public void onResponse(ImageLoader.ImageContainer imageContainer, boolean b) {
                                Bitmap bitmap = imageContainer.getBitmap();
                                if (bitmap != null) {
                                    mNetworkImageView.setImageBitmap(bitmap);//show by Bitmap
                                    Palette p = Palette.generate(bitmap, 15000000);
                                    int mMutedColor = p.getDarkMutedColor(0xFF333333);
                                    mLinearListContainer
                                            .setBackgroundColor(mMutedColor);

                                }
                            }

                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                            }
                        });
                //    titleView.setText(detailsModel.getEn_name());


                //    Bitmap bitmap = thumbnail.getDrawingCache();



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
            if (onRetaurantItemClickListner != null) {
                onRetaurantItemClickListner.onlItemClick( getAdapterPosition(), mDetailsModels.get(getLayoutPosition()).getId()
                ,mDetailsModels.get(getLayoutPosition()).getImage_url());
            }
        }
    }


}
