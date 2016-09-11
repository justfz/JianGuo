package com.jianguo.jiaowu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jianguo.R;
import com.jianguo.beans.ScheduleBean;
import com.jianguo.news.NewsListAdapter;

import java.util.List;

/**
 * Created by ifane on 2016/5/29 0029.
 */
public class ScheduleListAdapter extends RecyclerView.Adapter {

    private List<ScheduleBean> list;
    private final LayoutInflater from;

    public ScheduleListAdapter(Context context){
        from = LayoutInflater.from(context);
    }

    public void setData(List<ScheduleBean> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = from.inflate(R.layout.item_schedule, parent,false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder){
            ScheduleBean scheduleBean= list.get(position);
            ((ItemHolder) holder).teacher.setText(scheduleBean.getTeacher());
            ((ItemHolder) holder).name.setText(scheduleBean.getScheduleName());
            ((ItemHolder) holder).location.setText(scheduleBean.getLocation());
            ((ItemHolder) holder).week.setText(scheduleBean.getSchedule_time());
            ((ItemHolder) holder).day.setText(scheduleBean.getDay_id());
        }
    }

    @Override
    public int getItemCount() {
        if (list==null){
            return 0;
        }
        return list.size() == 0 ? 0 : list.size();
    }
    public class ItemHolder extends RecyclerView.ViewHolder{
        private TextView teacher;
        private TextView location;
        private TextView name;
        private TextView week;
        private TextView day;
        public ItemHolder(View itemView) {
            super(itemView);
            teacher = (TextView) itemView.findViewById(R.id.Schedule_tv_teacher);
            name= (TextView) itemView.findViewById(R.id.Schedule_tv_name);
            location= (TextView) itemView.findViewById(R.id.Schedule_tv_location);
            week= (TextView) itemView.findViewById(R.id.Schedule_tv_week);
            day= (TextView) itemView.findViewById(R.id.Schedule_tv_day);
        }
    }
}
