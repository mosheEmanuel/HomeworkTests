package com.example.homeworktests;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OpenActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etFirsName, etLastName;
    TextView tvClass;
    String theChoice;
    Button btnNext;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_open);

        firstScreen();
        init();
    }

    public void firstScreen() {
        sp = getSharedPreferences("isDan", 0);
        boolean isDan = sp.getBoolean("isDan", false);
        if (isDan) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void init() {
        etFirsName = findViewById(R.id.etFirsName);
        etLastName = findViewById(R.id.etLastName);
        tvClass = findViewById(R.id.tvClass);
        btnNext = findViewById(R.id.btnNext);

        tvClass.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tvClass)
            selectedClass();
        else if (v == btnNext) {
            if (!etLastName.toString().isEmpty() && !etFirsName.toString().isEmpty() && theChoice != null) {
                editor = sp.edit();
                editor.putBoolean("isDan", true);
                editor.apply();

                sp = getSharedPreferences("details", 0);
                editor = sp.edit();

                editor.putString("FirsName", etFirsName.getText().toString());
                editor.putString("LastName", etLastName.getText().toString());
                editor.putString("theChoice", theChoice);
                editor.apply();
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                finish();

            }
        }
    }


    public void selectedClass() {
        String[] classArray = {"ז", "ח", "ט", "י", "יא", "יב"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("תבחר כיתה")
                .setCancelable(false);

        builder.setSingleChoiceItems(classArray, -1, (dialog, which) -> theChoice = classArray[which])
                .setPositiveButton("OK", (dialog, which) -> tvClass.setText(theChoice))
                .setNegativeButton("cancel", null)
                .show();
    }


}
