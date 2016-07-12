package com.app.bedomax.tagadata.adapters;

/**
 * Created by Jorge on 22/06/16.
 */
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


    public NewsRecyclerAdapter(Context context, List<New> news) {
        this.context = context;
        this.newsList = news;
    }
    @Override
    public void onClick(View v) {
        if (listener != null)
            listener.onClick(v);
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        RecyclerView.ViewHolder vh;
        v = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {

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
        return newsList.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }
}