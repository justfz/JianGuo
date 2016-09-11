package com.jianguo.jiaowu.widget;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.jianguo.DB.JianGuoDB;
import com.jianguo.R;
import com.jianguo.beans.ScheduleBean;
import com.jianguo.jiaowu.IsLogin;
import com.jianguo.jiaowu.JWUtils;
import com.jianguo.jiaowu.ScheduleListAdapter;
import com.jianguo.jiaowu.presenter.SchedulePresenter;
import com.jianguo.jiaowu.presenter.SchedulePresenterImpl;
import com.jianguo.jiaowu.presenter.TestPresenterImpl;
import com.jianguo.jiaowu.view.ScheduleView;
import java.util.List;

/**
 * Created by ifane on 2016/5/28 0028.
 */
public class ScheduleFragment extends Fragment implements ScheduleView, AdapterView.OnItemSelectedListener{

    private AppCompatSpinner spinner;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ScheduleListAdapter adapter;
    private SchedulePresenter presenter;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=new SchedulePresenterImpl(this);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_schedule,null);
        spinner = (AppCompatSpinner) view.findViewById(R.id.Schedule_spinner);
        spinner.setOnItemSelectedListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.Schedule_lv);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ScheduleListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("正在加载...");
        progressDialog.show();
        return view;
    }

    @Override
    public void addSchedule(final List<ScheduleBean> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setData(list);
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println(position);
        switch (position){
            case 0:
                presenter.loadView(getActivity(),"1");
                break;
            case 1:
                presenter.loadView(getActivity(),"2");
                break;
            case 2:
                presenter.loadView(getActivity(),"3");
                break;
            case 3:
                presenter.loadView(getActivity(),"4");
                break;
            case 4:
                presenter.loadView(getActivity(),"5");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        System.out.println("onNothingSelected");
    }


}
