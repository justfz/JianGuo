package com.jianguo.news.widget;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.jianguo.R;
import com.jianguo.beans.NewsBean;
import com.jianguo.news.presenter.NewsDetailPresenter;
import com.jianguo.news.presenter.NewsDetailPresenterImpl;
import com.jianguo.news.view.NewsDeatil;



/**
 * Created by ifane on 2016/5/27 0027.
 */
public class NewsDetailActivity extends AppCompatActivity implements NewsDeatil{

    private NewsBean mNewsBean;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private NewsDetailPresenter newsDetailPresenter;
    private WebView webView;
    private ProgressBar progressBar;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mNewsBean = (NewsBean) getIntent().getSerializableExtra("news");
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(mNewsBean.getNews_title());
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.toolbar_size);
        webView= (WebView) findViewById(R.id.webview);
        newsDetailPresenter = new NewsDetailPresenterImpl(this);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        imageView = (ImageView) findViewById(R.id.ivImage);
        newsDetailPresenter.loadDetailView(mNewsBean.getHref());
    }

    @Override
    public void addNewsDetailContent(final String newsDetailContent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                webView.loadDataWithBaseURL(null, newsDetailContent,"text/html", "utf-8", null);
                webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                webView.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
                webView.requestFocus();
            }
        });
    }

    @Override
    public void addNewsDetailImage(final Bitmap bitmap) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}
