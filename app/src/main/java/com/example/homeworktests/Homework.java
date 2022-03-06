package com.example.homeworktests;

import android.graphics.Bitmap;

public class Homework {
    // בשביל ListView
    private Bitmap bitmap;
    private String date;
    private String priority;
    // בשביל שניהם
    private String page;
    private String subject;

    // בשביל SQL
    private String subSubject;
    private int notifications;
    private int numPriority;
    private int year;
    private int month;
    private int dayOfMonth;
    private long HomeworkId;


    // בשביל ListView

    public Homework(Bitmap bitmap, String page, String subject, String date, String priority) {
        this.bitmap = bitmap;
        this.page = page;
        this.subject = subject;
        this.date = date;
        this.priority = priority;
    }


    // בשביל SQL
    public Homework(String page, String subject, String subSubject, int notifications, int numPriority, int year, int month, int dayOfMonth) {
        this.page = page;
        this.subject = subject;
        this.subSubject = subSubject;
        this.notifications = notifications;
        this.numPriority = numPriority;
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.HomeworkId = 0;
    }


    public String getSubSubject() {
        return subSubject;
    }

    public void setSubSubject(String subSubject) {
        this.subSubject = subSubject;
    }

    public int getNotifications() {
        return notifications;
    }

    public void setNotifications(int notifications) {
        this.notifications = notifications;
    }


    public int getNumPriority() {
        return numPriority;
    }

    public void setNumPriority(int numPriority) {
        this.numPriority = numPriority;
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public long getHomeworkId() {
        return HomeworkId;
    }

    public void setHomeworkId(long homeworkId) {
        HomeworkId = homeworkId;
    }

}
