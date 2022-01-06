package com.example.homeworktests;

import android.graphics.Bitmap;

public class Test {
    private Bitmap bitmap;
    private String questions,page,subject,Test_date;

    public Test(Bitmap bitmap, String questions, String page, String subject, String test_date) {
        this.bitmap = bitmap;
        this.questions = questions;
        this.page = page;
        this.subject = subject;
        Test_date = test_date;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
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

    public String getTest_date() {
        return Test_date;
    }

    public void setTest_date(String test_date) {
        Test_date = test_date;
    }
}
