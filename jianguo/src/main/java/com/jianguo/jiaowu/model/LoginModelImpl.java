package com.jianguo.jiaowu.model;

import android.graphics.Bitmap;
import com.jianguo.Cookie.OKHttpUtils;
import com.jianguo.common.Common;
import com.jianguo.jiaowu.JWUtils;
import com.jianguo.jiaowu.view.LoginView;

import org.jsoup.Jsoup;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ifane on 2016/5/31 0031.
 */
public class LoginModelImpl implements LoginModel {
    private  OkHttpClient client = OKHttpUtils.getClient();
    private String viewstate;
    private String viewstategenerator;
    private String eventvalidation;
    @Override
    public void loadCheckCode(String url, final LoginView loginView) {
        Request request=new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Bitmap bitmap= JWUtils.readingCheckCode(response.body());
                loginView.showCheckCode(bitmap);
            }
        });
    }
    @Override
    public void getParameter(final LoginView loginView) {
        Request request=new Request.Builder()
            .url(Common.LOGIN_URL)
            .addHeader("HOST","newjwglxt.jiea.cn")
            .build();
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
                loadCheckCode(Common.CHECK_CODE,loginView);
            }
        });

    }
    @Override
    public void loginpost(final String arg1, String arg2, String arg3, final LoginView loginView) {
        RequestBody requestBody = new FormBody.Builder()
                .add("__VIEWSTATE", viewstate)
                .add("__VIEWSTATEGENERATOR", viewstategenerator)
                .add("__EVENTVALIDATION", eventvalidation)
                .add("Account", arg1)
                .add("PWD", arg2)
                .add_demo("CheckCode", arg3)//这个验证码参数是中文,所以用了自写方法编码为gb2312
                .add("cmdok", "")
                .build();
        Request request=new Request.Builder()
                .url(Common.LOGIN_URL)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(
                new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("登录失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String content=response.body().string();
                boolean login = JWUtils.isLogin(content);
                if (login){
                    loginView.success(JWUtils.getStudentName(content),arg1);
                }else {
                    loginView.fail(JWUtils.FailParameter(content));
                }
            }
        });
    }


}
