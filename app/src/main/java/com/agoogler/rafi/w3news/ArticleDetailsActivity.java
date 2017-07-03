package com.agoogler.rafi.w3news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class ArticleDetailsActivity extends AppCompatActivity {
    public static final String KEY_URL = "key_url";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_details_activity);

        Intent intent = getIntent();
        String url = intent.getStringExtra(KEY_URL);

        ((WebView)findViewById(R.id.activity_detail_webivew)).loadUrl(url);
    }
}
