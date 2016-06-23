package com.app.bedomax.tagadata.adapters;

/**
 * Created by Jorge on 22/06/16.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.app.bedomax.tagadata.R;

import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context context;
    //private List<Movie> moviesList;
    private View.OnClickListener listener;


    public NewsRecyclerAdapter(Context context, List<String> moviesList) {
        this.context = context;
        //this.moviesList = moviesList;
    }
    @Override
    public void onClick(View v) {
        if (listener != null)
            listener.onClick(v);
    }


    public void setClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View view) {
            super(view);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        RecyclerView.ViewHolder vh;
        //v = LayoutInflater.from(context).inflate(R.layout.movie_list_content, parent, false);
        vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
           /* Picasso.with(context)
                    .load(context.getString(R.string.images_url_500)+moviesList.get(position).getPosterPath())
                    .into(((ViewHolder) holder).cover);
            ((ViewHolder) holder).cover.setTag(moviesList.get(position));*/
        }
    }
    @Override
    public int getItemCount() {
        //return moviesList.size();
        return 0;
    }
}