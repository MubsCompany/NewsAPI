package com.example.newsapi;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebCustom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.web_view );

       class MyWebViewClient extends WebViewClient {
           @Override
           public boolean shouldOverrideUrlLoading(WebView view, String url) {
               if (Uri.parse( url ).getHost().equals( getIntent().getStringExtra( "url" ) ))
                   return false;
               else
               return super.shouldOverrideUrlLoading( view, url );
           }
       }

       android.webkit.WebView webView = findViewById( R.id.web_view );
       webView.getSettings().setJavaScriptEnabled( true );
       webView.loadUrl( getIntent().getStringExtra( "url" ) );
       webView.setWebViewClient( new MyWebViewClient() );
    }
}
