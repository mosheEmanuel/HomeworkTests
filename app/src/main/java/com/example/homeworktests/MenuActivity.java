package com.example.homeworktests;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    Button btnTest, btnHomework;
    ImageButton ibFilter;
    FloatingActionButton fab, fabHomework, fabTest;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    TextView tvOpen;
    boolean isOpen = false;

    RecyclerView recyclerView;

    ArrayList<Homework> allHomework;
    ArrayList<Test> allTest;

    HomeworkAdapter homeworkAdapter;
    TestAdapter testAdapter;
    SqlLiteHelperHomework sqlHomework;
    SqlLiteHelperTest sqlTest;

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

        btnTest = findViewById(R.id.btnTest);
        btnHomework = findViewById(R.id.btnHomework);

        ibFilter = findViewById(R.id.ibFilter);

        tvOpen = findViewById(R.id.tvOpen);
        fab = findViewById(R.id.fab);
        fabHomework = findViewById(R.id.fabTest);
        fabTest = findViewById(R.id.fabHomeWork);

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);

        ibFilter.setOnClickListener(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fab.setOnClickListener(this);
        fabHomework.setOnClickListener(this);
        fabTest.setOnClickListener(this);

        fab.setOnLongClickListener(this);
        fabHomework.setOnLongClickListener(this);
        fabTest.setOnLongClickListener(this);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View v) {
        if (v == fab) {
            animateFab();
        } else if (v == fabHomework) {
            Intent intent = new Intent(this, AddHomeworkActivity.class);
            startActivity(intent);
            animateFab();
        } else if (v == fabTest) {
            Intent intent = new Intent(this, AddTestActivity.class);
            startActivity(intent);
            animateFab();
        } else if (v == btnHomework) {
            setHomeworkRecyclerView();
        } else if (v == btnTest) {
            setTestRecyclerView();
        }

    }

    public void setTextOpen() {
        SqlLiteHelperHomework sql = new SqlLiteHelperHomework(this);
        sql.open();
        if (!sql.isEmpty()) {
            tvOpen.setVisibility(View.VISIBLE);
            ibFilter.setVisibility(View.INVISIBLE);
            SharedPreferences sp = getSharedPreferences("details", 0);
            String strFirsName = sp.getString("FirsName", null);
            String strLastName = sp.getString("LastName", null);
            tvOpen.setText(String.format("ברוך הבא %s %s\nלא הוספת שעורי בית", strFirsName, strLastName));
        } else {
            tvOpen.setVisibility(View.INVISIBLE);
            ibFilter.setVisibility(View.VISIBLE);
            setHomeworkRecyclerView();
        }
        sql.close();

    }

    public void setHomeworkRecyclerView() {

        sqlHomework = new SqlLiteHelperHomework(this);
        sqlHomework.open();
        allHomework = sqlHomework.getAllHomework();
        sqlHomework.close();
        homeworkAdapter = new HomeworkAdapter(this, allHomework);
        recyclerView.setAdapter(homeworkAdapter);
    }

    public void setTestRecyclerView() {
        sqlTest = new SqlLiteHelperTest(this);
        sqlTest.open();
        allTest = sqlTest.getAllTest();
        sqlTest.close();
        testAdapter = new TestAdapter(this, allTest);
        recyclerView.setAdapter(testAdapter);


    }

    @Override
    public boolean onLongClick(View v) {

        if (v == fabHomework) {
            Toast.makeText(this, "כפתור להוספת שעורי בית", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (v == fabTest) {
            Toast.makeText(this, "כפתור להוספת מבחן", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void animateFab() {
        if (isOpen) {
            fab.startAnimation(rotateForward);
            fabHomework.startAnimation(fabClose);
            fabTest.startAnimation(fabClose);
            fabHomework.setClickable(false);
            fabTest.setClickable(false);
            isOpen = false;
        } else {
            fab.startAnimation(rotateBackward);
            fabHomework.startAnimation(fabOpen);
            fabTest.startAnimation(fabOpen);
            fabHomework.setClickable(true);
            fabTest.setClickable(true);
            isOpen = true;
        }
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            sqlHomework.open();
            sqlHomework.deleteByRow(allHomework.get(viewHolder.getAdapterPosition()).getId());
            sqlHomework.close();
            allHomework.remove(viewHolder.getAdapterPosition());
            homeworkAdapter.notifyDataSetChanged();


        }
    };
}