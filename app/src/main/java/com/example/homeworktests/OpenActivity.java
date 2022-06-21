package com.example.homeworktests;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class OpenActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etFirsName; // EditText של השם הפרטי
    EditText etLastName;// EditText של השם המשפחה
    TextView tvClass; //  TextView שממנו יצא AlertDialog עם הכיתות
    String theChoice; //  String עם הבחריה של הכיתה
    Button btnNext; // כפתור שמוביל אותך למסך הבא
    SharedPreferences sp;// SharedPreferences שומר את הנתונים ומוציא
    SharedPreferences.Editor editor; // SharedPreferences.Editor שמאפשר הכנסת הנתונים
    PendingIntent pendingIntent;//תכונה שאחראית לשלוח את המשתמשלactivity Main כשהוא לוחץ על ההתראה.
    AlarmManager alarmManager;// תכונה שמתחברת לService המערכת שלתזמון ההתראות


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_open);

        init();
        startNotification();
        firstScreen();
    }

    // מגדיר את כל המשתנים
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
                setBtnNext();
            }
        }
    }

    // בודע האם זה פעם ראשונה שלך במסך עם לא מעביר אותך למסך הבא
    public void firstScreen() {
        sp = getSharedPreferences("haveName", 0);
        boolean haveName = sp.getBoolean("haveName", false);
        if (haveName) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }


// קובע מה קורה אם לוחצים על כפתור נקסט
    public void setBtnNext() {
        editor = sp.edit();
        editor.putBoolean("haveName", true);
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
    // מגדיר את AlertDialog של הכיתות
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
    // מגדיר את Service של ההתראות לפי שעה
    public void startNotification() {
        sp = getSharedPreferences("notification", 0);
        if (sp.getBoolean("hasNotification", true)) {
            Intent intent = new Intent(this, NotificationsReceiver.class);

            //שליחה לברודקסט
            pendingIntent = PendingIntent.getBroadcast(
                    this.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);//התחברות ל service

            Calendar calendar = Calendar.getInstance();

            calendar.set(Calendar.HOUR_OF_DAY, 9);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, pendingIntent);//הפעלה חוזרת
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("hasNotification", false);
            editor.apply();
        }
    }
}
