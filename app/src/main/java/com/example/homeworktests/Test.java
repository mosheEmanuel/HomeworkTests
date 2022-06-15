package com.example.homeworktests;

public class Test {
    private String subject;
    private String subSubject;
    private String date;
    private long id;

    public Test(String subject, String subSubject, String date, long id) {
        this.subject = subject;
        this.subSubject = subSubject;
        this.date = date;
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubSubject() {
        return subSubject;
    }

    public void setSubSubject(String subSubject) {
        this.subSubject = subSubject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
