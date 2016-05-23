package com.myapplication.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myapplication.R;
import com.myapplication.bean.News;

import java.util.List;

/**
 * Created by ifane on 2016/5/16 0016.
 */
public class News_Adapter extends ArrayAdapter<News> {

    private int resid;

    public News_Adapter(Context context, int resource, List<News> objects) {
        super(context, resource, objects);
        resid=resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news=getItem(position);
        View view;
        ViewHolder holder;
        if (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resid,null);
            holder=new ViewHolder();
           // holder.news_item_image= (ImageView) view.findViewById(R.id.news_image);
            holder.news_item_title= (TextView) view.findViewById(R.id.news_title);
            holder.news_item_date= (TextView) view.findViewById(R.id.news_date);
            view.setTag(holder);
        }else {
            view=convertView;
            holder= (ViewHolder) view.getTag();
        }
        holder.news_item_date.setText(news.getDate());
        holder.news_item_title.setText(news.getTitle());
        //holder.news_item_image.setImageResource(news.getImage());
        return view;
    }

    class ViewHolder {

        ImageView news_item_image;

        TextView news_item_title;

        TextView news_item_date;


    }
}
