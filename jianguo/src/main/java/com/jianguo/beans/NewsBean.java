package com.jianguo.beans;

import java.io.Serializable;

/**
 * Created by ifane on 2016/5/26 0026.
 */
public class NewsBean implements Serializable{
    private String href;
    private String News_title;
    private String News_time;

    public String getHref() {
        return href;
    }

    public String getNews_title() {
        return News_title;
    }

    public String getNews_time() {
        return News_time;
    }

    public void setHref(String href) {

        this.href = href;
    }

    public void setNews_title(String news_title) {
        News_title = news_title;
    }

    public void setNews_time(String news_time) {
        News_time = news_time;
    }
}
