package com.jianguo.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ifane on 2016/5/31 0031.
 */
public class JiaoWuOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_SChedule="create table Schedule(" +
            "id integer primary key autoincrement," +
            "schedule_name text," +
            "schedule_teacher text," +
            "schedule_loaction text," +
            "schedule_week_id text," +
            "schedule_day_id text," +
            "schedule_time text)";
    private static final String CREATE_SCORE="create table Score(" +
            "id integer primary key autoincrement," +
            "score_name text," +
            "score_grade text," +
            "score_term text)";
    private static final String CREATE_TEST="create table Test(" +
            "id integer primary key autoincrement," +
            "Test_name text," +
            "Test_time text," +
            "Test_seat text," +
            "Test_location text)";
    private static final String CREATE_NOTIFY="create table Notify(" +
            "id integer primary key autoincrement," +
            "Notify_title text," +
            "Notify_time text," +
            "Notify_herf text," +
            "Notify_content text," +
            "Notify_image text," +
            "Notify_isLook bit," +
            "Notify_type integer)";
    public JiaoWuOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SChedule);
        db.execSQL(CREATE_SCORE);
        db.execSQL(CREATE_TEST);
        db.execSQL(CREATE_NOTIFY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
