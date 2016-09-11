package com.jianguo.jiaowu.model;

import android.content.Context;

import com.jianguo.DB.JianGuoDB;
import com.jianguo.jiaowu.view.ScoreView;

/**
 * Created by ifane on 2016/6/3 0003.
 */
public interface ScoreModel {
    void loadScore(Context context, ScoreView scoreView);
    void loadScorefromDB(Context context, ScoreView scoreView, String term);
}
