package com.delivery.arish.arishdelivery.ui.Main;

/*
 * Created by ibrahim on 13/07/18.
 */

public class ArticlListAdapter{ /*extends RecyclerView.Adapter<ViewHolder> {

    private static final String TAG = ArticlListAdapter.class.toString();
    private List<MainModel> mMainModelsList;

    private OnMainItemListClickListener onMainItemListClickListener;


    private final Integer[] imgId;
    private final LayoutInflater mLayoutInflater;

    private int mMutedColor = 0xFF333333;

    public ArticlListAdapter(Integer[] imgId, LayoutInflater inflater) {
        this.imgId = imgId;
        mLayoutInflater = inflater;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.bind(mMainModelsList.get(position), position);

        MainModel mainModel = mMainModelsList.get(position);


        holder.titleView.setText(mainModel.getGenre());
        holder.thumbnail.setImageDrawable(mCtx.getDrawable(R.drawable.empty_detail));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onlItemClick(getItemId(holder.getAdapterPosition()));
            }
        });
        holder.thumbnail.buildDrawingCache();



    }

    @Override
    public int getItemCount() {
        return 0;
    }


}

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final Context mContext;
    @BindView(R.id.article_title)
    public  TextView titleView;
    @BindView(R.id.article_subtitle)
    public  TextView subtitleView;
    @BindView(R.id.thumbnail)
    protected ImageView thumbnail;
    @BindView(R.id.LinearListContainer)
    public  LinearLayout LinearListContainer;

    public ViewHolder(View view) {
        super(view);
        this.mContext = view.getContext();
        ButterKnife.bind(mContext,view);

    }

    public void bind(MainModel mainModel, int position) {
        titleView.setText(mainModel.getGenre());
        thumbnail.setImageResource(imgId[position]);

        thumbnail.buildDrawingCache();

        Bitmap bitmap = thumbnail.getDrawingCache();
        if (bitmap != null) {
            Palette p = Palette.generate(bitmap, 12);
            mMutedColor = p.getDarkMutedColor(0xFF333333);
            LinearListContainer
                    .setBackgroundColor(mMutedColor);
        }
    }


    @Override
    public void onClick(View view) {
        if (onMainItemListClickListener != null) {
            onMainItemListClickListener.onClick(getAdapterPosition());
        }
    }*/

}