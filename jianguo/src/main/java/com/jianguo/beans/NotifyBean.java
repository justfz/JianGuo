package com.jianguo.beans;

import java.io.Serializable;

/**
 * Created by ifane on 2016/6/28 0028.
 */
public class NotifyBean implements Serializable{
    private String herf;
    private String title;
    private String content;
    private String time;
    private String image;
    private int isLook;
    private int type;

    public int getIsLook() {
        return isLook;
    }

    public void setIsLook(int isLook) {
        this.isLook = isLook;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHerf() {
        return herf;
    }

    public void setHerf(String herf) {
        this.herf = herf;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
