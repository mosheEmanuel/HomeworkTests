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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etfname, etLname;
    TextView tvClass;
    String[] classArray = {"ז", "ח", "ט", "י", "יא", "יב"};
    String theChoice = null;
    Button btnxet;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("isDan", 0);
        boolean isDan = sp.getBoolean("isDan", true);
        if (!isDan) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
        init();
    }


    public void init() {
        etfname = findViewById(R.id.etfname);
        etLname = findViewById(R.id.etLname);
        tvClass = findViewById(R.id.tvClass);
        btnxet = findViewById(R.id.btnxet);

        tvClass.setOnClickListener(this);
        btnxet.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v == tvClass)
            selectedClass();
        else if (v == btnxet) {
            if (!etLname.toString().isEmpty() && !etfname.toString().isEmpty() && theChoice != null) {
                editor = sp.edit();
                editor.putBoolean("isDan", false);
                editor.apply();

                sp = getSharedPreferences("Details",0);
                editor = sp.edit();

                editor.putString("fname",etfname.getText().toString());
                editor.putString("Lname",etfname.getText().toString());
                editor.putString("theChoice",theChoice);

                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                finish();

            }
        }
    }


    public void selectedClass() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("תבחר כיתה");
        builder.setCancelable(false);

        builder.setSingleChoiceItems(classArray, 0, (dialog, which) -> theChoice = classArray[which]);

        builder.setPositiveButton("OK", (dialog, which) -> tvClass.setText(theChoice)
        );

        builder.setNegativeButton("cancel", null);
        builder.show();
    }


}
