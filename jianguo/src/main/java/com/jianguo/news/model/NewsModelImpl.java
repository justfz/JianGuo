package com.jianguo.news.model;


import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.jianguo.Cookie.OKHttpUtils;
import com.jianguo.beans.AdvertisementBean;
import com.jianguo.beans.NewsBean;
import com.jianguo.common.Common;
import com.jianguo.news.NewsUtils;
import com.jianguo.news.view.NewsDeatil;
import com.jianguo.news.view.NewsView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ifane on 2016/5/26 0026.
 */
public class NewsModelImpl implements NewsModel {

    private List<NewsBean> list;

    @Override
    public void loadNews(String url, final NewsView newsView) {
        OkHttpClient client = OKHttpUtils.getClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String content = response.body().string();
                list = NewsUtils.readingNews(content);
                newsView.addNews(list);
            }
        });

    }
    /*
    加载新闻详情
     */
    @Override
    public void loadNews(String url, final NewsDeatil newsDeatil) {
        OkHttpClient client = OKHttpUtils.getClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String content = response.body().string();
                String newsDate=NewsUtils.readingNewsDate(content);
                String detail = NewsUtils.readingNewsDetail(content);
                Bitmap bitmap = NewsUtils.readingNewsImage(content);
                if (bitmap != null) {
                    newsDeatil.addNewsDetailImage(bitmap);
                }
                newsDeatil.addNewsDate(newsDate);
                newsDeatil.addNewsDetailContent(detail);
            }
        });
    }

    @Override
    public void loadADNavigation(final NewsView mNewsView) {
        OkHttpClient client=new OkHttpClient.Builder()
                .build();
        Request request=new Request.Builder()
                .url(Common.AD).build();
        final AdvertisementBean advertisementBean = new AdvertisementBean();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String content = response.body().string();
                try {
                    JSONObject object=new JSONObject(content);
                    //拿到图片地址
                    //拿到广告地址
                    JSONArray ad_image = object.getJSONArray("ad_image");
                    JSONArray ad_herf = object.getJSONArray("ad_herf");
                    String[] img_arry=new String[ad_image.length()];
                    String[] herf_arry=new String[ad_herf.length()];
                    for (int i=0;i<ad_image.length();i++){
                        img_arry[i]=ad_image.get(i).toString();
                        herf_arry[i]=ad_herf.get(i).toString();
                    }
                    advertisementBean.setImg_herf(img_arry);
                    advertisementBean.setAd_href(herf_arry);
                    if (advertisementBean!=null){
                        mNewsView.setHeader(advertisementBean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
