package com.example.homeworktests;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    EditText etSubject, etSubSubject;
    TextView tvDate, tvNotifications;
    Spinner spinner;
    Button btnAdd;

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
        spinner = findViewById(R.id.spinner);

        tvDate.setOnClickListener(this);
        tvNotifications.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }

    public void setSpinner() {
        priority = new ArrayList<>();
        priority.add("נמוכה");
        priority.add("בנונית");
        priority.add("גבוהה");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, priority);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tvDate) {
            setTvDate();
        } else if (v == tvNotifications) {

        }
    }


    public void setTvDate() {
        Calendar systemCalender = Calendar.getInstance();
        int year = systemCalender.get(Calendar.YEAR);
        int month = systemCalender.get(Calendar.MONTH);
        int day = systemCalender.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new SetDate(), year, month, day);
        datePickerDialog.show();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "לא בחרת כלום", Toast.LENGTH_SHORT).show();
    }

    public class SetDate implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            monthOfYear++;
            String str = "אתה בחרת :" + dayOfMonth + "/0" + monthOfYear + "/" + year % 100;
            Toast.makeText(AddActivity.this, str, Toast.LENGTH_LONG).show();
            tvDate.setText(str);
        }
    }
}