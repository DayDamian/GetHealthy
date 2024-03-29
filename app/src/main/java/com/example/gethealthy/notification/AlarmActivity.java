package com.example.gethealthy.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gethealthy.R;
import com.example.gethealthy.fragment.WaterFragment;

public class AlarmActivity extends AppCompatActivity {

    EditText editWater;
    Button setWaterButton, deleteWaterButton;
    TextView waterBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        goAlarm();

        //editWater = (EditText) findViewById(R.id.editWater);
        //Long water = Long.valueOf(editWater.getText().toString().trim());

        setWaterButton = (Button) findViewById(R.id.setWaterButton);
        setWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AlarmActivity.this, "Reminder Set!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(AlarmActivity.this, MyNotification.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, intent, PendingIntent.FLAG_MUTABLE);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(), 10000, pendingIntent);

            }
        });

        deleteWaterButton = (Button) findViewById(R.id.deleteWaterButton);
        deleteWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AlarmActivity.this, "Reminder Delete!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AlarmActivity.this, MyNotification.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, intent, PendingIntent.FLAG_MUTABLE);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
            }
        });
    }

    private void goAlarm(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence charSequence = "WaterReminderChannel";
            String description = "Channel for Water Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyWater", charSequence, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }
}
