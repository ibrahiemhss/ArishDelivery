package com.delivery.arish.arishdelivery.ui.Main;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delivery.arish.arishdelivery.R;
import com.delivery.arish.arishdelivery.mvp.View.OnMainItemListClickListener;
import com.delivery.arish.arishdelivery.mvp.model.MainModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BakesAdapter extends RecyclerView.Adapter<BakesAdapter.Holder> {

    private static final String TAG = ArticlListAdapter.class.toString();
    private ArrayList<MainModel> mMainModelsList=new ArrayList<>();;
    private OnMainItemListClickListener onMainItemListClickListener;
    private final LayoutInflater mLayoutInflater;
    private int mMutedColor = 0xFF333333;


    public BakesAdapter(ArrayList<MainModel> mainModelArrayList, LayoutInflater inflater) {
        this.mMainModelsList=mainModelArrayList;
        mLayoutInflater = inflater;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_item, parent, false);

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
    public void setBakeClickListener(OnMainItemListClickListener listener) {
        onMainItemListClickListener = listener;
    }



    @SuppressWarnings("unused")
    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final Context mContext;
        @BindView(R.id.article_title)
        public  TextView titleView;
        @BindView(R.id.article_subtitle)
        public  TextView subtitleView;
        @BindView(R.id.thumbnail)
        protected ImageView thumbnail;
        @BindView(R.id.LinearListContainer)
        public LinearLayout LinearListContainer;

        public Holder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void bind(MainModel mainModel, int position) {
            titleView.setText(mainModel.getName());
            thumbnail.setImageResource(mainModel.getImage());

           /* thumbnail.buildDrawingCache();

            Bitmap bitmap = thumbnail.getDrawingCache();
            if (bitmap != null) {
                Palette p = Palette.generate(bitmap, 12);
                mMutedColor = p.getDarkMutedColor(0xFF333333);
                LinearListContainer
                        .setBackgroundColor(mMutedColor);
            }
*/
        }


        @Override
        public void onClick(View view) {
            if (onMainItemListClickListener != null) {
                onMainItemListClickListener.onlItemClick(getAdapterPosition());
            }
        }
    }
}

