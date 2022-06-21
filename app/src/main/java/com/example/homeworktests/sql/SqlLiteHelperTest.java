package com.example.homeworktests.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.homeworktests.Test;

import java.util.ArrayList;

public class SqlLiteHelperTest extends SQLiteOpenHelper {

    public static final String DATABASENAME = "Test.db"; // שם הדטא ביס
    public static final String TABLE_TEST = "tblTest";// שם הטבלה
    public static final int DATABASEVERSION = 1;// מספר גרסה

    public static final String COLUMN_ID = "testId";// הID של העמודה

    public static final String COLUMN_SUBJECT = "subject";// המקצוע
    public static final String COLUMN_SUB_SUBJECT = "subSubject";  // הנושא
    public static final String COLUMN_DATE = "date";  // הנושא

    SQLiteDatabase database;

    // הקמת הטבלה
    private static final String CREATE_TABLE_Test = "CREATE TABLE IF NOT EXISTS " + TABLE_TEST + "(" + COLUMN_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_SUBJECT + " VARCHAR, "
            + COLUMN_SUB_SUBJECT + " VARCHAR, " + COLUMN_DATE + " VARCHAR " + ");";

    // מערך עם כל השורות
    String[] allColumns = {COLUMN_SUBJECT, COLUMN_SUB_SUBJECT, COLUMN_DATE};

    //פעולה בונה
    public SqlLiteHelperTest(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }
    // יוצר את הטבלה בפעם הראשונה
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_Test);

    }
    // פועל ברגע שמעדכנים את הטבלה
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST);
        onCreate(db);
    }
    // פןתח את הטבלה לכתיבה
    public void open() {
        database = this.getWritableDatabase();
    }

    // יוצר עמודה חדשה
    public Test createTest(Test test) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_SUBJECT, test.getSubject());
        values.put(COLUMN_SUB_SUBJECT, test.getSubSubject());
        values.put(COLUMN_DATE, test.getDate());
        long insertId = database.insert(TABLE_TEST, null, values);
        test.setId(insertId);

        return test;

    }

    // יוצר מכול עמודה עצם ומחזיר רשימה שכוללת את כל הפרטים
    public ArrayList<Test> getAllTest() {
        ArrayList<Test> l = new ArrayList<>();
        String query = "select * from " + TABLE_TEST;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() >= 0) {

            while (cursor.moveToNext()) {
                String subject = "המקצוע: ";
                subject += cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT));
                String subSubject = "הנושא: ";
                subSubject += cursor.getString(cursor.getColumnIndex(COLUMN_SUB_SUBJECT));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));

                Test Test = new Test(subject, subSubject, date, id);
                l.add(Test);
            }
        }
        return l;
    }
    // בודק האם הטבלה ריקה
    public boolean isEmpty() {
        boolean rowExists;
        Cursor mCursor = database.rawQuery("select * from " + TABLE_TEST, null);
        if (mCursor.getCount() == 0)
            rowExists = false;
        else
            rowExists = true;

        return rowExists;
    }
    // מוחק שורה מהטבלה על פי ID
    public long deleteByRow(long rowId) {
        return database.delete(SqlLiteHelperTest.TABLE_TEST, SqlLiteHelperTest.COLUMN_ID + "=" + rowId, null);
    }


}
