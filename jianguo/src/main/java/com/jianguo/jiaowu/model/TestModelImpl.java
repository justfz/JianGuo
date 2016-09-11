package com.jianguo.jiaowu.model;

import android.content.Context;

import com.jianguo.Cookie.OKHttpUtils;
import com.jianguo.DB.JianGuoDB;
import com.jianguo.beans.TestBean;
import com.jianguo.common.Common;
import com.jianguo.jiaowu.JWUtils;
import com.jianguo.jiaowu.view.TestView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ifane on 2016/6/3 0003.
 */
public class TestModelImpl implements TestModel {
    @Override
    public void loadTest(final TestView testView, Context context) {
        OkHttpClient client = OKHttpUtils.getClient();
        Request request=new Request.Builder()
                .url(Common.TEST_URL)
                .build();
        final JianGuoDB instance = JianGuoDB.getInstance(context);
        if (instance.TestisExist()){
            loadfromDB(instance,testView);
        }else {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    JWUtils.readingTest(response.body().string(),instance);
                    loadfromDB(instance,testView);
                }
            });
        }
    }
    private void loadfromDB(JianGuoDB instance, TestView testView) {
        List<TestBean> testlist = instance.loadTest();
        testView.addTest(testlist);
    }
}
