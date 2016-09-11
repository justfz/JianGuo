package com.jianguo.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jianguo.R;

public class URLActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(webView.canGoBack())
                {
                    webView.goBack();//返回上一页面
                }
                else
                {
                    onBackPressed();
                }
            }
        });
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        int type=getIntent().getIntExtra("type",0);
        webView = (WebView) findViewById(R.id.url_webview);
        switch (type){
            case 0:
                //链接
                String herf = getIntent().getStringExtra("herf");
                webView.loadUrl(herf);
                break;
            case 1:
                //content
                String content = getIntent().getStringExtra("content");
                webView.loadDataWithBaseURL(null, content,"text/html", "utf-8", null);
                break;
        }
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
        webView.requestFocus();
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode()==KeyEvent.KEYCODE_BACK)
        {
            if(webView.canGoBack())
            {
                webView.goBack();//返回上一页面
                return true;
            }
            else
            {
                onBackPressed();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
