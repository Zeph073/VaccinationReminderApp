package com.example.vaccinationremindersystem;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class Notification extends BroadcastReceiver {
    int notificationID = 1;
    String channelID = "notificationChannel";

    String[] messages = {
            "Hello parent. Polio vaccination shall be offered to children below age 2 beginning from 12/06/2024. You are advised to bring your child",
            "Hello parent. Hepatitis B vaccination shall be offered to children between age 2 and 3 beginning from 12/09/2024. You are advised to bring your child",
            "Hello parent. BCG vaccination shall be offered to children between age 3 and 7 beginning from 12/10/2024. You are advised to bring your child",
            "Hello parent. Rota Virus vaccination shall be offered to children between age 3 and 7 beginning from 12/10/2024. You are advised to bring your child",
            "Hello parent. Pneumonia  vaccination shall be offered to children between age 3 and 7 beginning from 2/06/2024. You are advised to bring your child",
            "Hello parent. DTWPHibHepB vaccination shall be offered to children between age 3 and 7 beginning from 12/08/2024. You are advised to bring your child",
            "Hello parent. IPV vaccination shall be offered to children between age 3 and 7 beginning from 12/09/2024. You are advised to bring your child",
            "Hello parent. Yellow Fever vaccination shall be offered to children between age 3 and 7 beginning from 12/09/2024. You are advised to bring your child",
            "Hello parent. Measles vaccination shall be offered to children between age 3 and 7 beginning from 12/10/2024. You are advised to bring your child",
            "Hello parent. HPV  vaccination shall be offered to children between age 3 and 7 beginning from 12/11/2024. You are advised to bring your child"
    };
    private static int messageIndex = 0;

    @Override
    public void onReceive(Context context, Intent intent) {

        String message = getNextMessage();
        Intent clickIntent = new Intent(context, ViewNotification.class);
        clickIntent.putExtra("message", message);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                1,
                clickIntent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        android.app.Notification notification = new NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Vaccination Reminder")
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build();


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, notification);
    }

    private String getNextMessage() {
        String message = messages[messageIndex];
        messageIndex = (messageIndex + 1) % messages.length;
        return message;
    }


}
