package com.example.homeworktests;

import android.graphics.Bitmap;

public class Homework {
    private Bitmap bitmap;
    private String page;
    private  String subject;
    private String date;

    public Homework(Bitmap bitmap, String page, String subject, String date) {
        this.bitmap = bitmap;
        this.page = page;
        this.subject = subject;
        this.date = date;
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

    public void setDate(String date) { this.date = date; }
}
