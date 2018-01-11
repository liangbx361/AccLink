package com.out.accu.link.notify;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.cyou17173.android.arch.base.app.Smart;
import com.out.accu.link.R;
import com.out.accu.link.data.DataManager;
import com.out.accu.link.data.mode.Device;
import com.out.accu.link.data.mode.ModeData;
import com.out.accu.link.page.main.MainActivity;
import com.out.accu.link.util.SmsUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: <／p>
 * <p>Description: 每台设备报警一次<／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2018/1/11
 */

public class NotifyManager {

    public static final int NOTIFY_NULL = 0;
    public static final int NOTIFY_NORMAL = 1;
    public static final int NOTIFY_LOW_ALARM = 2;
    public static final int NOTIFY_LOW_LOW_ALARM = 3;

    private static volatile NotifyManager sNotifyManager;

    private Map<String, Integer> mNotifyStates;

    public static NotifyManager getInstance() {

        if(sNotifyManager == null) {
            synchronized (NotifyManager.class) {
                if(sNotifyManager == null) {
                    sNotifyManager = new NotifyManager();
                }
            }
        }

        return sNotifyManager;
    }

    private NotifyManager() {
        mNotifyStates = new HashMap<>();
    }

    public void handlerNotify(Activity activity, String deviceId) {
        ModeData modeData = DataManager.getInstance().getModeData();
        Device device = modeData.getDevice(deviceId);

        if(device.sampleValue == -1) {
            return;
        }

        if(!mNotifyStates.containsKey(deviceId)) {
            mNotifyStates.put(deviceId, NOTIFY_NULL);
        }

        int notifyState = mNotifyStates.get(deviceId);

        if(device.isLowLowAlarm()) {
            // 低低报通知
            switch (notifyState) {
                case NOTIFY_LOW_LOW_ALARM:
                    // 无变化，不处理
                    break;

                case NOTIFY_LOW_ALARM:
                case NOTIFY_NORMAL:
                case NOTIFY_NULL:
                    // 通知
                    String smsContent = SmsUtil.getSms(device);
                    String title = deviceId + "(" +
                            Smart.getApp().getResources().getString(R.string.notify_low_low_alarm) + ")";
                    int iconRes = R.mipmap.low_low_alarm;
                    show(activity, deviceId, iconRes, title, smsContent);

                    // 更新状态
                    mNotifyStates.put(deviceId, NOTIFY_LOW_LOW_ALARM);
                    break;
            }
        } else if(device.isLowAlarm()) {
            //低报通知
            switch (notifyState) {
                case NOTIFY_LOW_ALARM:
                    // 无变化，不处理
                    break;

                case NOTIFY_LOW_LOW_ALARM:
                case NOTIFY_NORMAL:
                case NOTIFY_NULL:
                    // 通知
                    String smsContent = SmsUtil.getSms(device);
                    String title = deviceId + "(" +
                            Smart.getApp().getResources().getString(R.string.notify_low_alarm) + ")";
                    int iconRes = R.mipmap.low_alarm;
                    show(activity, deviceId, iconRes, title, smsContent);

                    // 更新状态
                    mNotifyStates.put(deviceId, NOTIFY_LOW_ALARM);
                    break;
            }

        } else {
            //正常
            switch (notifyState) {
                case NOTIFY_LOW_ALARM:
                case NOTIFY_LOW_LOW_ALARM:
                    // 通知
                    String smsContent = "";
                    String title = deviceId + "(" +
                            Smart.getApp().getResources().getString(R.string.notify_normal) + ")";
                    int iconRes = R.mipmap.smile;
                    show(activity, deviceId, iconRes, title, smsContent);

                    // 通知恢复正常
                    mNotifyStates.put(deviceId, NOTIFY_NORMAL);
                    break;

                case NOTIFY_NULL:
                    // 更新状态
                    mNotifyStates.put(deviceId, NOTIFY_NORMAL);
                    break;

                case NOTIFY_NORMAL:
                    // 正常状态不处理
                    break;
            }
        }
    }

    public static void show(
            Context context,
            String deviceId,
            int iconId,
            String title,
            String content) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "acculink");
        builder.setSmallIcon(iconId);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), iconId));
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setAutoCancel(true);
        builder.setOngoing(false);
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);

        Intent resultIntent = new Intent(context, MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        notificationManager.notify(deviceId.hashCode(), builder.build());
    }

}
