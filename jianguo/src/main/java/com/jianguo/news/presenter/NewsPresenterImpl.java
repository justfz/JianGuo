package com.jianguo.news.presenter;

import com.jianguo.beans.NewsBean;
import com.jianguo.common.Common;
import com.jianguo.news.model.NewsModel;
import com.jianguo.news.model.NewsModelImpl;
import com.jianguo.news.view.NewsView;
import java.util.List;


/**
 * Created by ifane on 2016/5/26 0026.
 */
public class NewsPresenterImpl implements NewsPresenter {
    private NewsView mNewsView;
    private NewsModel mNewsModel;
    private List<NewsBean> list;

    public NewsPresenterImpl(NewsView newsView){
        mNewsView=newsView;
        mNewsModel=new NewsModelImpl();
    }

    //根据新闻类型请求加载什么新闻的数据
    @Override
    public void loadView() {
        mNewsModel.loadNews(Common.NewsList_zhuye,mNewsView);
    }

    @Override
    public void loadAdvertisement() {
        mNewsModel.loadADNavigation(mNewsView);
    }


}
