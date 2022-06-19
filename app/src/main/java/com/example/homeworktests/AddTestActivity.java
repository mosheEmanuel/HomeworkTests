package com.example.homeworktests;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.homeworktests.sql.SqlLiteHelperTest;

import java.util.Calendar;

public class AddTestActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    AutoCompleteTextView acTestSubject;
    EditText etTestSubSubject;
    TextView tvTestDate;
    Button btnTestAdd;
    String date;
    String subject;
    String subSubject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);
        init();
        setAutoComplete();
    }

    public void init() {
        acTestSubject = findViewById(R.id.acTestSubject);
        etTestSubSubject = findViewById(R.id.etTestSubSubject);
        tvTestDate = findViewById(R.id.tvTestDate);
        btnTestAdd = findViewById(R.id.btnTestAdd);

        btnTestAdd.setOnClickListener(this);
        tvTestDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == tvTestDate)
        {
            setTvDate();
        }
        if(view==btnTestAdd) {
            subject = acTestSubject.getText().toString();
            subSubject = etTestSubSubject.getText().toString();
            Test test = new Test(subject, subSubject, date, 0);
            SqlLiteHelperTest sql = new SqlLiteHelperTest(this);
            sql.open();
            sql.createTest(test);
            sql.close();
            finish();
        }
    }

    public void setAutoComplete() {
        String[] strAutoComplete = new String[]{"מתמטיקה", "אנגלית", "לשון", "עברית", "אזרחות", "גמרא", "תנך", "מחשבים"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strAutoComplete);
        acTestSubject.setAdapter(adapter);
    }

    public void setTvDate() {
        int nowYear = Calendar.getInstance().get(Calendar.YEAR);
        int nowMonth = Calendar.getInstance().get(Calendar.MONTH);
        int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, nowYear, nowMonth, nowDay);
        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        month++;
        if (month > 9)
            date = dayOfMonth + "/" + month + "/" + (year % 100);
        else
            date = dayOfMonth + "/0" + month + "/" + (year % 100);

        String str = "אתה בחרת :" + date;
        tvTestDate.setText(str);
    }


}