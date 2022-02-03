package com.example.homeworktests;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout relativeLayout;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        init();
    }

    public void init() {
        relativeLayout = findViewById(R.id.relativeLayout);
        linearLayout = findViewById(R.id.linearLayout);

    }

    @Override
    public void onClick(View view) {

    }
}