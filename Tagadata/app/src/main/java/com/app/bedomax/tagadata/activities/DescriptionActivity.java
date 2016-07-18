package com.app.bedomax.tagadata.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.app.bedomax.tagadata.R;
import com.app.bedomax.tagadata.models.New;
import com.app.bedomax.tagadata.utils.SharedPreferencesUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jorge on 13/07/16.
 */
public class DescriptionActivity extends AppCompatActivity {

    @InjectView(R.id.webView)
    protected WebView webView;

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    private MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_description_activity);
        ButterKnife.inject(this);
        loadWebViewLoad(webView);
        toolbar.setTitle(getIntent().getStringExtra(getString(R.string.titleWord)));
        toolbar.setNavigationIcon(R.drawable.left_arrow_icon);
        setSupportActionBar(toolbar);
    }

    private void loadWebViewLoad(WebView webview) {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setSupportMultipleWindows(true);
        webview.setWebViewClient(new WebViewClient());
        webview.setWebChromeClient(new WebChromeClient());
        webview.loadUrl(getIntent().getStringExtra(getString(R.string.urlWord)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.favorite_menu, menu);
        item = menu.findItem(R.id.favorite);
        if (item != null) {
            if (isFavorite())
                item.setIcon(R.drawable.favorited_icon);
            else
                item.setIcon(R.drawable.favorite_icon);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite:
                saveOrDeleteFavorites();
                if (isFavorite()) {
                    item.setIcon(R.drawable.favorited_icon);
                } else {
                    item.setIcon(R.drawable.favorite_icon);
                }

                return true;
            default:
                finish();
                return true;
        }
    }

    private boolean isFavorite() {
        ArrayList<New> favorites = SharedPreferencesUtil.obtainFavorites(this);
        if (favorites == null) {
            favorites = new ArrayList<>();
        }
        New object = getIntent().getParcelableExtra(getString(R.string.newWord));

        if (favorites.contains(object)) {
            return true;
        }
        return false;
    }

    private void saveOrDeleteFavorites() {
        ArrayList<New> favorites = SharedPreferencesUtil.obtainFavorites(this);
        if (favorites == null) {
            favorites = new ArrayList<>();
        }
        New object = getIntent().getParcelableExtra(getString(R.string.newWord));

        if (isFavorite()) {
            favorites.remove(object);
        } else {
            favorites.add(object);
        }
        SharedPreferencesUtil.saveFavorites(this, favorites);
    }

    public void share(View v) {
        New object = getIntent().getParcelableExtra(getString(R.string.newWord));

            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_SUBJECT, object.getName());
            share.putExtra(Intent.EXTRA_TEXT, object.getUrl());
            startActivity(Intent.createChooser(share, "Comparte el enlace!"));
    }

}
