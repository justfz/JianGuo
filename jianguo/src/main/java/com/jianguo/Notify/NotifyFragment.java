package com.jianguo.Notify;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jianguo.DB.JianGuoDB;
import com.jianguo.R;
import com.jianguo.beans.NotifyBean;
import com.jianguo.common.URLActivity;

import java.util.List;

/**
 * Created by ifane on 2016/6/28 0028.
 */
public class NotifyFragment extends Fragment {

    private RecyclerView notify_rec;
    private NotifyAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notify, container,false);
        notify_rec = (RecyclerView) view.findViewById(R.id.notify_recyclerview);
        adapter = new NotifyAdapter(getActivity());
        final List<NotifyBean> notify = JianGuoDB.getInstance(getActivity()).getNotify();
        adapter.setData(notify);
        adapter.setOnItemClickListener(new NotifyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), URLActivity.class);
                intent.putExtra("herf",notify.get(position).getHerf());
                intent.putExtra("content",notify.get(position).getContent());
                intent.putExtra("type",notify.get(position).getType());
                startActivity(intent);
            }
        });
        notify_rec.setLayoutManager(new LinearLayoutManager(getActivity()));
        notify_rec.setAdapter(adapter);
        return view;
    }
}
