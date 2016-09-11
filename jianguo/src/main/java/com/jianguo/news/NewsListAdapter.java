package com.jianguo.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jianguo.Main.widget.ADNavigation;
import com.jianguo.R;
import com.jianguo.beans.NewsBean;

import java.util.List;

/**
 * Created by ifane on 2016/5/26 0026.
 */
public class NewsListAdapter extends RecyclerView.Adapter {
    public static final int TYPE_CONTENT = 0;
    public static final int TYPE_HEADER = 1;
    private final LayoutInflater layoutInflater;
    private List<NewsBean> list;
    private View mHeaderView;

    public NewsListAdapter(Context context) {
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
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    public View getHeaderView() {
        return mHeaderView;
    }

    //根据viewType创建viewholder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER)
            return new ContentHolder(mHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ContentHolder(layout);
    }

    //绑定视图数据
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;
        final int pos = getRealPosition(holder);
        if (holder instanceof ContentHolder) {
            ((ContentHolder) holder).NewsList_title.setText(list.get(pos).getNews_title());
            ((ContentHolder) holder).NewsList_time.setText(list.get(pos).getNews_time());

            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, pos);
                    }
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null) return TYPE_CONTENT;
        if(position == 0) return TYPE_HEADER;
        return TYPE_CONTENT;

    }
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }
    @Override
    public int getItemCount() {
        return mHeaderView==null ?list.size():list.size()+1;
    }

     class ContentHolder extends RecyclerView.ViewHolder {
        private TextView NewsList_title;
        private TextView NewsList_time;

        public ContentHolder(View itemView) {
            super(itemView);
            if(itemView == mHeaderView) return;
            NewsList_title = (TextView) itemView.findViewById(R.id.tv_title);
            NewsList_time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
