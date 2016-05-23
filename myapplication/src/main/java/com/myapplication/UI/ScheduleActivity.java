package com.myapplication.UI;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.myapplication.Cookie.MyApplication;
import com.myapplication.R;
import com.myapplication.Utils.OKHttpUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ScheduleActivity extends AppCompatActivity {

    private WebView webView_schedule;
    private String url="http://newjwglxt.jiea.cn/jiaowu_system/JWXS/pkgl/XsKB_List.aspx";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        webView_schedule = (WebView) findViewById(R.id.webview_Schedule);
        OkHttpClient client = OKHttpUtils.getClient();
        RequestBody body= new FormBody.Builder()
                .add("ddlxnxqh","2015-2016-2")
                .add("__EVENTTARGET","ddlxnxqh")
                .build();
        Request request=new Request.Builder()
                .url(url)
                .get().post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String content = response.body().string();
                final Element kb = Jsoup.parse(content).getElementById("kb");
                Element label1 = Jsoup.parse(content).getElementById("Label1");
                System.out.println(label1);
                if (kb==null){
                    OKHttpUtils.getCookiesManager();
                    startActivity(new Intent(MyApplication.getContext(),LoginActivity.class));
                }else if (label1.text()!=""){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView_schedule.setBackgroundColor(Color.parseColor("#F9F9F9"));
                            webView_schedule.setWebViewClient(new WebViewClient(){
                                @Override
                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                    view.loadUrl(url);
                                    return true;
                                }
                            });
                            webView_schedule.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                            webView_schedule.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
                            webView_schedule.requestFocus();
                            webView_schedule.loadDataWithBaseURL(null, content,"text/html", "utf-8", null);
                        }
                    });
                }else {
                    Toast.makeText(MyApplication.getContext(),label1.text(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
