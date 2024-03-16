package com.example.vaccinationremindersystem;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vaccinationremindersystem.databinding.ActivityNotificationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class NotificationActivity extends AppCompatActivity {
    ActivityNotificationBinding binding;
    String title, message;
    Long time;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        String data = getIntent().getStringExtra("message");
        binding.actualNotification.setText(data);
//        long currentTimeInMillis = System.currentTimeMillis();
        createChannel();
//        binding.allowNotification.setOnClickListener(view -> {
//            scheduleNotification();
//            startActivity(new Intent(NotificationActivity.this , HomeActivity.class));
//            finish();
//        });

        FirebaseDatabase.getInstance().getReference().child(
                Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()
        ).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                title = snapshot.child("title").getValue(String.class);
                message = snapshot.child("message").getValue(String.class);
                time = snapshot.child("time").getValue(Long.class);

                long trigger = System.currentTimeMillis();
                scheduleNotification(trigger + 60000);


//                for (int i = 0; i < 4; i++) {
//                    trigger = trigger + 120000;
//                    scheduleNotification(trigger);
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void scheduleMultipleNotifications(long time) {
//        long[] intervals = {0 , 60 * 1000 , 120 * 1000};
//        for (long l : intervals) {
//            long notificationTime = time + l;
//            scheduleNotification(notificationTime);
//        }
//    }
    @SuppressLint("ScheduleExactAlarm")
    private void scheduleNotification(long trigger) {
        Intent intent = new Intent(NotificationActivity.this, Notification.class);
        intent.putExtra("titleExtra", title);
        intent.putExtra("messageExtra", message);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                NotificationActivity.this,
                1,
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

//        long trigger = System.currentTimeMillis() + 6000;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                trigger,
                60 * 1000,
                pendingIntent
        );

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        String name = "Vaccination";
        String desc = "Channel description";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("notificationChannel", name, importance);
        channel.setDescription(desc);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }
}
