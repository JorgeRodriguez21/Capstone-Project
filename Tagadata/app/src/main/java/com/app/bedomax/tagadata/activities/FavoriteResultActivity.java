package com.app.bedomax.tagadata.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.app.bedomax.tagadata.R;
import com.app.bedomax.tagadata.adapters.NewsRecyclerAdapter;
import com.app.bedomax.tagadata.models.New;
import com.app.bedomax.tagadata.utils.SharedPreferencesUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jorge on 18/07/16.
 */
public class FavoriteResultActivity extends AppCompatActivity {

    @InjectView(R.id.favoriteItemsRV)
    protected RecyclerView favoriteItemsRV;

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    private NewsRecyclerAdapter newsRecyclerAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_result_activity);
        ButterKnife.inject(this);
        ArrayList <New> favoriteNews = SharedPreferencesUtil.obtainFavorites(this);
        toolbar.setTitle(R.string.favorites);
        toolbar.setNavigationIcon(R.drawable.left_arrow_icon);
        setSupportActionBar(toolbar);
        newsRecyclerAdapter = new NewsRecyclerAdapter(this, favoriteNews,favoriteItemsRV,true);
        newsRecyclerAdapter.setFavorite(true);
        newsRecyclerAdapter.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                New newObject = (New) v.getTag();
                getDataToIntent(newObject);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this);
        favoriteItemsRV.setLayoutManager(manager);
        favoriteItemsRV.setAdapter(newsRecyclerAdapter);
    }

    @NonNull
    private void getDataToIntent(New newObject) {
        Intent intent = new Intent(getApplicationContext(), DescriptionActivity.class);
        intent.putExtra(getString(R.string.urlWord), newObject.getUrl());
        intent.putExtra(getString(R.string.titleWord), newObject.getName());
        intent.putExtra(getString(R.string.newWord), newObject);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
