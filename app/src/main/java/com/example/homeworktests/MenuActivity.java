package com.example.homeworktests;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

    LinearLayout linearLayout;

    boolean isOpen = false;
    boolean homeworkOrTest = true;

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

    public void init() { // מגדיר את כל המשתנים

        btnTest = findViewById(R.id.btnTest);
        btnHomework = findViewById(R.id.btnHomework);

        btnTest.setOnClickListener(this);
        btnHomework.setOnClickListener(this);

        linearLayout = findViewById(R.id.linearLayout);

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

        sqlHomework = new SqlLiteHelperHomework(this);
        sqlTest = new SqlLiteHelperTest(this);

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

    public void setTextOpen() {  // בודק האם יש נתונים ב sql ואם אין מראה משפט פתיחה
        sqlHomework.open();
        sqlTest.open();
        if (!sqlHomework.isEmpty() && !sqlTest.isEmpty()) {
            tvOpen.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.INVISIBLE);
            SharedPreferences sp = getSharedPreferences("details", 0);
            String strFirsName = sp.getString("FirsName", null);
            String strLastName = sp.getString("LastName", null);
            tvOpen.setText(String.format("ברוך הבא %s %s\nלא הוספת שעורי בית", strFirsName, strLastName));
        } else {
            tvOpen.setVisibility(View.INVISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            if(homeworkOrTest)
            setHomeworkRecyclerView();
            else
                setTestRecyclerView();
        }
        sqlHomework.close();
        sqlTest.close();

    }

    public void setHomeworkRecyclerView() { // קובע RecyclerView לשעורי הבית

        sqlHomework.open();
        allHomework = sqlHomework.getAllHomework();
        sqlHomework.close();
        homeworkAdapter = new HomeworkAdapter(this, allHomework);
        recyclerView.setAdapter(homeworkAdapter);
        homeworkOrTest = true;

    }

    public void setTestRecyclerView() { // קובע RecyclerView למבחן

        sqlTest.open();
        allTest = sqlTest.getAllTest();
        sqlTest.close();
        testAdapter = new TestAdapter(this, allTest);
        recyclerView.setAdapter(testAdapter);
        homeworkOrTest = false;


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

    public void animateFab() { // קובע את האנמציות
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

    public void deleteAlertDialog(RecyclerView.ViewHolder viewHolder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).
                setTitle("Delete")
                .setMessage("בטוח שאתה רוצה למחוק")
                .setCancelable(false)
                .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (homeworkOrTest) {
                            sqlHomework.open();
                            sqlHomework.deleteByRow(allHomework.get(viewHolder.getAdapterPosition()).getId());
                            sqlHomework.close();
                            allHomework.remove(viewHolder.getAdapterPosition());
                            homeworkAdapter.notifyDataSetChanged();
                        } else {
                            sqlTest.open();
                            sqlTest.deleteByRow(allTest.get(viewHolder.getAdapterPosition()).getId());
                            sqlTest.close();
                            allTest.remove(viewHolder.getAdapterPosition());
                            testAdapter.notifyDataSetChanged();
                        }
                    }
                }).setNegativeButton("לא", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (homeworkOrTest)
                            setHomeworkRecyclerView();
                        else
                            setTestRecyclerView();

                    }
                });
        AlertDialog dialog = builder.create();// נפעיל את הבילדר ונחזיר רפרנס ל דיאלוג
        dialog.show();

    }

    // פעולת המחיקה בהחלקה
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteAlertDialog(viewHolder);
        }
    };

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.menuSettings) {

        } else if (id == R.id.menuSms) {
            Intent intent = new Intent(this, SmsActivity.class);
            startActivity(intent);
        }
        return true;
    }


}