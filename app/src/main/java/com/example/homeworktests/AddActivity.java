package com.example.homeworktests;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {

    EditText etSubject, etSubSubject, etPage;
    TextView tvDate, tvNotifications;
    Spinner spinnerPriority;
    Button btnAdd;

    String subject;
    String subSubject;
    String page;
    int notifications;
    int priority;
    int year;
    int month;
    int dayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        init();
        setSpinner();
    }

    public void init() {
        etSubject = findViewById(R.id.etSubject);
        etSubSubject = findViewById(R.id.etSubSubject);
        etPage = findViewById(R.id.etPage);
        tvDate = findViewById(R.id.tvDate);
        tvNotifications = findViewById(R.id.tvNotifications);
        btnAdd = findViewById(R.id.btnAdd);
        spinnerPriority = findViewById(R.id.spinnerPriority);

        tvDate.setOnClickListener(this);
        tvNotifications.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tvDate) {
            setTvDate();
        } else if (v == tvNotifications) {
            setNotifications();
        } else if (btnAdd == v) {
            setBtnAdd();
        }
    }

    public void setBtnAdd() {
        subject = etSubject.getText().toString();
        subSubject = etSubSubject.getText().toString();
        page = etPage.getText().toString();
        Homework homework = new Homework(page, subject, subSubject, notifications, priority, year, month, dayOfMonth);
        SqlLiteHelper sql = new SqlLiteHelper(this);
        sql.open();
        sql.createHomework1(homework);
        sql.close();
        finish();
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
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        month++;
        String str = "אתה בחרת :" + dayOfMonth + "/" + month + "/" + year % 100;
        tvDate.setText(str);
    }

    public void setNotifications() {
        String[] strNotifications = {"כל יום", "כל יום חול(ראשון-חמישי)", "פעם בשבוע", " פעם בשבועים", "פעם בחודש"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("תבחר את כמות התראות")
                .setCancelable(true)
                .setItems(strNotifications, (dialogInterface, i) -> {
                    tvNotifications.setText(strNotifications[i]);
                    notifications = i;
                });
        builder.show();
    }

    public void setSpinner() {

        List<String> lstPriority;

        lstPriority = new ArrayList<>();
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
            Toast.makeText(this, "תבחר עדיפות", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}