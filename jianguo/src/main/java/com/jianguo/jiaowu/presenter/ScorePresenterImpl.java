package com.jianguo.jiaowu.presenter;

import android.content.Context;

import com.jianguo.DB.JianGuoDB;
import com.jianguo.jiaowu.model.ScheduleModel;
import com.jianguo.jiaowu.model.ScheduleModelImpl;
import com.jianguo.jiaowu.model.ScoreModel;
import com.jianguo.jiaowu.model.ScoreModelImpl;
import com.jianguo.jiaowu.view.ScheduleView;
import com.jianguo.jiaowu.view.ScoreView;

import java.util.List;

/**
 * Created by ifane on 2016/6/3 0003.
 */
public class ScorePresenterImpl implements ScorePresenter {
    private  ScoreView mScoreView;
    private ScoreModel mScoreModel;
    public ScorePresenterImpl(ScoreView scheduleView){
        mScoreView=scheduleView;
        mScoreModel=new ScoreModelImpl();
    }
    @Override
    public void loadSpinner(Context context) {
        mScoreModel.loadScore(context,mScoreView);
    }

    @Override
    public void loadScore(Context context,String term) {
        mScoreModel.loadScorefromDB(context,mScoreView,term);
    }


}
