package com.jianguo.jiaowu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jianguo.R;
import com.jianguo.beans.ScheduleBean;
import com.jianguo.beans.ScoreBean;

import java.util.List;

/**
 * Created by ifane on 2016/6/3 0003.
 */
public class ScoreListAdapter extends RecyclerView.Adapter {

    private final LayoutInflater from;
    private List<ScoreBean> list;

    public ScoreListAdapter(Context context) {
        from = LayoutInflater.from(context);
    }
    public void setData(List<ScoreBean> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=from.inflate(R.layout.item_score,parent,false);
        return new Scoreitem(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Scoreitem){
            ((Scoreitem) holder).Score_name.setText(list.get(position).getScore_Name());
            ((Scoreitem) holder).Score_grade.setText(list.get(position).getScore_grage());
        }
    }

    @Override
    public int getItemCount() {
        if (list==null){
            return 0;
        }
        return list.size() == 0 ? 0 : list.size();
    }

    public class Scoreitem extends RecyclerView.ViewHolder{
        private TextView Score_name;
        private TextView Score_grade;
        public Scoreitem(View itemView) {
            super(itemView);
            Score_name= (TextView) itemView.findViewById(R.id.score_name);
            Score_grade= (TextView) itemView.findViewById(R.id.score_grade);
        }
    }
}
