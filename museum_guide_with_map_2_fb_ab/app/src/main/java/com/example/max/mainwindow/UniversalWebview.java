package com.example.max.mainwindow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class UniversalWebview extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universal_webview);
        webView=findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");
        String passkey = intent.getStringExtra("Passkey");
        webView.getSettings().setBuiltInZoomControls(true);
        switch (passkey){
            case "News":
                setTitle("Новости на Znak.com");
                webView.loadUrl(url);
                break;
            case "Museum":
                setTitle("Музей");
                webView.loadUrl(url);
                break;
        }

        webView.setWebViewClient(new MyWebViewClient());
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }
    }

}
