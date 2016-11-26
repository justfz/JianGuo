package com.jianguo.news.presenter;

import com.jianguo.common.Common;
import com.jianguo.news.model.NewsModel;
import com.jianguo.news.model.NewsModelImpl;
import com.jianguo.news.view.NewsDeatil;

/**
 * Created by ifane on 2016/5/27 0027.
 */
public class NewsDetailPresenterImpl implements NewsDetailPresenter {
    private NewsModel mNewsModel;
    private NewsDeatil mNewsDeatil;
    public NewsDetailPresenterImpl(NewsDeatil newsDeatil){
        mNewsDeatil=newsDeatil;
        mNewsModel=new NewsModelImpl();
    }
    @Override
    public void loadDetailView(String url) {
        mNewsModel.loadNews(url,mNewsDeatil);
    }

}
