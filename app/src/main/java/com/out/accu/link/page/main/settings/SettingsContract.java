package com.out.accu.link.page.main.settings;

import com.cyou17173.android.arch.base.mvp.SmartStatePresenter;
import com.cyou17173.android.arch.base.mvp.SmartStateView;
import com.out.accu.link.data.mode.User;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <P>Company: 17173</p>
 *
 * @author
 * @version 2017-11-09
 */
public interface SettingsContract {

    interface View extends SmartStateView {
        void showUser(User user);
        void showLoadingDialog();
        void hideLoadingDialog();
    }

    interface Presenter extends SmartStatePresenter {
        User getUser();
        void modifyUsername(String username);
        void modifyMobile(String mobile);
        void modifyPassword(String oldPwd, String newPwd);
    }
}