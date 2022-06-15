package com.example.homeworktests;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class SqlLiteHelperHomework extends SQLiteOpenHelper {

    public static final String DATABASENAME = "Homework.dp";
    public static final String TABLE_HOMEWORK = "tblHomework";
    public static final int DATABASEVERSION = 1;

    public static final String COLUMN_ID = "HomeworkId";

    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_SUB_SUBJECT = "subSubject";
    public static final String COLUMN_PAGE = "page";
    public static final String COLUMN_EXERCISE = "exercise";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_DATE = "date";

    SQLiteDatabase database;


    private static final String CREATE_TABLE_HOMEWORK = "CREATE TABLE IF NOT EXISTS " + TABLE_HOMEWORK + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_SUBJECT + " VARCHAR, "
            + COLUMN_SUB_SUBJECT + " VARCHAR, " + COLUMN_PAGE + " VARCHAR, " + COLUMN_EXERCISE + " VARCHAR, "  + COLUMN_PRIORITY + " INTEGER, "
            + COLUMN_DATE + " VARCHAR " + ");";

    String[] allColumns = {COLUMN_ID, COLUMN_SUBJECT, COLUMN_SUB_SUBJECT, COLUMN_PAGE, COLUMN_EXERCISE,
            COLUMN_PRIORITY, COLUMN_DATE};

    public SqlLiteHelperHomework(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_HOMEWORK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOMEWORK);
        onCreate(db);
    }

    public void open() {
        database = this.getWritableDatabase();
    }

    public Homework createHomework(Homework homework) {

//        long lastId = -1;
//        String str_sql = "INSERT INTO " + SqlLiteHelperHomework.TABLE_HOMEWORK + "(" + COLUMN_SUBJECT + ","
//                + COLUMN_PAGE + "," + COLUMN_SUB_SUBJECT + "," + COLUMN_NOTIFICATION + "," + COLUMN_PRIORITY + ","
//                + COLUMN_YEAR + "," + COLUMN_MONTH + "," + COLUMN_DAYOfMONTH + ")"
//                + " VALUES ('" + homework.getSubject() + "','" + homework.getPage() + "','" + homework.getSubSubject() + "','"
//                + homework.getNotifications() + "','" + homework.getPriority() + "','" + homework.getYear() + "','" + homework.getMonth() + "','" + homework.getDayOfMonth() + "')";
//        database.execSQL(str_sql);
//        str_sql = "SELECT " + SqlLiteHelperHomework.COLUMN_ID + " from " + SqlLiteHelperHomework.TABLE_HOMEWORK + "  order by " + SqlLiteHelperHomework.COLUMN_ID + " DESC limit 1\n";
//        Cursor c = database.rawQuery(str_sql, null);
//        if (c != null && c.moveToFirst()) {
//            lastId = c.getLong(0); //The 0 is the column index, we only have 1 column, so the index is 0
//            Log.d("data1", "" + lastId);
//        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_SUBJECT, homework.getSubject());
        values.put(COLUMN_SUB_SUBJECT, homework.getSubSubject());
        values.put(COLUMN_PAGE, homework.getPage());
        values.put(COLUMN_EXERCISE, homework.getExercise());
        values.put(COLUMN_PRIORITY, homework.getPriority());
        values.put(COLUMN_DATE, homework.getDate());

        long insertId = database.insert(TABLE_HOMEWORK, null, values);
        homework.setId(insertId);

        return homework;
    }

    public ArrayList<Homework> getAllHomework() {
        ArrayList<Homework> l = new ArrayList<>();
        String query = "select * from " + TABLE_HOMEWORK;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() >= 0) {

            while (cursor.moveToNext()) {
                String subject = cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT));
                String page = cursor.getString(cursor.getColumnIndex(COLUMN_PAGE));
                String exercise = cursor.getString(cursor.getColumnIndex(COLUMN_EXERCISE));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                int priority = cursor.getInt(cursor.getColumnIndex(COLUMN_PRIORITY));
                long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));

                String sPriority;
                if (priority == 1)
                    sPriority = "נמוכה";
                else if (priority == 2)
                    sPriority = "בנונית";
                else
                    sPriority = "גבוהה";
                Homework homework = new Homework(id,page, exercise, subject, date, sPriority);
                l.add(homework);
            }
        }
        return l;
    }
    public boolean isEmpty() {
        boolean rowExists;
        Cursor mCursor = database.rawQuery("select * from " + TABLE_HOMEWORK, null);
        if (mCursor.getCount() == 0)
            rowExists = false;
        else
            rowExists = true;

        return rowExists;
    }

    public long deleteByRow(long rowId) {
        return database.delete(SqlLiteHelperHomework.TABLE_HOMEWORK, SqlLiteHelperHomework.COLUMN_ID + "=" + rowId, null);
    }


}

