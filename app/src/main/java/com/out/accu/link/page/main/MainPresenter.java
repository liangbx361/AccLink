package com.out.accu.link.page.main;

import android.support.annotation.StringDef;

import com.out.accu.link.R;
import com.out.accu.link.page.main.map.MapFragment;

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

    }

    @Override
    public void onItemSelected(int itemId) {

        switch (itemId) {
            case R.id.nav_device:
                if(mView.isCachedFragment(MAIN_TAB.DEVICE)) {
                    mView.showCacheFragment(MAIN_TAB.DEVICE);
                } else {
                    mView.showFragmentAndCache(MAIN_TAB.DEVICE, new MapFragment());
                }
                break;

            case R.id.nav_map:

                break;

            case R.id.nav_graph:
                break;

            case R.id.nav_settings:
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
}
