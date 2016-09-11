package com.jianguo.jiaowu.model;

import android.content.Context;

import com.jianguo.jiaowu.view.ScheduleView;

/**
 * Created by ifane on 2016/5/29 0029.
 */
public interface ScheduleModel  {
    void loadSchedule(ScheduleView scheduleView, Context context, String week_id);
}
