package com.app.bedomax.tagadata.adapters;

/**
 * Created by Jorge on 22/06/16.
 */
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.bedomax.tagadata.R;
import com.app.bedomax.tagadata.models.NewModel;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context context;
    private List<NewModel> newsList;
    private View.OnClickListener listener;
    private OnLoadMoreListener onLoadMoreListener;
    private static int width;
    private final int VIEW_ITEM = 1;
    private final int VIEW_FAV_ITEM = 2;
    private final int VIEW_PROG = 0;
    private boolean loading;
    private int visibleThreshold = 2;
    private boolean isFavorite;

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public int getItemViewType(int position) {
        if(isFavorite){
            return VIEW_FAV_ITEM;
        }else{
            return newsList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
        }
    }


    public NewsRecyclerAdapter(Context context, List<NewModel> news, RecyclerView recyclerView, boolean ... isFavorite) {
        this.context = context;
        this.newsList = news;
        if(isFavorite.length>0)
        this.isFavorite = isFavorite[0];
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int totalItemCount = gridLayoutManager.getItemCount();
                    int lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }
    @Override
    public void onClick(View v) {
        if (listener != null)
            listener.onClick(v);
    }

    public void setLoaded() {
        loading = false;
    }


    private void getDimensions() {
        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
        width = displaymetrics.widthPixels / 3;
    }
    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView newsTitle;
        public SimpleDraweeView newsPreviewImage;
        public RelativeLayout holderRL;

        public ViewHolder(View view) {
            super(view);
            newsTitle = (TextView) view.findViewById(R.id.newTitle);
            newsPreviewImage = (SimpleDraweeView) view.findViewById(R.id.previewImage);
            holderRL = (RelativeLayout) view.findViewById(R.id.holderRL);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView photo;
        public TextView newTitle;
        public LinearLayout favoriteItemLL;

        public FavoriteViewHolder(View v) {
            super(v);
            photo = (SimpleDraweeView) v.findViewById(R.id.photo);
            newTitle = (TextView) v.findViewById(R.id.new_title);
            favoriteItemLL = (LinearLayout) v.findViewById(R.id.favoriteItemLL);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        RecyclerView.ViewHolder vh;
        if(viewType == VIEW_FAV_ITEM) {
            v = LayoutInflater.from(context).inflate(R.layout.favorite_item, parent, false);
            v.setOnClickListener(this);
            vh = new FavoriteViewHolder(v);
        }else
        if(viewType == VIEW_ITEM) {
            v = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
            v.setOnClickListener(this);
            vh = new ViewHolder(v);
        }else{
            v = LayoutInflater.from(context).inflate(R.layout.progressbar_item, parent, false);
            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof FavoriteViewHolder){
            ((FavoriteViewHolder) holder).newTitle.setText(newsList.get(position).getName());
            ImageRequest request;
            getDimensions();
            request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(newsList.get(position).getImage()))
                    .setResizeOptions(new ResizeOptions(55, 55))
                    .setAutoRotateEnabled(true)
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest((request))
                    .setOldController(((FavoriteViewHolder) holder).photo.getController())
                    .build();
            ((FavoriteViewHolder) holder).photo.setController(controller);
            (((FavoriteViewHolder) holder).favoriteItemLL).setTag(newsList.get(position));
        }else
        if(holder instanceof ProgressViewHolder){
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }else{
            ((ViewHolder) holder).newsTitle.setText(newsList.get(position).getName());
            ImageRequest request;
            getDimensions();
            request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(newsList.get(position).getImage()))
                    .setResizeOptions(new ResizeOptions(width, width))
                    .setAutoRotateEnabled(true)
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest((request))
                    .setOldController(((ViewHolder) holder).newsPreviewImage.getController())
                    .build();
            ((ViewHolder) holder).newsPreviewImage.setController(controller);
            ((ViewHolder) holder).holderRL.setTag(newsList.get(position));
        }
    }
    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }
}