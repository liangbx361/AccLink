package com.out.accu.link.page.main;

import android.support.annotation.StringDef;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.out.accu.link.R;
import com.out.accu.link.data.BusAction;
import com.out.accu.link.data.DataManager;
import com.out.accu.link.page.main.device.DeviceListFragment;
import com.out.accu.link.page.main.history.HistoryFragment;
import com.out.accu.link.page.main.map.MapFragment;
import com.out.accu.link.page.main.settings.SettingsFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/9/29
 */

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        DataManager.getInstance().getDataService().getUser();
    }

    @Override
    public void onItemSelected(int itemId) {

        switch (itemId) {
            case R.id.nav_device:
                if(mView.isCachedFragment(MAIN_TAB.DEVICE)) {
                    mView.showCacheFragment(MAIN_TAB.DEVICE);
                } else {
                    mView.showFragmentAndCache(MAIN_TAB.DEVICE, new DeviceListFragment());
                }
                break;

            case R.id.nav_map:
                if(mView.isCachedFragment(MAIN_TAB.MAP)) {
                    mView.showCacheFragment(MAIN_TAB.MAP);
                } else {
                    mView.showFragmentAndCache(MAIN_TAB.MAP, new MapFragment());
                }
                break;

            case R.id.nav_history:
                if(mView.isCachedFragment(MAIN_TAB.GRAPH)) {
                    mView.showCacheFragment(MAIN_TAB.GRAPH);
                } else {
                    mView.showFragmentAndCache(MAIN_TAB.GRAPH, new HistoryFragment());
                }
                break;

            case R.id.nav_settings:
                if(mView.isCachedFragment(MAIN_TAB.SETTINGS)) {
                    mView.showCacheFragment(MAIN_TAB.SETTINGS);
                } else {
                    mView.showFragmentAndCache(MAIN_TAB.SETTINGS, new SettingsFragment());
                }
                break;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({MAIN_TAB.DEVICE, MAIN_TAB.MAP, MAIN_TAB.GRAPH, MAIN_TAB.SETTINGS})
    @interface MAIN_TAB {
        String DEVICE = "device";
        String MAP = "map";
        String GRAPH = "graph";
        String SETTINGS = "settings";
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_GET_USERNAME)
            }
    )
    public void setUserName(Object o) {
        mView.showUsername(DataManager.getInstance().getModeData().user.username);
    }
}
