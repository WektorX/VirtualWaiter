package com.example.virtualwaiter.UI.Actions;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.virtualwaiter.R;

public class PushNofitication {


    public static void notify(Context context, String title, String body, int id){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel channel = new NotificationChannel(
                        "ch_0",
                        "ch_0",
                        NotificationManager.IMPORTANCE_HIGH);

                channel.setDescription("Channel for notifying the customer about a new order!");
                channel.enableLights(true);
                channel.enableVibration(true);
                channel.setShowBadge(true);

                NotificationManager manager = context.getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);
            }

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context, "ch_0")
                            .setSmallIcon(R.drawable.icon)
                            .setContentTitle(title)
                            .setContentText(body)
                            .setAutoCancel(false)
                            .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND | Notification.FLAG_SHOW_LIGHTS)
                            .setPriority(NotificationCompat.PRIORITY_HIGH);

            NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(context);
            mNotificationMgr.notify(id, mBuilder.build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
