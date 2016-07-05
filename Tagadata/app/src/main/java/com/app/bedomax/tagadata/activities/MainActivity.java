package com.app.bedomax.tagadata.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.bedomax.tagadata.R;
import com.app.bedomax.tagadata.adapters.NewsRecyclerAdapter;
import com.app.bedomax.tagadata.handlers.HandlerCallback;
import com.app.bedomax.tagadata.handlers.NewsHandler;
import com.app.bedomax.tagadata.models.New;

public class MainActivity extends AppCompatActivity {

    private NewsHandler handler;
    private NewsRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new NewsHandler(this);
        adapter = new NewsRecyclerAdapter(this,handler.getNews());
        handler.searchNews(new HandlerCallback.listener() {
            @Override
            public void onResponse(int status) {
                for (New n : handler.getNews()){
                    System.out.println(n.getName());
                }
            }
        });

        adapter.setOnLoadMoreListener(new NewsRecyclerAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
               /* if (vehiclesList.size() > 0)
                    if (vehiclesList.get(vehiclesList.size() - 1) != null) {
                        if (pager.hasNextPage()) {
                            pager.passPage();
                            vehiclesList.add(null);
                            mAdapter.notifyItemInserted(vehiclesList.size() - 1);
                            customLoadMoreDataFromApi();
                        }
                    }*/
            }
        });
    }
}
