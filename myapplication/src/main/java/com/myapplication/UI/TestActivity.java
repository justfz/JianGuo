package com.myapplication.UI;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.myapplication.Cookie.MyApplication;
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

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        final WebView webView_test= (WebView) findViewById(R.id.webview_test);
        OkHttpClient client = OKHttpUtils.getClient();
        Request request=new Request.Builder()
                .url("http://newjwglxt.jiea.cn/jiaowu_system/JWXS/ksap/jwxs_ksap_like.aspx")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String content = response.body().string();
                final Element table = Jsoup.parse(content).getElementById("mxh");
                if (table==null){
                    OKHttpUtils.getCookiesManager();
                    startActivity(new Intent(MyApplication.getContext(),LoginActivity.class));
                }else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView_test.setBackgroundColor(Color.parseColor("#F9F9F9"));
                            webView_test.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                            webView_test.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
                            webView_test.requestFocus();
                            webView_test.loadDataWithBaseURL(null, content,"text/html", "utf-8", null);
                        }
                    });
                }
            }
        });
    }
}
