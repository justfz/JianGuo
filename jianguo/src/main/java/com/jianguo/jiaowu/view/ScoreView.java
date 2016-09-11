package com.jianguo.jiaowu.view;

import com.jianguo.beans.ScoreBean;

import java.util.List;

/**
 * Created by ifane on 2016/6/3 0003.
 */
public interface ScoreView {
    void addSpinner(List<String> list);
    void addScore(List<ScoreBean> list);
}
