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

import com.jianguo.OA.Presenter.OA_InformationPresenter;
import com.jianguo.OA.Presenter.OA_InformationPresenterImpl;
import com.jianguo.R;
import com.jianguo.beans.NewsBean;

import java.util.List;

/**
 * Created by ifane on 2016/8/27 0027.
 */
public class OAFragment_information  extends Fragment implements OA_Common_View, OA_RecycleViewAdapter.OnItemClickListener {

    private RecyclerView information_recy;
    private OA_RecycleViewAdapter oa_recycleViewAdapter_information;
    private OA_InformationPresenter oa_informationPresenter;
    private List<NewsBean> data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oa_informationPresenter = new OA_InformationPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oa_list, null);
        information_recy = (RecyclerView) view.findViewById(R.id.oa_recy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        information_recy.setLayoutManager(linearLayoutManager);
        information_recy.setItemAnimator(new DefaultItemAnimator());
        oa_recycleViewAdapter_information = new OA_RecycleViewAdapter(getContext());
        oa_recycleViewAdapter_information.setOnItemClickListener(this);
        oa_informationPresenter.loadInformation();
        return view;
    }

    @Override
    public void setDataBean(final List<NewsBean> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (list!=null){
                    data =list;
                    oa_recycleViewAdapter_information.setData(list);
                    information_recy.setAdapter(oa_recycleViewAdapter_information);
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
