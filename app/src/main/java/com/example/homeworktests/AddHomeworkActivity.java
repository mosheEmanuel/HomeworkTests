package com.example.homeworktests;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homeworktests.sql.SqlLiteHelperHomework;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddHomeworkActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {

    AutoCompleteTextView acSubject; // text view עם השלמה אוטמטית של המקצוע
    EditText etSubSubject; // נושא השיעורי הבית
    EditText etPage; // העמודים
    EditText etExercises; // התרגילם
    TextView tvDate; // טקסט שלוחצים עליו נותן לך לבחור תאריך
    Spinner spinnerPriority; // ספינר שנותן לך לבחור עדיפות

    Button btnAdd; // הכפתור שמוסיף שעורי בית

    String date; // String שעליו יופיע התאריך

    int priority; // העדיפות נשמר במספר 0-2


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_homework);

        init();
        setAutoComplete();
        setSpinner();
    }

    public void init() { // מגדיר את כל המשתנים
        acSubject = findViewById(R.id.acSubject);

        etSubSubject = findViewById(R.id.etSubSubject);
        etPage = findViewById(R.id.etPage);
        etExercises = findViewById(R.id.etExercises);

        tvDate = findViewById(R.id.tvDate);
        btnAdd = findViewById(R.id.btnAdd);

        spinnerPriority = findViewById(R.id.spinnerPriority);

        tvDate.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == tvDate) {
            setTvDate();
        } else
            setBtnAdd();
    }

    public void setAutoComplete() { // קובע את המלים בהשלמה האוטומטית
        String[] strAutoComplete = new String[]{"מתמטיקה", "אנגלית", "לשון", "עברית", "אזרחות", "גמרא", "תנך", "מחשבים"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strAutoComplete);
        acSubject.setAdapter(adapter);

    }

    public void setSpinner() { //  מגדיר את הספינר של העדיפפות

        List<String> lstPriority = new ArrayList<>();
        lstPriority.add("תבחר עדיפות");
        lstPriority.add("נמוכה");
        lstPriority.add("בנונית");
        lstPriority.add("גבוהה");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lstPriority);
        spinnerPriority.setAdapter(arrayAdapter);
        spinnerPriority.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i != 0) {
//            String item = adapterView.getItemAtPosition(i).toString();
            priority = i;
        } else
            priority = -1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void setBtnAdd() { // מגדיר את כפתור ההוספה
        if (priority > 0) {
            String subject  = acSubject.getText().toString();
            String subSubject = etSubSubject.getText().toString();
            String  page = etPage.getText().toString();
            String  exercises = etExercises.getText().toString();
            Homework homework = new Homework(0, subject, page, exercises, subSubject, date, priority);
            SqlLiteHelperHomework sql = new SqlLiteHelperHomework(this);
            sql.open();
            sql.createHomework(homework);
            sql.close();
            finish();
        } else Toast.makeText(this, "לא הוספת את כל הפרטים", Toast.LENGTH_SHORT).show();
    }

    public void setTvDate() { // מגדיר את בחירת התאריך
        int nowYear = Calendar.getInstance().get(Calendar.YEAR);
        int nowMonth = Calendar.getInstance().get(Calendar.MONTH);
        int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, nowYear, nowMonth, nowDay);
        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    @Override // מגדיר את בחירת התאריך
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        month++;
        if (month > 9)
            date = dayOfMonth + "/" + month + "/" + (year % 100);
        else
            date = dayOfMonth + "/0" + month + "/" + (year % 100);

        String str = "אתה בחרת :" + date;
        tvDate.setText(str);
    }




}