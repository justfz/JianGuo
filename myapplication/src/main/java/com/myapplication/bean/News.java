package com.myapplication.bean;

/**
 * Created by ifane on 2016/5/16 0016.
 */
public class News {
    private String title;
    private int image;

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;
    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
