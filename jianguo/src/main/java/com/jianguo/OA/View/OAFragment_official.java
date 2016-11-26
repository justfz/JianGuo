package com.jianguo.OA.View;

import android.app.ProgressDialog;
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

import com.jianguo.OA.Presenter.OA_OfficialPresemter;
import com.jianguo.OA.Presenter.OA_OfficialPresenterImpl;
import com.jianguo.R;
import com.jianguo.beans.NewsBean;

import java.util.List;

/**
 * Created by ifane on 2016/8/26 0026.
 */
public class OAFragment_official extends Fragment implements OA_Common_View, OA_RecycleViewAdapter.OnItemClickListener {

    private RecyclerView official_recy;
    private OA_RecycleViewAdapter oa_recycleViewAdapter;
    private OA_OfficialPresemter oa_officialPresemter;
    private List<NewsBean> data;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oa_officialPresemter = new OA_OfficialPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oa_list, null);
        official_recy = (RecyclerView) view.findViewById(R.id.oa_recy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        official_recy.setLayoutManager(linearLayoutManager);
        official_recy.setItemAnimator(new DefaultItemAnimator());
        oa_recycleViewAdapter = new OA_RecycleViewAdapter(getContext());
        oa_recycleViewAdapter.setOnItemClickListener(this);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("正在加载...");
        progressDialog.show();
        oa_officialPresemter.loadOfficial();
        return view;
    }

    @Override
    public void setDataBean(final List<NewsBean> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (list!=null){
                    data=list;
                    oa_recycleViewAdapter.setData(list);
                    official_recy.setAdapter(oa_recycleViewAdapter);
                    progressDialog.dismiss();
                }
            }
        });
    }
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), OfficialActivity.class);
        intent.putExtra("herf",data.get(position).getHref());
        getActivity().startActivity(intent);
    }
}
