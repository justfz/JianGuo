package com.jianguo.news.widget;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jianguo.R;
import com.jianguo.beans.NewsBean;
import com.jianguo.common.DisplayUtil;
import com.jianguo.news.presenter.NewsDetailPresenter;
import com.jianguo.news.presenter.NewsDetailPresenterImpl;
import com.jianguo.news.view.NewsDeatil;



/**
 * Created by ifane on 2016/5/27 0027.
 */
public class NewsDetailActivity extends AppCompatActivity implements NewsDeatil{

    private NewsBean mNewsBean;
    private NewsDetailPresenter newsDetailPresenter;
    private WebView webView;
    private ProgressBar progressBar;
    private ImageView imageView;
    private NestedScrollView scrollView;
    private TextView news_title;
    private TextView news_date;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //当Android版本大于5.0,状态栏透明
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_detail_md);
        Toolbar toolbar= (Toolbar) findViewById(R.id.detail_tb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mNewsBean = (NewsBean) getIntent().getSerializableExtra("news");
        scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        //当滑动新闻时，移动图片，有联动效果。
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                imageView.setTranslationY(Math.max(-scrollY / 2, -DisplayUtil.dip2px(getBaseContext(), 170)));
            }
        });
        frameLayout = (FrameLayout) findViewById(R.id.main_content);
        news_title = (TextView) findViewById(R.id.news_title);
        news_title.setText(mNewsBean.getNews_title());
        news_date = (TextView) findViewById(R.id.news_date);
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
        final int darkMutedColor = Palette.from(bitmap).generate().getDarkMutedColor(0xfff);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                frameLayout.setBackgroundColor(darkMutedColor);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void addNewsDate(final String newsDate) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                news_date.setText(newsDate);
            }
        });
    }
}
