package com.jianguo.news;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jianguo.Cookie.OKHttpUtils;
import com.jianguo.beans.NewsBean;
import com.jianguo.common.Common;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ifane on 2016/5/26 0026.
 */
public class NewsUtils {
    public static List<NewsBean> readingNews(String content){
        List<NewsBean> list=new ArrayList<NewsBean>();
        Elements select = Jsoup.parse(content).getElementById("xxkc_11").select("li");
        for (int i=0;i<select.size();i++){
            NewsBean news=new NewsBean();
            //得到标题
            news.setNews_title(select.get(i).select("a").attr("title"));
            //得到网址
            news.setHref(Common.NewsList_zhuye+"/"+select.get(i).select("a").attr("href"));
            //得到日期
            news.setNews_time(select.get(i).select("em").text());
            list.add(news);
        }
        Elements select1 = Jsoup.parse(content).select("ul.nlist.w96").get(3).select("li");
        for (int i=0;i<select1.size();i++){
            NewsBean news=new NewsBean();
            news.setNews_title(select1.get(i).select("a").text());
            news.setNews_time(select1.get(i).select("em").text());
            news.setHref(select1.get(i).select("a").attr("href"));
            list.add(news);
        }
        return list;
    }
    public static  String readingNewsDate(String content){
        String NewsDate=Jsoup.parse(content).getElementsByClass("amsg").text();
        return NewsDate;
    }
    public static String readingNewsDetail(String content){
        String Content_Title= Jsoup.parse(content).select("h1.atit").text();
        Element content_tmp = Jsoup.parse(content).getElementById("content");
        if (content_tmp==null)
            return "对不起，找不到新闻（非标准的新闻格式）";
        String web_conten=content_tmp.toString();
        web_conten = web_conten.replace("src=\"","src=\"http://www.jiea.cn");
        web_conten = web_conten.concat("<style>\n" +
                "img{max-width: 100%;height:50vw;}\n" +
                "*{\n" +
                "\tfont:10px/1.5 Microsoft YaHei,\"微软雅黑\", Arial, Helvetica, sans-serif;word-wrap:break-word;color:#333;\n" +
                "}\n" +
                "</style>");
        return  web_conten;
    }
    public static Bitmap readingNewsImage(String content){
        if (Jsoup.parse(content).select("img").size()>1){
            Element img = Jsoup.parse(content).select("img").get(1);
            if (img!=null) {
                String src = img.attr("src");
                src="http://www.jiea.cn/"+src;
                Request request=new Request.Builder().url(src).build();
                try {
                    Response execute = OKHttpUtils.getClient().newCall(request).execute();
                    Bitmap bitmap = BitmapFactory.decodeStream(execute.body().byteStream());
                    return bitmap;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
