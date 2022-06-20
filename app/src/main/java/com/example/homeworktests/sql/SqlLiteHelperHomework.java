package com.example.homeworktests.sql;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.homeworktests.Homework;

import java.util.ArrayList;

public class SqlLiteHelperHomework extends SQLiteOpenHelper {



    public static final String DATABASENAME = "Homework.dp"; // שם הדטא ביס
    public static final String TABLE_HOMEWORK = "tblHomework";// שם הטבלה
    public static final int DATABASEVERSION = 1; // מספר גרסה

    public static final String COLUMN_ID = "HomeworkId"; // הID של העמודה

    public static final String COLUMN_SUBJECT = "subject"; // המקצוע
    public static final String COLUMN_SUB_SUBJECT = "subSubject";  // הנושא
    public static final String COLUMN_PAGE = "page"; //העמוד
    public static final String COLUMN_EXERCISE = "exercise"; // התרגלים
    public static final String COLUMN_PRIORITY = "priority"; // העדיפות
    public static final String COLUMN_DATE = "date"; // התאריך

    SQLiteDatabase database;

    // הקמת הטבלה
    private static final String CREATE_TABLE_HOMEWORK = "CREATE TABLE IF NOT EXISTS " + TABLE_HOMEWORK + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_SUBJECT + " VARCHAR, "
            + COLUMN_SUB_SUBJECT + " VARCHAR, " + COLUMN_PAGE + " VARCHAR, " + COLUMN_EXERCISE + " VARCHAR, " + COLUMN_PRIORITY + " INTEGER, "
            + COLUMN_DATE + " VARCHAR " + ");";
    // מערך עם כל השורות
    String[] allColumns = {COLUMN_ID, COLUMN_SUBJECT, COLUMN_SUB_SUBJECT, COLUMN_PAGE, COLUMN_EXERCISE,
            COLUMN_PRIORITY, COLUMN_DATE};
    //פעולה בונה
    public SqlLiteHelperHomework(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }
    // יוצר את הטבלה בפעם הראשונה
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_HOMEWORK);
    }

    @Override // פועל ברגע שמעדכנים את הטבלה
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOMEWORK);
        onCreate(db);
    }
    // פןתח את הטבלה לכתיבה
    public void open() {
        database = this.getWritableDatabase();
    }

    // יוצר עמודה חדשה
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
    // יוצר מכול עמודה עצם ומחזיר רשימה שכוללת את כל הפרטים
    public ArrayList<Homework> getAllHomework() {
        ArrayList<Homework> l = new ArrayList<>();
        String query = "select * from " + TABLE_HOMEWORK;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.getCount() >= 0) {

            while (cursor.moveToNext()) {
                String subject = "המקצוע: ";
                subject += cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT));
                String page = "העמוד: ";
                page += cursor.getString(cursor.getColumnIndex(COLUMN_PAGE));
                String exercise = "התרגיל: ";
                exercise += cursor.getString(cursor.getColumnIndex(COLUMN_EXERCISE));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                int priority = cursor.getInt(cursor.getColumnIndex(COLUMN_PRIORITY));
                long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));

                String sPriority = "העדיפות: ";
                if (priority == 1)
                    sPriority += "נמוכה";
                else if (priority == 2)
                    sPriority += "בנונית";
                else
                    sPriority += "גבוהה";
                Homework homework = new Homework(id, page, exercise, subject, date, sPriority);
                l.add(homework);
            }
        }
        return l;
    }
    // בודק האם הטבלה ריקה
    public boolean isEmpty() {
        boolean rowExists;
        Cursor mCursor = database.rawQuery("select * from " + TABLE_HOMEWORK, null);
        if (mCursor.getCount() == 0)
            rowExists = false;
        else
            rowExists = true;

        return rowExists;
    }
    // מוחק שורה מהטבלה על פי ID
    public long deleteByRow(long rowId) {
        return database.delete(SqlLiteHelperHomework.TABLE_HOMEWORK, SqlLiteHelperHomework.COLUMN_ID + "=" + rowId, null);
    }

//    public long updateByRow(Homework homework) {
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_ID, homework.getId());
//        values.put(COLUMN_SUBJECT, homework.getSubject());
//        values.put(COLUMN_SUB_SUBJECT, homework.getSubSubject());
//        values.put(COLUMN_PAGE, homework.getPage());
//        values.put(COLUMN_EXERCISE, homework.getExercise());
//        values.put(COLUMN_PRIORITY, homework.getPriority());
//        values.put(COLUMN_DATE, homework.getDate());
//        return database.update(SqlLiteHelperHomework.TABLE_HOMEWORK, values, SqlLiteHelperHomework.COLUMN_ID + "=" + homework.getId(), null);
//
//    }
//
//    public Homework getHomeworkById(long rowId) {
//        Cursor cursor = database.query(SqlLiteHelperHomework.TABLE_HOMEWORK, allColumns, SqlLiteHelperHomework.COLUMN_ID + "=" + rowId, null, null, null, null);
//        cursor.moveToFirst();
//        if (cursor.getCount() > 0) {
//            long id = cursor.getLong(cursor.getColumnIndex(SqlLiteHelperHomework.COLUMN_ID));
//            String subject = cursor.getString(cursor.getColumnIndex(SqlLiteHelperHomework.COLUMN_SUBJECT));
//            String subSubject = cursor.getString(cursor.getColumnIndex(SqlLiteHelperHomework.COLUMN_SUB_SUBJECT));
//            String page = cursor.getString(cursor.getColumnIndex(SqlLiteHelperHomework.COLUMN_PAGE));
//            String exercise = cursor.getString(cursor.getColumnIndex(SqlLiteHelperHomework.COLUMN_EXERCISE));
//            int priority = cursor.getInt(cursor.getColumnIndex(SqlLiteHelperHomework.COLUMN_PRIORITY));
//            String date = cursor.getString(cursor.getColumnIndex(SqlLiteHelperHomework.COLUMN_DATE));
//            Homework homework = new Homework(id, subject, subSubject, page, exercise, date, priority);
//            this.close();
//            return homework;
//        }
//        return null;
//    }


}

