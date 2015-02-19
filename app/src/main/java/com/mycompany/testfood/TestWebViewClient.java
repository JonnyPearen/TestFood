package com.mycompany.testfood;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/*
Webview client for the test webview that displays the database.
 */
public class TestWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}