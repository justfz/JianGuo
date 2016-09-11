package com.jianguo.beans;

import android.view.View;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by ifane on 2016/6/17 0017.
 */
public class AdvertisementBean implements Serializable {
    private String[] ad_href;
    private String[] img_herf;

    public String[] getAd_href() {
        return ad_href;
    }

    public void setAd_href(String[] ad_href) {
        this.ad_href = ad_href;
    }

    public String[] getImg_herf() {
        return img_herf;
    }

    public void setImg_herf(String[] img_herf) {
        this.img_herf = img_herf;
    }
}
