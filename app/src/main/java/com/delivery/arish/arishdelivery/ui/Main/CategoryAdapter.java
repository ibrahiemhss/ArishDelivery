package com.delivery.arish.arishdelivery.ui.Main;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.mvp.View.OnMainItemListClickListener;
import com.delivery.arish.arishdelivery.mvp.model.CategoryModel;
import com.delivery.arish.arishdelivery.util.ImageLoaderHelper;
import com.delivery.arish.arishdelivery.util.LangUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder> {

    // --Commented out by Inspection (03/11/18 02:01 م):private static final String TAG = DetailsAdapter.class.toString();
    private final ArrayList<CategoryModel> mDetailsModels;
    private OnMainItemListClickListener onMainItemListClickListener;
    private final LayoutInflater mLayoutInflater;

    public CategoryAdapter(ArrayList<CategoryModel> detailsModelArrayList, LayoutInflater inflater) {
        this.mDetailsModels = detailsModelArrayList;
        mLayoutInflater = inflater;
    }

    @SuppressWarnings("unused")
    @NonNull
    @Override
    public CategoryAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, @SuppressWarnings("unused") int viewType) {
        View view = mLayoutInflater.inflate(R.layout.main_list_item, parent, false);

        return new CategoryAdapter.Holder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressWarnings("unused")
    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.Holder holder, int position) {

        holder.bind(mDetailsModels.get(position), position);
    }

    @SuppressWarnings("unused")
    @Override
    public int getItemCount() {
        return mDetailsModels.size();
    }

    //create interface to goo another activity
    public void setOnMainItemListClickListener(OnMainItemListClickListener listener) {
        onMainItemListClickListener = listener;
    }


    @SuppressWarnings("unused")
    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Context mContext;
        @BindView(R.id.name_ctg_item)
        protected TextView titleView;
        @BindView(R.id.image_ctg_item)
        protected CircleImageView mNetworkImageView;

        public Holder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @SuppressWarnings("deprecation")
        public void bind(CategoryModel detailsModel, int position) {
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
            if (onMainItemListClickListener != null) {
                onMainItemListClickListener.onlItemClick( getAdapterPosition(), mDetailsModels.get(getLayoutPosition()).getId());
            }
        }
    }


}
