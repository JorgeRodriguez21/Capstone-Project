package com.app.bedomax.tagadata.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.bedomax.tagadata.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Jorge on 18/07/16.
 */
public class AboutUsActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    protected Toolbar toolbar;

    @InjectView(R.id.webView)
    protected WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_activity);
        ButterKnife.inject(this);
        toolbar.setTitle(R.string.about_us);
        toolbar.setNavigationIcon(R.drawable.left_arrow_icon);
        setSupportActionBar(toolbar);
        loadWebViewLoad(webView);
    }

    private void loadWebViewLoad(WebView webview) {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setSupportMultipleWindows(true);
        webview.setWebViewClient(new WebViewClient());
        webview.setWebChromeClient(new WebChromeClient());
        webview.loadUrl(getString(R.string.about_us_url));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
