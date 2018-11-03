package com.delivery.arish.arishdelivery.ui.Main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.mvp.View.OnItemListClickListener;
import com.delivery.arish.arishdelivery.mvp.model.MainModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.Holder> {

    // --Commented out by Inspection (03/11/18 02:00 م):private static final String TAG = MainListAdapter.class.toString();
    private final ArrayList<MainModel> mMainModelsList;
    private OnItemListClickListener onItemListClickListener;
    private final LayoutInflater mLayoutInflater;


    public MainListAdapter(ArrayList<MainModel> mainModelArrayList, LayoutInflater inflater) {
        this.mMainModelsList=mainModelArrayList;
        mLayoutInflater = inflater;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.main_list_item, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.bind(mMainModelsList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mMainModelsList.size();
    }

    //create interface to goo another activity
    public void setOnItemListClickListener(OnItemListClickListener listener) {
        onItemListClickListener = listener;
    }



    @SuppressWarnings("unused")
    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Context mContext;
        @BindView(R.id.name_item)
        protected  TextView titleView;
        @BindView(R.id.image_item)
        protected CircleImageView thumbnail;
        @BindView(R.id.main_linear_item_list_container)
        protected LinearLayout LinearListContainer;


        public Holder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void bind(MainModel mainModel, int position) {
            titleView.setText(mainModel.getName());

            Glide.with(mContext)
                    .load("null value")
                    .apply(new RequestOptions()
                            .placeholder(mainModel.getImage()))
                    .into(thumbnail);

            thumbnail.buildDrawingCache();

            thumbnail.buildDrawingCache();

        }

        @Override
        public void onClick(View view) {
            if (onItemListClickListener != null) {
                onItemListClickListener.onlItemClick(getAdapterPosition());
            }
        }
    }
}

