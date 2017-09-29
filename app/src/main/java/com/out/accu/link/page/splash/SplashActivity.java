package com.out.accu.link.page.splash;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.out.accu.link.router.Navigation;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 隐藏虚拟键
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        Navigation.login(this);
        finish();
    }
}
