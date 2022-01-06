package com.example.homeworktests;

import android.graphics.Bitmap;

public class Homework_Test {
    private Bitmap bitmap;
    private String questions,page,subject,Date_of_submission;

    public Homework_Test(Bitmap bitmap, String questions, String page, String subject, String date_of_submission) {
        this.bitmap = bitmap;
        this.questions = questions;
        this.page = page;
        this.subject = subject;
        Date_of_submission = date_of_submission;
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

    public String getDate_of_submission() {
        return Date_of_submission;
    }

    public void setDate_of_submission(String date_of_submission) {
        Date_of_submission = date_of_submission;
    }
}
