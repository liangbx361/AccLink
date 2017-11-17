package com.out.accu.link.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.Explode;

import com.out.accu.link.page.login.LoginActivity;
import com.out.accu.link.page.main.MainActivity;
import com.out.accu.link.page.main.device.detail.DeviceDetailActivity;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/9/29
 */

public class Navigation {

    public static void login(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void main(Activity activity) {
        Explode explode = new Explode();
        explode.setDuration(500);

        activity.getWindow().setExitTransition(explode);
        activity.getWindow().setEnterTransition(explode);
        ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(activity);
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent, option.toBundle());
        activity.finish();
    }

    public static void deviceDetail(Activity activity, String deviceId) {
        Intent intent = new Intent(activity, DeviceDetailActivity.class);
        intent.putExtra("deviceId", deviceId);
        activity.startActivity(intent);
    }
}
