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

public class AddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    EditText etSubject, etSubSubject;
    TextView tvDate, tvNotifications;
    Spinner spinnerPriority;
    Button btnAdd;
    String theChoice;
    List<String> priority;

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
        tvDate = findViewById(R.id.tvDate);
        tvNotifications = findViewById(R.id.tvNotifications);
        btnAdd = findViewById(R.id.btnAdd);
        spinnerPriority = findViewById(R.id.spinnerPriority);

        tvDate.setOnClickListener(this);
        tvNotifications.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }

    public void setSpinner() {
        priority = new ArrayList<>();
        priority.add("נמוכה");
        priority.add("בנונית");
        priority.add("גבוהה");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, priority);
        spinnerPriority.setAdapter(arrayAdapter);
        spinnerPriority.setOnItemSelectedListener(this);
    }

    public void setTvDate() {
        Calendar systemCalender = Calendar.getInstance();
        int nowYear = systemCalender.get(Calendar.YEAR);
        int nowMonth = systemCalender.get(Calendar.MONTH);
        int nowDay = systemCalender.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new SetDate(), nowYear, nowMonth, nowDay);
        datePickerDialog.show();
    }

    public void setNotifications() {
        String[] notifications = {"כל יום", "כל יום חול(ראשון-חמישי)", "פעם בשבוע", " פעם בשבועים", "פעם בחודש"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("תבחר את כמות התראות")
                .setCancelable(true)
                .setItems(notifications, (dialogInterface, i) -> {
                    theChoice = notifications[i];
                    tvNotifications.setText(theChoice);
                });
        builder.show();
    }

    @Override
    public void onClick(View v) {
        if (v == tvDate) {
            setTvDate();
        } else if (v == tvNotifications) {
            setNotifications();
        } else if (btnAdd == v) {

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public class SetDate implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            Calendar systemCalender = Calendar.getInstance();
            int nowYear = systemCalender.get(Calendar.YEAR);
            int nowMonth = systemCalender.get(Calendar.MONTH);
            int nowDay = systemCalender.get(Calendar.DAY_OF_MONTH);

            if (year > nowYear || (year == nowYear && monthOfYear > nowMonth) || (year == nowYear && monthOfYear == nowMonth && dayOfMonth >= nowDay)) {
                monthOfYear++;
                String str = "אתה בחרת :" + dayOfMonth + "/0" + monthOfYear + "/" + year % 100;
                Toast.makeText(AddActivity.this, str, Toast.LENGTH_LONG).show();
                tvDate.setText(str);
            } else {
                tvDate.setText("בחר תאריך הגשה");
                Toast.makeText(AddActivity.this, "תבחר תאריך מאוחר מהיום", Toast.LENGTH_SHORT).show();
            }
        }
    }
}