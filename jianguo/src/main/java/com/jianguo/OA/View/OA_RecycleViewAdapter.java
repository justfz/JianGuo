package com.jianguo.OA.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jianguo.R;
import com.jianguo.beans.NewsBean;

import java.util.List;

/**
 * Created by ifane on 2016/8/26 0026.
 */
public class OA_RecycleViewAdapter extends RecyclerView.Adapter {

    private final LayoutInflater layoutInflater;
    private List<NewsBean> list;

    public OA_RecycleViewAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<NewsBean> data) {
        list = data;
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
        View view = layoutInflater.inflate(R.layout.item_news, parent, false);
        return new OA_itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OA_itemViewHolder){
            ((OA_itemViewHolder) holder).OA_item_title.setText(list.get(position).getNews_title());
            ((OA_itemViewHolder) holder).OA_item_time.setText(list.get(position).getNews_time());
            if (onItemClickListener!=null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    class OA_itemViewHolder extends RecyclerView.ViewHolder {
        private TextView OA_item_title;
        private TextView OA_item_time;
        public OA_itemViewHolder(View itemView) {
            super(itemView);
            OA_item_title = (TextView) itemView.findViewById(R.id.tv_title);
            OA_item_time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
