package com.jianguo.jiaowu.view;

import android.graphics.Bitmap;

/**
 * Created by ifane on 2016/5/31 0031.
 */
public interface LoginView {
    void showCheckCode(Bitmap bitmap);
    void success(String user,String id);
    void fail(String fail);
}
