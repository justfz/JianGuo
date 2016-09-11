package com.jianguo.news.view;

import com.jianguo.beans.AdvertisementBean;
import com.jianguo.beans.NewsBean;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by ifane on 2016/5/26 0026.
 */
public interface NewsView {
    void addNews(List<NewsBean> list);
    void setHeader(AdvertisementBean advertisementBean);
}
