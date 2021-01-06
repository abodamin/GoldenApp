package com.abdullah.goldenapp.models;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationUtil {
    public static String createNotificationChannel(
            Context context) {

        // NotificationChannels are required for Notifications on O (API 26) and above.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // The id of the channel.
            String channelId = Globals.CHANNEL_ID;

            // The user-visible name of the channel.
            CharSequence channelName = Globals.ChannelName;
            // The user-visible description of the channel.
            String channelDescription = Globals.ChannelDescription;
            int channelImportance = NotificationManager.IMPORTANCE_HIGH;
            int channelLockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC;

            // Initializes NotificationChannel.
            NotificationChannel notificationChannel = null;
            notificationChannel = new NotificationChannel(channelId, channelName, channelImportance);

            notificationChannel.setDescription(channelDescription);
            notificationChannel.enableVibration(true);
            notificationChannel.setLockscreenVisibility(channelLockscreenVisibility);

            // Adds NotificationChannel to system. Attempting to create an existing notification
            // channel with its original values performs no operation, so it's safe to perform the
            // below sequence.
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

            return channelId;
        } else {
            // Returns null for pre-O (26) devices.
            return null;
        }
    }
}