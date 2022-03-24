package com.example.homeworktests;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    ImageButton ibFilter;
    Button btnAll, btnTest, btnHomeWork;
    FloatingActionButton fab, fab1, fab2;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    TextView tvOpen;
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        SqlLiteHelper sql = new SqlLiteHelper(this);

        init();
        setTextOpen();
    }

    public void init() {

        ibFilter = findViewById(R.id.ibFilter);

        btnAll = findViewById(R.id.btnAll);
        btnTest = findViewById(R.id.btnTest);
        btnHomeWork = findViewById(R.id.btnHomeWork);

        tvOpen = findViewById(R.id.tvOpen);

        fab = findViewById(R.id.fab);
        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);

        ibFilter.setOnClickListener(this);

        btnAll.setOnClickListener(this);
        btnTest.setOnClickListener(this);
        btnHomeWork.setOnClickListener(this);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);

        fab.setOnLongClickListener(this);
        fab1.setOnLongClickListener(this);
        fab2.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == fab) {
            animateFab();
        } else if (v == fab1) {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
            animateFab();
        } else if (v == fab2) {
            animateFab();
        }
    }

    public void setTextOpen() {
        SharedPreferences sp = getSharedPreferences("details", 0);
        String strFirsName = sp.getString("FirsName", null);
        String strLastName = sp.getString("LastName", null);
        tvOpen.setText(String.format("ברוך הבא %s %s\nלא הוספת שעורי בית", strFirsName, strLastName));
    }

    @Override
    public boolean onLongClick(View v) {

        if (v == fab1) {
            Toast.makeText(this, "הוספת שעורי בית", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (v == fab2) {
            Toast.makeText(this, "הוספת מבחן", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void animateFab() {
        if (isOpen) {
            fab.startAnimation(rotateForward);
            fab1.startAnimation(fabClose);
            fab2.startAnimation(fabClose);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isOpen = false;
        } else {
            fab.startAnimation(rotateBackward);
            fab1.startAnimation(fabOpen);
            fab2.startAnimation(fabOpen);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isOpen = true;
        }
    }
}