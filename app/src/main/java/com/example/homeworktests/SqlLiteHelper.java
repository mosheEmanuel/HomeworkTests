package com.example.homeworktests;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqlLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASENAME = "Homework.dp";
    public static final String TABLE_HOMEWORK = "tblHomework";
    public static final int DATABASEVERSION = 1;

    public static final String COLUMN_ID = "HomeworkId";

    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_SUB_SUBJECT = "subSubject";
    public static final String COLUMN_PAGE = "page";
    public static final String COLUMN_NOTIFICATION = "notifications";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_DAYOfMONTH = "dayOfMonth";

    SQLiteDatabase database;


    private static final String CREATE_TABLE_HOMEWORK = "CREATE TABLE IF NOT EXISTS " + TABLE_HOMEWORK + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_SUBJECT + " VARCHAR, "
            + COLUMN_SUB_SUBJECT + " VARCHAR, " + COLUMN_PAGE + " VARCHAR, " + COLUMN_NOTIFICATION + " INTEGER, " + COLUMN_PRIORITY + " INTEGER, "
            + COLUMN_YEAR + " INTEGER, " + COLUMN_MONTH + " INTEGER, " + COLUMN_DAYOfMONTH + " INTEGER " + ");";

    String[] allColumns = {COLUMN_ID, COLUMN_SUBJECT, COLUMN_SUB_SUBJECT, COLUMN_PAGE, COLUMN_NOTIFICATION,
            COLUMN_PRIORITY, COLUMN_YEAR, COLUMN_MONTH, COLUMN_DAYOfMONTH};

    public SqlLiteHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("data1", CREATE_TABLE_HOMEWORK);
        db.execSQL(CREATE_TABLE_HOMEWORK);
        Log.d("data1", "Table customer created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOMEWORK);
        onCreate(db);
    }

    public void open() {
        database = this.getWritableDatabase();
        Log.d("data1", "Database connection open");
    }

    public Homework createHomework(Homework homework) {

//        long lastId = -1;
//        String str_sql = "INSERT INTO " + SqlLiteHelper.TABLE_HOMEWORK + "(" + COLUMN_SUBJECT + ","
//                + COLUMN_PAGE + "," + COLUMN_SUB_SUBJECT + "," + COLUMN_NOTIFICATION + "," + COLUMN_PRIORITY + ","
//                + COLUMN_YEAR + "," + COLUMN_MONTH + "," + COLUMN_DAYOfMONTH + ")"
//                + " VALUES ('" + homework.getSubject() + "','" + homework.getPage() + "','" + homework.getSubSubject() + "','"
//                + homework.getNotifications() + "','" + homework.getPriority() + "','" + homework.getYear() + "','" + homework.getMonth() + "','" + homework.getDayOfMonth() + "')";
//
//        database.execSQL(str_sql);
//
//        str_sql = "SELECT " + SqlLiteHelper.COLUMN_ID + " from " + SqlLiteHelper.TABLE_HOMEWORK + "  order by " + SqlLiteHelper.COLUMN_ID + " DESC limit 1\n";
//        Cursor c = database.rawQuery(str_sql, null);
//
//        if (c != null && c.moveToFirst()) {
//            lastId = c.getLong(0); //The 0 is the column index, we only have 1 column, so the index is 0
//            Log.d("data1", "" + lastId);
//        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_SUBJECT, homework.getSubject());
        values.put(COLUMN_SUB_SUBJECT, homework.getSubSubject());
        values.put(COLUMN_PAGE, homework.getPage());
        values.put(COLUMN_NOTIFICATION, homework.getNotifications());
        values.put(COLUMN_PRIORITY, homework.getPriority());
        values.put(COLUMN_YEAR, homework.getYear());
        values.put(COLUMN_MONTH, homework.getDayOfMonth());
        values.put(COLUMN_DAYOfMONTH, homework.getDayOfMonth());

        long insertId = database.insert(TABLE_HOMEWORK, null, values);
        homework.setId(insertId);

        return homework;
    }
}
