package com.app.bedomax.tagadata.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.app.bedomax.tagadata.R;
import com.app.bedomax.tagadata.adapters.NewsRecyclerAdapter;
import com.app.bedomax.tagadata.handlers.HandlerCallback;
import com.app.bedomax.tagadata.handlers.NewsHandler;
import com.app.bedomax.tagadata.models.NewModel;
import com.app.bedomax.tagadata.services.ListProvider;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

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
                if (handler.getNews().size() > 0)
                    if (handler.getNews().get(handler.getNews().size() - 1) != null) {
                            handler.getPager().passPage();
                            handler.getNews().add(null);
                            adapter.notifyItemInserted(handler.getNews().size() - 1);
                            handler.searchNews(searchNews);
                    }
            }
        });

        adapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewModel newObject = (NewModel) v.getTag();
                startActivity(getDataToIntent(newObject));
            }
        });

        mainNewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(getDataToIntent(handler.getMainNew()));
            }
        });
    }

    @NonNull
    private Intent getDataToIntent(NewModel newObject) {
        Intent intent = new Intent(getApplicationContext(), DescriptionActivity.class);
        intent.putExtra(getString(R.string.urlWord), newObject.getUrl());
        intent.putExtra(getString(R.string.titleWord), newObject.getName());
        intent.putExtra(getString(R.string.newWord), newObject);
        return intent;
    }

    public HandlerCallback.listener searchNews = new HandlerCallback.listener() {
        @Override
        public void onResponse(int status) {
                ListProvider.newsList = (ArrayList<NewModel>) handler.getNews();
                adapter.notifyDataSetChanged();
                mainNewImage.setImageURI(Uri.parse(handler.getMainNew().getImage()));
                profileToobar.setTitle(handler.getMainNew().getName());
                setSupportActionBar(profileToobar);
                adapter.setLoaded();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()){
            case R.id.favorite:
                intent = new Intent(getApplicationContext(),FavoriteResultActivity.class);
                startActivity(intent);
                return true;
            case R.id.aboutUs:
                intent = new Intent(getApplicationContext(),AboutUsActivity.class);
                startActivity(intent);
                return true;
            case R.id.update:
                updateNews();
                return true;
            default:
                return false;
        }
    }

    private void updateNews(){
        handler.getNews().clear();
        handler.getPager().resetPage();
        handler.searchNews(searchNews);
    }
}
