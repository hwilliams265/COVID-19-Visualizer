package com.example.covid_19visualizer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.covid_19visualizer.ui.main.NewsFragment;
import com.squareup.picasso.Picasso;

public class NewsInDetails extends AppCompatActivity {
    TextView newsTitle,newsSource,newsTime,newsDesc;
    ImageView imageView;
    WebView webView;
    ProgressBar loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_in_details);
        newsTitle=findViewById(R.id.newsTitle);
        newsSource=findViewById(R.id.Source);
        newsTime=findViewById(R.id.newsDate);
        webView=findViewById(R.id.webView);
        imageView=findViewById(R.id.imageView);
        loader = findViewById(R.id.loader);
        loader.setVisibility(View.VISIBLE);
        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        String source=intent.getStringExtra("source");
        String time=intent.getStringExtra("time");
        String imageUrl=intent.getStringExtra("imageUrl");
        String url=intent.getStringExtra("url");



        newsTitle.setText(title);
        newsSource.setText(source);
        newsTime.setText(time);

       Picasso.get().load(imageUrl).into(imageView);

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        if(webView.isShown()){
            loader.setVisibility(View.INVISIBLE);
        }
    }


}



