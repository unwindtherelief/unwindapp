package com.depression.relief.depressionissues.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.depression.relief.depressionissues.R;

public class GamesListActivity extends AppCompatActivity {
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);

        webView = findViewById(R.id.webView);

        // Enable JavaScript (optional, depending on the website)
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Set a WebViewClient to handle events inside the WebView
        webView.setWebViewClient(new WebViewClient());

        // Load the website URL
        webView.loadUrl("https://www.cartoonnetworkhq.com/games/teen-titans-go-ninja-run/play");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
            Animatoo.INSTANCE.animateCard(GamesListActivity.this);
        }
    }
}