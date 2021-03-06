package com.example.homeworktests;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworktests.adapter.HomeworkAdapter;
import com.example.homeworktests.adapter.TestAdapter;
import com.example.homeworktests.sql.SqlLiteHelperHomework;
import com.example.homeworktests.sql.SqlLiteHelperTest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener,
        HomeworkAdapter.HomeworkItmeClickListener, TestAdapter.TestItmeClickListener {

    Button btnTest;//הכפתור מראה את המבחנים
    Button btnHomework; // הכפתור מהראה את השעורי בית
    Button btnExit;

    FloatingActionButton fab; // כפתור עגול שמראה את שתי הכפתורים העגולים האחרים
    FloatingActionButton fabHomework; // כפתור שלמעביר אותך למסך activity_add_homework
    FloatingActionButton fabTest; // כפתור שלמעביר אותך למסך activity_add_test

    // אנמציות
    Animation fabOpen;
    Animation fabClose;
    Animation rotateForward;
    Animation rotateBackward;


    TextView tvOpen; // הטקסט שמופיע שאין שעורי בית או מבחנים

    LinearLayout linearLayout; // Layout של הכפתורים  btnTest btnHomework

    boolean animationIsOpen; // boolean שבודק האם האנמציות נפתחו
    boolean homeworkOrTest; // boolean שבודק על איזה כפתור אנחנו נמצאים עך  btnTest או btnHomework

    RecyclerView recyclerView; // ה recyclerView שמראה את השעורי בית/מבחנים

    ArrayList<Homework> allHomework; // רשימה שמכילה את כל השעורי הבית
    ArrayList<Test> allTest; // רשימה שמכילה את כל ההמבחנים

    HomeworkAdapter homeworkAdapter; // adapter של השעורי הבית
    TestAdapter testAdapter; // adapter של המבחנים
    SqlLiteHelperHomework sqlHomework; // הdb של השעורי בית
    SqlLiteHelperTest sqlTest; // הdp של המבחנים

    Dialog dialog;// דילוג


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

        tvOpen = findViewById(R.id.tvOpen);
        fab = findViewById(R.id.fab);
        fabHomework = findViewById(R.id.fabTest);
        fabTest = findViewById(R.id.fabHomeWork);

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);

        animationIsOpen = false;
        homeworkOrTest = true;

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
        } else if (v == btnExit) {
            dialog.cancel();
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
            if (homeworkOrTest)
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
        homeworkAdapter = new HomeworkAdapter(this, allHomework, this);
        recyclerView.setAdapter(homeworkAdapter);
        homeworkOrTest = true;


    }

    public void setTestRecyclerView() { // קובע RecyclerView למבחן

        sqlTest.open();
        allTest = sqlTest.getAllTest();
        sqlTest.close();
        testAdapter = new TestAdapter(this, allTest, this);
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
        if (animationIsOpen) {
            fab.startAnimation(rotateForward);
            fabHomework.startAnimation(fabClose);
            fabTest.startAnimation(fabClose);
            fabHomework.setClickable(false);
            fabTest.setClickable(false);
            animationIsOpen = false;
        } else {
            fab.startAnimation(rotateBackward);
            fabHomework.startAnimation(fabOpen);
            fabTest.startAnimation(fabOpen);
            fabHomework.setClickable(true);
            fabTest.setClickable(true);
            animationIsOpen = true;
        }
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {      // פעולת המחיקה בהחלקה

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteAlertDialog(viewHolder);
        }
    };

    public void deleteAlertDialog(RecyclerView.ViewHolder viewHolder) {   // מראה דילוג ששאול האם אתה בטוח רוצה למחוק
        AlertDialog.Builder builder = new AlertDialog.Builder(this).
                setTitle("Delete")
                .setMessage("בטוח שאתה רוצה למחוק")
                .setCancelable(false)
                .setPositiveButton("כן", (dialogInterface, i) -> {  // מגדיר את הכפתור החיובי
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
                }).setNegativeButton("לא", (dialogInterface, i) -> { // מגדיר את הכפתור השלילי
                    if (homeworkOrTest)
                        setHomeworkRecyclerView();
                    else
                        setTestRecyclerView();

                });
        AlertDialog dialog = builder.create();// נפעיל את הבילדר ונחזיר רפרנס ל דיאלוג
        dialog.show();

    }



    // מגדיר את ה menu
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // מראה את ה menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menuSms) {
            Intent intent = new Intent(this, SmsActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.menuMenuActivity) {
            Toast.makeText(this, "אתה נמצא במסך הנוכחי", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    // יוצר Dialog שמראה את כל הפרטים של השעורי הבית בגדול
    public void createHomeworkDialog(int position) {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_homework);
        dialog.setTitle("כל הפרטים");
        dialog.setCancelable(true);
        TextView tvSubject = dialog.findViewById(R.id.tvSubject);
        TextView tvSubSubject = dialog.findViewById(R.id.tvSubSubject);
        TextView tvPage = dialog.findViewById(R.id.tvPage);
        TextView tvExercise = dialog.findViewById(R.id.tvExercise);
        TextView tvDate = dialog.findViewById(R.id.tvDate);
        TextView tvPriority = dialog.findViewById(R.id.tvPriority);

        btnExit = dialog.findViewById(R.id.btnExit);

        btnExit.setOnClickListener(this);

        tvSubject.setText(allHomework.get(position).getSubject());
        tvSubSubject.setText(allHomework.get(position).getSubSubject());
        tvPage.setText(allHomework.get(position).getPage());
        tvExercise.setText(allHomework.get(position).getExercise());
        tvDate.setText(allHomework.get(position).getDate());
        tvPriority.setText(allHomework.get(position).getPriority());
        dialog.show();

    }
    // יוצר Dialog שמראה את כל הפרטים של המבחנים בגדול

    public void createTestDialog (int position) {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_test);
        dialog.setTitle("כל הפרטים");
        TextView tvSubject = dialog.findViewById(R.id.tvSubject);
        TextView tvSubSubject = dialog.findViewById(R.id.tvSubSubject);
        TextView tvDate = dialog.findViewById(R.id.tvDate);

        btnExit = dialog.findViewById(R.id.btnExit);

        btnExit.setOnClickListener(this);

        tvSubject.setText(allTest.get(position).getSubject());
        tvSubSubject.setText(allTest.get(position).getSubSubject());
        tvDate.setText(allTest.get(position).getDate());

        dialog.show();

    }

    // מגדיר לחיצה על Itme בrecyclerView
    @Override
    public void onHomeworkItmeClick(int position) {
    createHomeworkDialog(position);
    }

    // מגדיר לחיצה על Itme בrecyclerView
    @Override
    public void onTestItmeClick(int position) {
        createTestDialog(position);
    }
}