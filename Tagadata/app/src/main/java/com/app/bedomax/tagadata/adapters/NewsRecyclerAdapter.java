package com.app.bedomax.tagadata.adapters;

/**
 * Created by Jorge on 22/06/16.
 */
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.bedomax.tagadata.R;
import com.app.bedomax.tagadata.models.New;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context context;
    private List<New> newsList;
    private View.OnClickListener listener;
    private OnLoadMoreListener onLoadMoreListener;
    private static int width;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private boolean loading;
    private int visibleThreshold = 2;

    @Override
    public int getItemViewType(int position) {
        if(newsList.size()>0 && position < newsList.size())
            return newsList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
        else{
            return VIEW_PROG;
        }

    }


    public NewsRecyclerAdapter(Context context, List<New> news, RecyclerView recyclerView) {
        this.context = context;
        this.newsList = news;
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

        public ViewHolder(View view) {
            super(view);
            newsTitle = (TextView) view.findViewById(R.id.newTitle);
            newsPreviewImage = (SimpleDraweeView) view.findViewById(R.id.previewImage);
        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        RecyclerView.ViewHolder vh;
        if(viewType == VIEW_ITEM) {
            v = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
            vh = new ViewHolder(v);
        }else{
            v = LayoutInflater.from(context).inflate(R.layout.progressbar_item, parent, false);
            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
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
           /* Picasso.with(context)
                    .load(context.getString(R.string.images_url_500)+moviesList.get(position).getPosterPath())
                    .into(((ViewHolder) holder).cover);
            ((ViewHolder) holder).cover.setTag(moviesList.get(position));*/
        }
    }
    @Override
    public int getItemCount() {
        //return moviesList.size();
        return newsList.size()+1;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }
}