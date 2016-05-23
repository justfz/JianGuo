package com.myapplication.UI;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.myapplication.Cookie.MyApplication;
import com.myapplication.R;
import com.myapplication.Utils.OKHttpUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        final WebView webView_score = (WebView) findViewById(R.id.webview_score);
        OkHttpClient client = OKHttpUtils.getClient();
        Request request=new Request.Builder()
                .url("http://newjwglxt.jiea.cn/jiaowu_system/JWXS/cjcx/jwxs_cjcx_like.aspx")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String content = response.body().string();
                System.out.println(content);
                final Element cjxx = Jsoup.parse(content).getElementById("cjxx");

                if (cjxx==null){
                    //说明cookie失效了，需要重新登录
                    OKHttpUtils.getCookiesManager();
                    startActivity(new Intent(MyApplication.getContext(),LoginActivity.class));
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView_score.setBackgroundColor(Color.parseColor("#F9F9F9"));

                            webView_score.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                            webView_score.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
                            webView_score.requestFocus();
                            webView_score.loadDataWithBaseURL(null,content,"text/html", "utf-8", null);
                        }
                    });
                }
            }
        });
    }
}
