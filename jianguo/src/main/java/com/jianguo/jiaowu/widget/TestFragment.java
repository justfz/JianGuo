package com.jianguo.jiaowu.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jianguo.DB.JianGuoDB;
import com.jianguo.R;
import com.jianguo.beans.TestBean;
import com.jianguo.jiaowu.IsLogin;
import com.jianguo.jiaowu.JWUtils;
import com.jianguo.jiaowu.TestListAdapter;
import com.jianguo.jiaowu.presenter.TestPresenter;
import com.jianguo.jiaowu.presenter.TestPresenterImpl;
import com.jianguo.jiaowu.view.TestView;

import java.util.List;

/**
 * Created by ifane on 2016/6/3 0003.
 */
public class TestFragment extends Fragment implements TestView {

    private RecyclerView test_recyclerview;
    private TestListAdapter testListAdapter;
    private TestPresenter testPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testPresenter = new TestPresenterImpl(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, null);
        test_recyclerview = (RecyclerView) view.findViewById(R.id.Test_Recyclerview);
        test_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        test_recyclerview.setItemAnimator(new DefaultItemAnimator());
        testListAdapter = new TestListAdapter(getActivity());
        test_recyclerview.setAdapter(testListAdapter);
        testPresenter.loadTestView(getActivity());
        return view;
    }

    @Override
    public void addTest(final List<TestBean> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                testListAdapter.setData(list);
            }
        });
    }
}
