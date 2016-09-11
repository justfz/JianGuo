package com.jianguo.jiaowu.presenter;

import android.content.Context;

import com.jianguo.jiaowu.model.ScheduleModel;
import com.jianguo.jiaowu.model.ScheduleModelImpl;
import com.jianguo.jiaowu.view.ScheduleView;
import com.jianguo.jiaowu.widget.ScheduleFragment;

/**
 * Created by ifane on 2016/5/28 0028.
 */
public class SchedulePresenterImpl implements SchedulePresenter {
    private ScheduleView mScheduleView;
    private ScheduleModel mScheduleModel;
    public SchedulePresenterImpl(ScheduleView scheduleView) {
        mScheduleView=scheduleView;
        mScheduleModel=new ScheduleModelImpl();
    }

    @Override
    public void loadView(Context context,String week) {
        mScheduleModel.loadSchedule(mScheduleView,context,week);
    }
}
