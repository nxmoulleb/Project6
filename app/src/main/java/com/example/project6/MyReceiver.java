package com.example.project6;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    private final static String CHANNEL_ID = "0";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // throw new UnsupportedOperationException("Not yet implemented");
        String name = intent.getStringExtra("NAME");
        String address = intent.getStringExtra("ADDRESS");
        String desc = intent.getStringExtra("DESC");
        double rating = Double.parseDouble(intent.getStringExtra("RATING"));

        notif(context, name, address + ", " + desc + ", " + rating + "/10");
    }

    private void notif(Context context, String name, String content) {
        if(name != null && !name.isEmpty()) {
            Intent intent = new Intent(context, MainActivity.class);
            // use System.currentTimeMillis() to have a unique ID for the pending intent
            PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_IMMUTABLE);

            // build notification
            // the addAction re-use the same intent to keep the example short
            Notification n  = new Notification.Builder(context)
                    .setContentTitle("Reminder for " + name)
                    .setContentText(content)
                    .setSmallIcon(R.drawable.d20)
                    .setContentIntent(pIntent)
                    .setAutoCancel(false).build();


            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(0, n);
        }
    }
}