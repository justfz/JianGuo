package com.jianguo.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jianguo.beans.NotifyBean;
import com.jianguo.beans.ScheduleBean;
import com.jianguo.beans.ScoreBean;
import com.jianguo.beans.TestBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ifane on 2016/5/31 0031.
 */
public class JianGuoDB {
    public static final String DB_NAME="JiaoWu";
    public static final int VERSION=1;
    private final SQLiteDatabase db;
    private static JianGuoDB jianGuoDB;
    private JianGuoDB(Context context){
        JiaoWuOpenHelper dbHelper=new JiaoWuOpenHelper(context,DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }
    public synchronized static JianGuoDB getInstance(Context context){
        if (jianGuoDB==null){
            jianGuoDB=new JianGuoDB(context);
        }
        return jianGuoDB;
    }
    public void saveSchedule(ScheduleBean scheduleBean){
        if (scheduleBean!=null){
            ContentValues values=new ContentValues();
            values.put("schedule_name",scheduleBean.getScheduleName());
            values.put("schedule_teacher",scheduleBean.getTeacher());
            values.put("schedule_loaction",scheduleBean.getLocation());
            values.put("schedule_day_id",scheduleBean.getDay_id());
            values.put("schedule_week_id",scheduleBean.getWeek_id());
            values.put("schedule_time",scheduleBean.getSchedule_time());
            db.insert("Schedule",null,values);
        }
    }
    public List<ScheduleBean> loadSchedule(){
        List<ScheduleBean> list=new ArrayList<ScheduleBean>();
        Cursor cursor=db.query("Schedule",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                ScheduleBean scheduleBean=new ScheduleBean();
                scheduleBean.setScheduleName(cursor.getString(cursor.getColumnIndex("schedule_name")));
                scheduleBean.setTeacher(cursor.getString(cursor.getColumnIndex("schedule_teacher")));
                scheduleBean.setLocation(cursor.getString(cursor.getColumnIndex("schedule_loaction")));
                scheduleBean.setDay_id(cursor.getString(cursor.getColumnIndex("schedule_day_id")));
                scheduleBean.setWeek_id(cursor.getString(cursor.getColumnIndex("schedule_week_id")));
                scheduleBean.setSchedule_time(cursor.getString(cursor.getColumnIndex("schedule_time")));
                list.add(scheduleBean);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<ScheduleBean> loadWeek(String week_id){
        List<ScheduleBean> list=new ArrayList<ScheduleBean>();
        Cursor cursor=db.rawQuery("select * from Schedule where schedule_week_id=? order by schedule_day_id",new String[]{week_id});
        //db.rawQuery("select * from Schedule where schedule_week_id=?",new String[]{String.valueOf(week_id)});
        //new String[]{"schedule_name","schedule_teacher","schedule_loaction","schedule_day_id"};
        if (cursor.moveToFirst()){
            do {
                ScheduleBean scheduleBean=new ScheduleBean();
                scheduleBean.setScheduleName(cursor.getString(cursor.getColumnIndex("schedule_name")));
                scheduleBean.setTeacher(cursor.getString(cursor.getColumnIndex("schedule_teacher")));
                scheduleBean.setLocation(cursor.getString(cursor.getColumnIndex("schedule_loaction")));
                scheduleBean.setDay_id(cursor.getString(cursor.getColumnIndex("schedule_day_id")));
                scheduleBean.setWeek_id(cursor.getString(cursor.getColumnIndex("schedule_week_id")));
                scheduleBean.setSchedule_time(cursor.getString(cursor.getColumnIndex("schedule_time")));
                list.add(scheduleBean);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    public boolean isScheduleExist(){
        Cursor cursor=db.rawQuery("select * from Schedule",null);
        int count=cursor.getCount();
        cursor.close();
        if (count==0)
            return false;
        else
            return true;
    }
    public boolean ScoreSpinnerisExist(){
        Cursor cursor=db.rawQuery("select Score_term from Score",null);
        int count=cursor.getCount();
        cursor.close();
        if (count==0)
            return false;
        else
            return true;
    }
    public  List<String> loadSpinner(){
        List<String> list=new ArrayList<String>();
        Cursor cursor=db.rawQuery("select distinct Score_term from Score order by Score_term desc",null);
        if (cursor.moveToFirst()){
            do {
                list.add(cursor.getString(cursor.getColumnIndex("score_term")));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public void  saveScore(ScoreBean scoreBean){
        if (scoreBean!=null){
            ContentValues values=new ContentValues();
            values.put("score_name",scoreBean.getScore_Name());
            values.put("score_grade",scoreBean.getScore_grage());
            values.put("score_term",scoreBean.getTerm());
            db.insert("Score",null,values);
        }
    }

    public List<ScoreBean> loadScore(String term){
        List<ScoreBean> list=new ArrayList<ScoreBean>();
        Cursor cursor = db.rawQuery("select * from Score where Score_term=?", new String[]{term});
        if (cursor.moveToFirst()){
            do {
                ScoreBean scoreBean=new ScoreBean();
                scoreBean.setScore_Name(cursor.getString(cursor.getColumnIndex("score_name")));
                scoreBean.setScore_grage(cursor.getString(cursor.getColumnIndex("score_grade")));
                scoreBean.setTerm(cursor.getString(cursor.getColumnIndex("score_term")));
                list.add(scoreBean);
            }while (cursor.moveToNext());
        }
        return list;
    }
    public boolean TestisExist(){
        Cursor cursor=db.rawQuery("select * from Test",null);
        int count=cursor.getCount();
        cursor.close();
        if (count==0)
            return false;
        else
            return true;
    }
    public void saveTest(TestBean testBean) {
        if (testBean!=null){
            ContentValues values=new ContentValues();
            values.put("Test_name",testBean.getTest_name());
            values.put("Test_time",testBean.getTest_time());
            values.put("Test_location",testBean.getTest_location());
            values.put("Test_seat",testBean.getTest_seat());
            db.insert("Test",null,values);
        }
    }
    public List<TestBean> loadTest(){
        List<TestBean> list=new ArrayList<TestBean>();
        Cursor cursor=db.rawQuery("select * from Test",null);
        if (cursor.moveToFirst()){
            do {
                TestBean testBean=new TestBean();
                testBean.setTest_name(cursor.getString(cursor.getColumnIndex("Test_name")));
                testBean.setTest_time(cursor.getString(cursor.getColumnIndex("Test_time")));
                testBean.setTest_location(cursor.getString(cursor.getColumnIndex("Test_location")));
                testBean.setTest_seat(cursor.getString(cursor.getColumnIndex("Test_seat")));
                list.add(testBean);
            }while (cursor.moveToNext());
        }
        return list;
    }
    public void clean(){
        //删除课表数据
        db.execSQL("delete from Schedule");
        //删除考试数据
        db.execSQL("delete from Test");
        //删除成绩数据
        db.execSQL("delete from Score");
    }


    public void addNotify(NotifyBean notifyBean){
        if (notifyBean!=null){
            ContentValues values=new ContentValues();
            values.put("Notify_title",notifyBean.getTitle());
            values.put("Notify_time",notifyBean.getTime());
            values.put("Notify_content",notifyBean.getContent());
            values.put("Notify_herf",notifyBean.getHerf());
            values.put("Notify_isLook",notifyBean.getIsLook());
            values.put("Notify_type",notifyBean.getType());
            db.insert("Notify",null,values);
        }
    }
    public List<NotifyBean> getNotify(){
        List<NotifyBean> list=new ArrayList<NotifyBean>();
        Cursor cursor=db.rawQuery("select * from Notify",null);
        if (cursor.moveToFirst()){
            do {
                NotifyBean notifyBean=new NotifyBean();
                notifyBean.setTitle(cursor.getString(cursor.getColumnIndex("Notify_title")));
                notifyBean.setTime(cursor.getString(cursor.getColumnIndex("Notify_time")));
                notifyBean.setContent(cursor.getString(cursor.getColumnIndex("Notify_content")));
                notifyBean.setHerf(cursor.getString(cursor.getColumnIndex("Notify_herf")));
                notifyBean.setIsLook(cursor.getInt(cursor.getColumnIndex("Notify_isLook")));
                notifyBean.setType(cursor.getInt(cursor.getColumnIndex("Notify_type")));
                notifyBean.setImage(cursor.getString(cursor.getColumnIndex("Notify_image")));
                list.add(notifyBean);
            }while (cursor.moveToNext());
        }
        return list;
    }
}
