package com.out.accu.link;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.cyou17173.android.arch.base.app.SmartApplication;
import com.cyou17173.android.arch.base.app.SmartConfig;
import com.cyou17173.android.arch.base.event.SmartActivityLifecycle;
import com.cyou17173.android.arch.smart.toolbar.SmartToolbar;
import com.cyou17173.android.arch.smart.toolbar.ToolbarDelegate;
import com.cyou17173.android.component.swipe.view.GlobalSwipeView;
import com.cyou17173.android.component.swipe.view.SwipeLayout;
import com.cyou17173.android.component.swipe.view.footer.LoadFinishDisplayMode;
import com.out.accu.link.data.DataManager;
import com.out.accu.link.data.config.Platform;
import com.out.accu.link.data.logger.AppLogger;
import com.tencent.bugly.Bugly;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/9/29
 */

public class App extends SmartApplication {

    @Override
    protected void initConfig(SmartConfig smartConfig) {

    }

    @Override
    protected void asyncInit() {
        AppLogger.get().setDebug(BuildConfig.DEBUG);
        DataManager.init(Platform.TEST);

        // SwipeView全局初始化
        GlobalSwipeView.getInstance().setRefreshLayoutId(R.layout.sv_google_refresh);
        GlobalSwipeView.getInstance().setLoadMoreLayoutId(R.layout.sv_state_footer);
        GlobalSwipeView.getInstance().setHeaderStyle(SwipeLayout.STYLE.ABOVE);
        GlobalSwipeView.getInstance().setFooterStyle(SwipeLayout.STYLE.FOOTER_OUT);
        GlobalSwipeView.getInstance().setAutoLoadMore(true);
        GlobalSwipeView.getInstance().setLoadMoreEnable(false);
        GlobalSwipeView.getInstance().setLoadFinishDisplayMode(LoadFinishDisplayMode.ONLY_SCROLLABLE);

        registerSmartActivityLifecycleListener(new SmartActivityLifecycle() {

            @Override
            public void afterSetContentView(Activity activity, Bundle savedInstanceState) {
                if(activity instanceof SmartToolbar) {
                    Toolbar toolbar = (Toolbar) activity.findViewById(R.id.smart_toolbar);
                    SmartToolbar smartToolbar = (SmartToolbar) activity;
                    ToolbarDelegate toolbarDelegate = new ToolbarDelegate(toolbar);
                    // 导航栏控制
                    toolbarDelegate.showNavigation(activity, smartToolbar.showNavigation());
                    // 标题显示
                    toolbarDelegate.setTitle(activity.getTitle().toString());
                }
            }
        });
    }

    @Override
    protected void syncInit() {
        Bugly.init(getApplicationContext(), "b08dbb8909", BuildConfig.DEBUG);
    }
}
