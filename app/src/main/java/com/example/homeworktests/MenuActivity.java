package com.example.homeworktests;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    LinearLayout linearLayout;
    ImageButton ibFilter;
    Button btnAll, btnTest, btnHomeWork;
    FloatingActionButton fab, fabTest, fabHomeWork;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    TextView tvOpen;
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTextOpen();
    }

    public void init() {
        linearLayout = findViewById(R.id.linearLayout);

        ibFilter = findViewById(R.id.ibFilter);

        btnAll = findViewById(R.id.btnAll);
        btnTest = findViewById(R.id.btnTest);
        btnHomeWork = findViewById(R.id.btnHomeWork);

        tvOpen = findViewById(R.id.tvOpen);
        fab = findViewById(R.id.fab);
        fabTest = findViewById(R.id.fabTest);
        fabHomeWork = findViewById(R.id.fabHomeWork);

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);

        ibFilter.setOnClickListener(this);

        btnAll.setOnClickListener(this);
        btnTest.setOnClickListener(this);
        btnHomeWork.setOnClickListener(this);

        fab.setOnClickListener(this);
        fabTest.setOnClickListener(this);
        fabHomeWork.setOnClickListener(this);

        fab.setOnLongClickListener(this);
        fabTest.setOnLongClickListener(this);
        fabHomeWork.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == fab) {
            animateFab();
        } else if (v == fabTest) {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
            animateFab();
        } else if (v == fabHomeWork) {
            animateFab();
        }
    }

    public void setTextOpen() {
        SqlLiteHelper sql = new SqlLiteHelper(this);
        sql.open();
        if (!sql.isEmpty()) {
            tvOpen.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.INVISIBLE);
            SharedPreferences sp = getSharedPreferences("details", 0);
            String strFirsName = sp.getString("FirsName", null);
            String strLastName = sp.getString("LastName", null);
            tvOpen.setText(String.format("ברוך הבא %s %s\nלא הוספת שעורי בית", strFirsName, strLastName));
        } else {
            tvOpen.setVisibility(View.INVISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
        }
        sql.close();

    }

    @Override
    public boolean onLongClick(View v) {

        if (v == fabTest) {
            Toast.makeText(this, "כפתור להוספת שעורי בית", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (v == fabHomeWork) {
            Toast.makeText(this, "כפתור להוספת מבחן", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void animateFab() {
        if (isOpen) {
            fab.startAnimation(rotateForward);
            fabTest.startAnimation(fabClose);
            fabHomeWork.startAnimation(fabClose);
            fabTest.setClickable(false);
            fabHomeWork.setClickable(false);
            isOpen = false;
        } else {
            fab.startAnimation(rotateBackward);
            fabTest.startAnimation(fabOpen);
            fabHomeWork.startAnimation(fabOpen);
            fabTest.setClickable(true);
            fabHomeWork.setClickable(true);
            isOpen = true;
        }
    }
}