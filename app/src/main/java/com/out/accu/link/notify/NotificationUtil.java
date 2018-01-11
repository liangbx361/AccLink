package com.out.accu.link.notify;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.v7.app.NotificationCompat;

/**
 * <p>Title: 报警通知栏默认实现</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <P>Company: 17173</p>
 *
 * @author liangbx
 * @version 2017/3/9
 */

public class NotificationUtil {

    public static void notify(
            Context context,
            @DrawableRes int iconId,
            String name,
            String content,
            int percent,
            Class clazz,
            String downloadUrl) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(iconId);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), iconId));
        builder.setContentTitle(name);
        builder.setContentText(content);
        builder.setProgress(100, percent, false);
        builder.setAutoCancel(true);
        builder.setOngoing(false);
        builder.setWhen(System.currentTimeMillis());

        Intent resultIntent = new Intent(context, clazz);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        notificationManager.notify(downloadUrl.hashCode(), builder.build());
    }

    public static void remove(Context context, String downloadUrl) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(downloadUrl.hashCode());
    }
}
