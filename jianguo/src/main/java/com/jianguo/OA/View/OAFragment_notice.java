package com.jianguo.OA.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jianguo.OA.Presenter.OA_NoticePresenter;
import com.jianguo.OA.Presenter.OA_NoticePresenterImpl;
import com.jianguo.R;
import com.jianguo.beans.NewsBean;

import java.util.List;

/**
 * Created by ifane on 2016/8/27 0027.
 */
public class OAFragment_notice extends Fragment implements OA_Common_View, OA_RecycleViewAdapter.OnItemClickListener {

    private RecyclerView notice_recy;
    private OA_RecycleViewAdapter oa_recycleViewAdapter_notice;
    private OA_NoticePresenter oa_noticePresenter;
    private List<NewsBean> data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oa_noticePresenter = new OA_NoticePresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oa_list, null);
        notice_recy = (RecyclerView) view.findViewById(R.id.oa_recy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        notice_recy.setLayoutManager(linearLayoutManager);
        notice_recy.setItemAnimator(new DefaultItemAnimator());
        oa_recycleViewAdapter_notice = new OA_RecycleViewAdapter(getContext());
        oa_recycleViewAdapter_notice.setOnItemClickListener(this);
        oa_noticePresenter.loadNotice();
        return view;
    }

    @Override
    public void setDataBean(final List<NewsBean> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (list!=null){
                    data=list;
                    oa_recycleViewAdapter_notice.setData(list);
                    notice_recy.setAdapter(oa_recycleViewAdapter_notice);
                }else {
                    Toast.makeText(getContext(),"数据获取错误",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent=new Intent(getActivity(), Notice_InformationActivity.class);
        intent.putExtra("herf",data.get(position).getHref());
        startActivity(intent);
    }
}
