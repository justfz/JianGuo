package com.jianguo.news.view;

import android.graphics.Bitmap;

/**
 * Created by ifane on 2016/5/27 0027.
 */
public interface NewsDeatil {
    void addNewsDetailContent(String newsDetailContent);
    void addNewsDetailImage(Bitmap bitmap);
    void addNewsDate(String newsDate);
}
