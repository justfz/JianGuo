package com.jianguo.news.widget;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jianguo.Cookie.OKHttpUtils;
import com.jianguo.Main.widget.ADNavigation;
import com.jianguo.R;
import com.jianguo.beans.AdvertisementBean;
import com.jianguo.beans.NewsBean;
import com.jianguo.news.NewsListAdapter;
import com.jianguo.news.presenter.NewsPresenter;
import com.jianguo.news.presenter.NewsPresenterImpl;
import com.jianguo.news.view.NewsView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ifane on 2016/5/25 0025.
 */
public class NewsListFragment extends Fragment implements NewsView, SwipeRefreshLayout.OnRefreshListener {
    private List<NewsBean> list;
    private SwipeRefreshLayout refreshLayout;
    private NewsPresenter mNewsPresenter;
    private RecyclerView recyclerView;
    private NewsListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ADNavigation adNavigation;

    //这里创建了新闻列表的Fragment,并将新闻类型和fragment联系起来,将这个fragment返回
    public static NewsListFragment newInstance() {
        NewsListFragment fragment = new NewsListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsPresenter = new NewsPresenterImpl(this);
    }
    public void setHeader(final AdvertisementBean advertisementBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View header = LayoutInflater.from(getContext()).inflate(R.layout.viewholder_header, recyclerView, false);
                adNavigation = (ADNavigation) header.findViewById(R.id.ad_header);
                adNavigation.setImagesUrl(advertisementBean);
                adapter.setHeaderView(header);
                adapter.notifyDataSetChanged();
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("资讯");
        View view = inflater.inflate(R.layout.fragment_newslist, null);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        refreshLayout.setColorSchemeResources(R.color.primary,
                R.color.primary_dark, R.color.primary_light,
                R.color.accent);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
        recyclerView= (RecyclerView) view.findViewById(R.id.recycle_view);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        onRefresh();
        adapter = new NewsListAdapter(getActivity());
        adapter.setOnItemClickListener(new NewsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NewsBean newsBean=list.get(position);
                Intent intent=new Intent(getActivity(),NewsDetailActivity.class);
                intent.putExtra("news",newsBean);
                getActivity().startActivity(intent);
                System.out.println(position);
            }
        });
        return view;
    }
    @Override
    public void addNews(List<NewsBean> newslist) {

        if(list == null) {
            list = new ArrayList<NewsBean>();
        }
        list.addAll(newslist);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setData(list);
                recyclerView.setAdapter(adapter);
                refreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        if (list!=null)
            list.clear();
        mNewsPresenter.loadView();
        //加载图片轮播
        //mNewsPresenter.loadAdvertisement();
    }


}
