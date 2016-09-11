package com.jianguo.OA.Model;

import com.jianguo.Cookie.OKHttpUtils;
import com.jianguo.OA.OAUtils;
import com.jianguo.OA.View.OA_LoginView;
import com.jianguo.common.Common;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by ifane on 2016/8/26 0026.
 */
public class OA_LoginModelImpl implements OA_LoginModel {
    public void getParameter(final OA_LoginView view, String url){
        Request request=new Request.Builder()
                .url(url)
                .build();
        OKHttpUtils.getClient_oa().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                view.setParameter(OAUtils.readingCheckCode(response.body()));
            }
        });
    }

    @Override
    public void LoginPost(String str1, String str2, String str3, final OA_LoginView oa_loginView) {
        RequestBody requestBody= new FormBody.Builder()
                .add("userName",str1)
                .add("password",str2)
                .add("checkCode",str3)
                .build();
        Request request=new Request.Builder()
                .url(Common.OA_LOGIN_URL)
                .post(requestBody)
                .build();
        OKHttpUtils.getClient_oa().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                oa_loginView.fail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String content = response.body().string();
                if (OAUtils.isLogin(content)){
                    oa_loginView.success();
                }else {
                    oa_loginView.fail();
                }
            }
        });
    }
}

