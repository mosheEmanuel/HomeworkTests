package com.example.homeworktests;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SqlLiteHelperTest extends SQLiteOpenHelper {

    public static final String DATABASENAME = "Test.db";
    public static final String TABLE_TEST = "tblTest";
    public static final int DATABASEVERSION = 1;

    public static final String COLUMN_ID = "testId";

    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_SUB_SUBJECT = "subSubject";
    public static final String COLUMN_DATE = "date";

    SQLiteDatabase database;

    private static final String CREATE_TABLE_Test = "CREATE TABLE IF NOT EXISTS " + TABLE_TEST + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_SUBJECT + " VARCHAR, "
            + COLUMN_SUB_SUBJECT + " VARCHAR, " + COLUMN_DATE + " VARCHAR " + ");";

    String[] allColumns = {COLUMN_SUBJECT, COLUMN_SUB_SUBJECT, COLUMN_DATE};

    public SqlLiteHelperTest(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_Test);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST);
        onCreate(db);
    }

    public void open() {
        database = this.getWritableDatabase();
    }

    public Test createTest(Test test) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_SUBJECT, test.getSubject());
        values.put(COLUMN_SUB_SUBJECT, test.getSubSubject());
        values.put(COLUMN_DATE, test.getDate());
        long insertId = database.insert(TABLE_TEST, null, values);
        test.setId(insertId);

        return test;

    }


    public ArrayList<Test> getAllTest() {
        ArrayList<Test> l = new ArrayList<>();
        String query = "select * from " + TABLE_TEST;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() >= 0) {

            while (cursor.moveToNext()) {
                String subject = cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT));
                String subSubject = cursor.getString(cursor.getColumnIndex(COLUMN_SUB_SUBJECT));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));

                Test Test = new Test(subject, subSubject, date, id);
                l.add(Test);
            }
        }
        return l;
    }

    public boolean isEmpty() {
        boolean rowExists;
        Cursor mCursor = database.rawQuery("select * from " + TABLE_TEST, null);
        if (mCursor.getCount() == 0)
            rowExists = false;
        else
            rowExists = true;

        return rowExists;
    }

    public long deleteByRow(long rowId) {
        return database.delete(SqlLiteHelperTest.TABLE_TEST, SqlLiteHelperTest.COLUMN_ID + "=" + rowId, null);
    }


}
