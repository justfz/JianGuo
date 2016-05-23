package com.myapplication.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.myapplication.MainActivity;
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

/**
 * Created by ifane on 2016/5/13 0013.
 */
public class LoginActivity extends AppCompatActivity{

    private EditText edit_checkcode;
    private AppCompatEditText edit_number;
    private AppCompatEditText edit_password;
    private ImageView image_checkcode;
    private Button button_login;
    private String viewstate;
    private String viewstategenerator;
    private String eventvalidation;
    private OkHttpClient client;
    private Button button_del;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //OKHttpUtils.getCookiesManager();
        setContentView(R.layout.activity_login);
        //初始化控件
        initView();
        //初始化Cookie
        client = OKHttpUtils.getClient();
        button_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OKHttpUtils.getCookiesManager();
            }
        });
        //获取html参数
        getparameter();
        //获取验证码
        getCheckCode();
        //开始登录
        image_checkcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCheckCode();
            }
        });
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody requestBody = new FormBody.Builder()
                        .add("__VIEWSTATE", viewstate)
                        .add("__VIEWSTATEGENERATOR", viewstategenerator)
                        .add("__EVENTVALIDATION", eventvalidation)
                        .add("Account", edit_number.getText().toString())
                        .add("PWD", edit_password.getText().toString())
                        .add("CheckCode", edit_checkcode.getText().toString())
                        .add("cmdok", "")
                        .build();
                //Request request=OKHttpUtils.getRequest(OKHttpUtils.getLoginurl(),requestBody);
                Request request = new Request.Builder()
                        .url("http://newjwglxt.jiea.cn/jiaowu_system/login.aspx")
                        .addHeader("HOST", "newjwglxt.jiea.cn")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36")
                        .addHeader("Origin", "http://newjwglxt.jiea.cn")
                        .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                        .addHeader("Cache-Control", "max-age=0")
                        .addHeader("Referer", "http://newjwglxt.jiea.cn/jiaowu_system/")
                        .addHeader("Upgrade-Insecure-Requests", "1")
                        .post(requestBody)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String con=response.body().string();
                        System.out.println(response.networkResponse().request().url());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Element lerror = Jsoup.parse(con).getElementById("Lerror");
                                if (lerror!=null)
                                    Toast.makeText(LoginActivity.this,lerror.text(),Toast.LENGTH_LONG).show();
                                else{
                                    String users=Jsoup.parse(con).getElementById("users").text();
                                    Toast.makeText(LoginActivity.this,users+"登录成功",Toast.LENGTH_LONG).show();
                                    SharedPreferences user = getSharedPreferences("jianguo_user", MODE_PRIVATE);
                                    user.edit().putBoolean("login",true).commit();
                                    user.edit().putString("num",users).commit();
                                    user.edit().putString("xh",edit_number.getText().toString()).commit();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                }

                            }
                        });
                    }
                });
            }
        });
    }
    private void getCheckCode() {

        Request request=OKHttpUtils.getRequest(OKHttpUtils.getCheckCode());
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            image_checkcode.setImageBitmap(bitmap);
                        }
                    });
            }
        });

    }

    private void getparameter() {
        Request request = OKHttpUtils.getRequest(OKHttpUtils.getParameter());
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body=response.body().string();
                viewstate = Jsoup.parse(body).getElementById("__VIEWSTATE").val();
                viewstategenerator = Jsoup.parse(body).getElementById("__VIEWSTATEGENERATOR").val();
                eventvalidation = Jsoup.parse(body).getElementById("__EVENTVALIDATION").val();
            }
        });


    }

    private void initView() {
        edit_checkcode = (EditText) findViewById(R.id.edit_checkcode);
        edit_number = (AppCompatEditText) findViewById(R.id.edit_num);
        edit_password = (AppCompatEditText) findViewById(R.id.edit_pass);
        image_checkcode = (ImageView) findViewById(R.id.image_checkcode);
        button_login = (Button) findViewById(R.id.button_login);
        button_del = (Button) findViewById(R.id.button_del);
    }
}
