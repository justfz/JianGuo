package com.myapplication.UI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.myapplication.R;
import com.myapplication.Utils.OKHttpUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ifane on 2016/5/16 0016.
 */
public class News_Content_Activity extends AppCompatActivity {

    private WebView webView;
    private String img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);
        webView = (WebView) findViewById(R.id.webview);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String news_content = extras.getString("news_content");
        System.out.println(news_content);
        //开始抓取
        OkHttpClient client = OKHttpUtils.getClient();
        Request request=new Request.Builder()
                .url(news_content)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String content = response.body().string();
                final String Content_Title=Jsoup.parse(content).select("h1.atit").text();
                final String Content_Content=Jsoup.parse(content).getElementById("content").text();
                Element content1 = Jsoup.parse(content).getElementById("content");
                final String web_conten=content1.toString();
                img = web_conten.replace("src=\"","src=\"http://www.jiea.cn");
                img = img.concat("<style type=\"text/css\">img { max-width: 70%;}</style>");
                System.out.println(img);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       setTitle(Content_Title);
                        webView.setBackgroundColor(Color.parseColor("#F9F9F9"));
                        webView.loadDataWithBaseURL(null, img,"text/html", "utf-8", null);
                        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                        webView.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
                        webView.requestFocus();
                    }
                });
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack())
        {
            webView.goBack();//返回webView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
