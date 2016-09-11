package com.jianguo.jiaowu.model;

import android.content.Context;

import com.jianguo.Cookie.OKHttpUtils;
import com.jianguo.DB.JianGuoDB;
import com.jianguo.beans.ScoreBean;
import com.jianguo.common.Common;
import com.jianguo.jiaowu.JWUtils;
import com.jianguo.jiaowu.view.ScoreView;

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
public class ScoreModelImpl implements ScoreModel {
    @Override
    public void loadScore(Context context, final ScoreView scoreView) {
        OkHttpClient client = OKHttpUtils.getClient();
        Request request=new Request.Builder()
                .url(Common.SCORE_URL)
                .build();
        final JianGuoDB instance =  JianGuoDB.getInstance(context);
        if (instance.ScoreSpinnerisExist()){
            loadSpinnerfromDB(instance,scoreView);
        }else {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String content=response.body().string();
                    JWUtils.readingScore(content,instance);
                    loadSpinnerfromDB(instance,scoreView);
                }
            });
        }
    }
    public void loadSpinnerfromDB(JianGuoDB instance, ScoreView scoreView){
        List<String> spinner = instance.loadSpinner();
        scoreView.addSpinner(spinner);
    }
    public void loadScorefromDB(Context context,ScoreView scoreView,String term){
        JianGuoDB instance =  JianGuoDB.getInstance(context);
        List<ScoreBean> score = instance.loadScore(term);
        scoreView.addScore(score);
    }
}
