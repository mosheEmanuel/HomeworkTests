package com.example.homeworktests;

import android.Manifest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.app.PendingIntent;
import android.content.Intent;
import android.provider.Settings;
import android.telephony.SmsManager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class SmsActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    EditText message;
    Button sendSms;
    boolean isGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        init();

    }

    // מגדיר את כל המשתנים
    public void init() {
        message = findViewById(R.id.etSms);
        sendSms = findViewById(R.id.btnSms);

        sendSms.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (!message.getText().toString().equals("")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                permission();
                if (isGranted) {
                    sendSmsFunction();
                }
            } else
                sendSmsFunction();
        } else
            Toast.makeText(getApplicationContext(), "הודעה לא יכולה להיות ריקה", Toast.LENGTH_LONG).show();

    }

    public void dialog()//בונה את הדיאלוג אם המשתמש לא נתן הרשאה ומנסה עוד פעם לשלוח הודעה
    {
        AlertDialog.Builder builder = new AlertDialog
                .Builder(this);
        builder.setMessage("בשביל לשלוח הודעה למפתח אתה צריך לאשר את הבקשה");
        builder.setTitle("צריך הרשאה ל SMS");
        builder.setCancelable(true);


        builder.setPositiveButton(
                "אשר", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        ActivityCompat.requestPermissions(SmsActivity.this, new String[]{Manifest.permission.SEND_SMS}, 200);
                    }
                });
        builder.setNegativeButton(
                "דחה", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void sendSmsFunction() {//פעולה ששולחת sms למפתח

        sp = getSharedPreferences("details", 0);
        String strFirsName = sp.getString("FirsName", null);
        String strLastName = sp.getString("LastName", null);
        String strTheChoice = sp.getString("theChoice", null);

        String msg = "שלום אני " + strFirsName + " " + strLastName + " מכיתה " + strTheChoice;
        msg += " " + message.getText().toString();


        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);


        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage("0506558504", null, msg, pi, null);

        Toast.makeText(getApplicationContext(), "ההודעה נשלחה", Toast.LENGTH_LONG).show();
    }

    public void permission() {//פעולה שמטפלת בהרשאה
        sp = getSharedPreferences("data", 0);
        editor = sp.edit();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                dialog();


            } else if (!sp.getBoolean("firstCheckPermission", false)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 100);
                editor.putBoolean("firstCheckPermission", true);
                editor.commit();

            } else {
                Toast.makeText(this, "תאשר אתה הרשאת SMS בהגדרות", Toast.LENGTH_LONG);
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                this.startActivity(intent);
            }

        } else//יש הרשאה
            isGranted = true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {//פעולה שמופעלת אחרי בקשת הרשאה
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isGranted = true;
            } else {
                isGranted = false;
            }
        }
        if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isGranted = true;

            } else
                isGranted = false;
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menuSms) {

            Toast.makeText(this, "אתה נמצא במסך הנוכחי", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menuMenuActivity) {
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

}