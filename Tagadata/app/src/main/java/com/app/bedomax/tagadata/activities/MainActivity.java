package com.app.bedomax.tagadata.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.app.bedomax.tagadata.R;
import com.app.bedomax.tagadata.adapters.NewsRecyclerAdapter;
import com.app.bedomax.tagadata.handlers.HandlerCallback;
import com.app.bedomax.tagadata.handlers.NewsHandler;
import com.app.bedomax.tagadata.models.New;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {


    @InjectView(R.id.mainNewImage)
    protected SimpleDraweeView mainNewImage;
    @InjectView(R.id.newsRV)
    protected RecyclerView newsRV;
    @InjectView(R.id.profileToolbar)
    protected Toolbar profileToobar;
    private NewsHandler handler;
    private NewsRecyclerAdapter adapter;
    private GridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        handler = new NewsHandler(this);
        manager = new GridLayoutManager(getApplicationContext(), 2);
        newsRV.setLayoutManager(manager);
        adapter = new NewsRecyclerAdapter(this,handler.getNews(),newsRV);
        newsRV.setAdapter(adapter);
        handler.searchNews(searchNews);

        adapter.setOnLoadMoreListener(new NewsRecyclerAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
                if (handler.getNews().size() > 0)
                    if (handler.getNews().get(handler.getNews().size() - 1) != null) {
                            handler.getPager().passPage();
                            handler.getNews().add(null);
                            adapter.notifyItemInserted(handler.getNews().size() - 1);
                            handler.searchNews(searchNews);
                    }
            }
        });
    }

    public HandlerCallback.listener searchNews = new HandlerCallback.listener() {
        @Override
        public void onResponse(int status) {
                adapter.notifyDataSetChanged();
                mainNewImage.setImageURI(Uri.parse(handler.getMainNew().getImage()));
                profileToobar.setTitle(handler.getMainNew().getName());
                adapter.setLoaded();
        }
    };
}
