package com.example.homeworktests;


public class Homework {
    // בשביל ListView
    private String priority;

    // בשביל שניהם
    private String page;
    private String exercise;
    private String subject;
    private String date;
    private long Id;


    // בשביל SQL
    private String subSubject;
    private int numPriority;


    // בשביל ListView

    public Homework(long id, String page, String exercise, String subject, String date, String priority) {
        this.Id = id;
        this.page = page;
        this.exercise = exercise;
        this.subject = subject;
        this.date = date;
        this.priority = priority;
    }


    // בשביל SQL
    public Homework(long id, String subject, String subSubject, String page, String exercise, String date, int numPriority) {
        this.subject = subject;
        this.subSubject = subSubject;
        this.page = page;
        this.exercise = exercise;
        this.date = date;
        this.numPriority = numPriority;
        this.Id = id;
    }


    public String getSubSubject() {
        return subSubject;
    }

    public void setSubSubject(String subSubject) {
        this.subSubject = subSubject;
    }

    public int getNumPriority() {
        return numPriority;
    }

    public void setNumPriority(int numPriority) {
        this.numPriority = numPriority;
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

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        this.Id = id;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }
}
