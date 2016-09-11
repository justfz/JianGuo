package com.jianguo.jiaowu.widget;

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
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;

import com.jianguo.DB.JianGuoDB;
import com.jianguo.R;
import com.jianguo.beans.ScoreBean;
import com.jianguo.jiaowu.IsLogin;
import com.jianguo.jiaowu.JWUtils;
import com.jianguo.jiaowu.ScoreListAdapter;
import com.jianguo.jiaowu.presenter.ScorePresenter;
import com.jianguo.jiaowu.presenter.ScorePresenterImpl;
import com.jianguo.jiaowu.view.ScoreView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ifane on 2016/6/3 0003.
 */
public class ScoreFragment extends Fragment implements ScoreView, AdapterView.OnItemSelectedListener {

    private RecyclerView score_recyclerview;
    private AppCompatSpinner spinner;
    private ArrayAdapter<String> spinneradapter;
    private List<String> spinnerlist;
    private ScorePresenter presenter;
    private ScoreListAdapter Scoreadapter;
    private FrameLayout frameLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ScorePresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, null);
        spinnerlist = new ArrayList<String>();
        spinner = (AppCompatSpinner) view.findViewById(R.id.score_spinner);
        spinneradapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_style, spinnerlist);
        spinneradapter.setDropDownViewResource(R.layout.spinner_style);
        spinner.setAdapter(spinneradapter);
        score_recyclerview = (RecyclerView) view.findViewById(R.id.score_recyclerview);
        score_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        score_recyclerview.setItemAnimator(new DefaultItemAnimator());
        Scoreadapter = new ScoreListAdapter(getActivity());
        score_recyclerview.setAdapter(Scoreadapter);
        presenter.loadSpinner(getActivity());
        spinner.setOnItemSelectedListener(this);
        return view;
    }

    @Override
    public void addSpinner(final List<String> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spinnerlist.addAll(list);
                spinneradapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void addScore(final List<ScoreBean> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Scoreadapter.setData(list);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        presenter.loadScore(getActivity(), spinnerlist.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

//    @Override
//    public void loginSuccess() {
//        frameLayout.setVisibility(View.GONE);
//        presenter.loadSpinner(getActivity());
//        spinner.setOnItemSelectedListener(this);
//    }
//
//    @Override
//    public void loginfail() {
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                frameLayout.setVisibility(View.VISIBLE);
//                getFragmentManager().beginTransaction().replace(R.id.Score_fragment,new LoginFragment()).commit();
//            }
//        });
//    }
}
