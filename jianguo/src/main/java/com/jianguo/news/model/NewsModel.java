package com.jianguo.news.model;

import com.jianguo.beans.NewsBean;
import com.jianguo.news.view.NewsDeatil;
import com.jianguo.news.view.NewsView;

import java.util.List;

/**
 * Created by ifane on 2016/5/26 0026.
 */
public interface NewsModel {
    //通过URL取数据，并通过newsView将数据发出去
    void loadNews(String url,NewsView newsView);
    void loadNews(String url, NewsDeatil newsDeatil);

    void loadADNavigation(NewsView mNewsView);
}
