package com.jianguo.beans;

/**
 * Created by ifane on 2016/5/29 0029.
 */
public class ScheduleBean {
    private String teacher;
    private String location;
    private String ScheduleName;
    private String week_id;
    private String day_id;
    private String Schedule_time;
    public String getSchedule_time() {
        return Schedule_time;
    }

    public void setSchedule_time(String schedule_time) {
        Schedule_time = schedule_time;
    }

    public String getWeek_id() {
        return week_id;
    }

    public void setWeek_id(String week_id) {
        this.week_id = week_id;
    }

    public String getDay_id() {
        return day_id;
    }

    public void setDay_id(String day_id) {
        this.day_id = day_id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getScheduleName() {
        return ScheduleName;
    }

    public void setScheduleName(String scheduleName) {
        ScheduleName = scheduleName;
    }
}
