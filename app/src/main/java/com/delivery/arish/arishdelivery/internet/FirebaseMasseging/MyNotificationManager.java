package com.delivery.arish.arishdelivery.internet.FirebaseMasseging;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;


import com.delivery.arish.arishdelivery.R;

import java.util.Objects;


class MyNotificationManager {
    private static final int ID_SMALL_NOTIFICATION = 235;
    private final Context mCtx;
    private static final String NOTIFICATION_CHANNEL_ID="default";

    public MyNotificationManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    //the method will show a small notification
    //parameters are title for message title, message for message text and an intent that will open
    //when you will tap on the notification
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void showSmallNotification (String title, String messageBody, Intent intent) {
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mCtx,
                        ID_SMALL_NOTIFICATION,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );


        NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);

        AudioAttributes att = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();
        Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + mCtx.getPackageName() + "/raw/sorted");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Notification.Builder notificationBuilder =
                    new Notification.Builder(mCtx, NOTIFICATION_CHANNEL_ID)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(title)
                            .setContentText(messageBody)
                            .setAutoCancel(true)
                            //.setPriority(Notification.PRIORITY_MAX) // this is deprecated in API 26 but you can still use for below 26. check below update for 26 API
                            //.setSound(alarmSound)
                            .setContentIntent(resultPendingIntent);

            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel.
            notificationChannel.setSound(alarmSound, att);
            notificationChannel.setDescription(messageBody);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            Objects.requireNonNull(notificationManager).createNotificationChannel(notificationChannel);
           /* if (imageThumbnail != null) {
                notificationBuilder.setStyle(new Notification.BigPictureStyle()
                        .bigPicture(imageThumbnail).setSummaryText(messageBody));
            }*/
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        } else {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(mCtx, NOTIFICATION_CHANNEL_ID);

             //icon appears in device notification bar and right hand corner of notification

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.setSmallIcon(R.drawable.ic_face);
                builder.setColor(mCtx.getResources().getColor(R.color.color_notification));
            } else {
                builder.setSmallIcon(R.drawable.ic_face);
            }

            Bitmap icon = BitmapFactory.decodeResource(mCtx.getResources(),
                    R.mipmap.ic_launcher);
            builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(icon));
            Intent i = new Intent();
            PendingIntent pIntent = PendingIntent.getActivity(mCtx, (int) System.currentTimeMillis(), i, 0);

            builder.addAction(R.mipmap.ic_launcher,mCtx.getResources().getString(R.string.ok),pIntent);
            // This intent is fired when notification is clicked
           /* Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/"));
            PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, intent, 0);
*/
            // Set the intent that will fire when the user taps the notification.
            builder.setContentIntent(resultPendingIntent);

            // Large icon appears on the left of the notification
            builder.setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.ic_launcher));

            // Content title, which appears in large type at the top of the notification
            builder.setContentTitle(title);

            // Content text, which appears in smaller text below the title
            builder.setContentText(messageBody);

            builder.setSound(alarmSound);
            // The subtext, which appears under the text on newer devices.
            // This will show-up in the devices with Android 4.2 and above only
            builder.setSubText(mCtx.getResources().getString(R.string.notification_sub_text));


            // Will display the notification in the notification bar
            Objects.requireNonNull(notificationManager).notify(ID_SMALL_NOTIFICATION, builder.build());

        }

    }
}
