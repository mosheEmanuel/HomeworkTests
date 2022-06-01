package com.example.homeworktests;

import android.app.AlertDialog;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {

    AutoCompleteTextView acSubject;
    EditText etSubSubject, etPage, etExercises;
    TextView tvDate, tvNotifications;
    Spinner spinnerPriority;
    Button btnAdd;

    String subject;
    String subSubject;
    String page;
    String exercises;
    String date;
    int notifications;
    int priority;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        init();
        setAutoComplete();
        setSpinner();
    }

    public void init() {
        acSubject = findViewById(R.id.acSubject);

        etSubSubject = findViewById(R.id.etSubSubject);
        etPage = findViewById(R.id.etPage);
        etExercises = findViewById(R.id.etExercises);

        tvDate = findViewById(R.id.tvDate);
        tvNotifications = findViewById(R.id.tvNotifications);
        btnAdd = findViewById(R.id.btnAdd);

        spinnerPriority = findViewById(R.id.spinnerPriority);

        tvDate.setOnClickListener(this);
        tvNotifications.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }

    public void setAutoComplete() {
        String[] strAutoComplete = new String[]{"מתמטיקה", "אנגלית", "לשון", "עברית", "אזרחות", "גמרא", "תנך", "מחשבים"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strAutoComplete);
        acSubject.setAdapter(adapter);

    }

    public void setSpinner() {

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
        if (priority > 0) {
            subject = acSubject.getText().toString();
            subSubject = etSubSubject.getText().toString();
            page = etPage.getText().toString();
            exercises = etExercises.getText().toString();
            Homework homework = new Homework(0, subject, page, exercises, subSubject, date, notifications, priority);
            SqlLiteHelper sql = new SqlLiteHelper(this);
            sql.open();
            sql.createHomework(homework);
            sql.close();
            finish();
        } else Toast.makeText(this, "לא הוספת את כל הפרטים", Toast.LENGTH_SHORT).show();
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

}