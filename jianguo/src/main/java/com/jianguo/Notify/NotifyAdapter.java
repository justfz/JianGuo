package com.jianguo.Notify;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jianguo.R;
import com.jianguo.beans.NewsBean;
import com.jianguo.beans.NotifyBean;

import java.util.List;

/**
 * Created by ifane on 2016/6/28 0028.
 */
public class NotifyAdapter extends RecyclerView.Adapter {
    private  LayoutInflater layoutInflater;
    private List<NotifyBean> list;

    public NotifyAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
    }
    public void setData(List<NotifyBean> data){
        list=data;
        notifyDataSetChanged();
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_notify, parent, false);
        return new NotifyHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((NotifyHolder)holder).Notify_title.setText(list.get(position).getTitle());
        ((NotifyHolder) holder).Notify_time.setText(list.get(position).getTime());
        if (onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }
    public class NotifyHolder extends RecyclerView.ViewHolder{
        private TextView Notify_title;
        private TextView Notify_time;
        public NotifyHolder(View itemView) {
            super(itemView);
            Notify_title= (TextView) itemView.findViewById(R.id.notify_title);
            Notify_time= (TextView) itemView.findViewById(R.id.notify_time);
        }
    }
}
