package com.out.accu.link.page.main;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.cyou17173.android.arch.base.mvp.SmartPresenter;
import com.cyou17173.android.arch.base.mvp.SmartView;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/9/29
 */

public class MainContract {

    interface View extends SmartView {
        boolean isCachedFragment(@NonNull String tag);
        void showFragmentAndCache(@NonNull String tag, @NonNull Fragment fragment);
        void showCacheFragment(@NonNull String tag);
        void showUsername(String username);
        void showLoading();
        void hideLoading();
    }

    interface Presenter extends SmartPresenter {
        void onItemSelected(int itemId);
        void logout();
    }
}
