package com.jianguo.jiaowu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jianguo.R;
import com.jianguo.beans.ScoreBean;
import com.jianguo.beans.TestBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ifane on 2016/6/4 0004.
 */
public class TestListAdapter extends RecyclerView.Adapter {
    private List<TestBean> list=new ArrayList<TestBean>();
    private final LayoutInflater from;

    public TestListAdapter(Context context) {
        from = LayoutInflater.from(context);
    }
    public void setData(List<TestBean> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = from.inflate(R.layout.item_test, parent, false);
        return new TestItem(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TestItem){
            ((TestItem) holder).Test_name.setText(list.get(position).getTest_name());
            ((TestItem) holder).Test_location.setText(list.get(position).getTest_location());
            ((TestItem) holder).Test_time.setText(list.get(position).getTest_time());
            ((TestItem) holder).Test_seat.setText(list.get(position).getTest_seat());
        }
    }

    @Override
    public int getItemCount() {
        if (list==null){
            return 0;
        }
        return list.size()==0 ? 0 : list.size();
    }

    public class TestItem extends RecyclerView.ViewHolder{
        private TextView Test_name;
        private TextView Test_location;
        private TextView Test_time;
        private TextView Test_seat;

        public TestItem(View itemView) {
            super(itemView);
            Test_name= (TextView) itemView.findViewById(R.id.Test_name);
            Test_location= (TextView) itemView.findViewById(R.id.Test_location);
            Test_time= (TextView) itemView.findViewById(R.id.Test_time);
            Test_seat= (TextView) itemView.findViewById(R.id.Test_seat);
        }
    }
}
