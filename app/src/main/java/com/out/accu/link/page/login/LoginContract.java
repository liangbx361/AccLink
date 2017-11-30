package com.out.accu.link.page.login;

import android.app.ProgressDialog;

import com.cyou17173.android.arch.base.mvp.SmartPresenter;
import com.cyou17173.android.arch.base.mvp.SmartView;
import com.out.accu.link.data.mode.LoginInfo;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <P>Company: 17173</p>
 *
 * @author
 * @version 2017-09-29
 */
public interface LoginContract {

    interface View extends SmartView {

        ProgressDialog getProgress();

        void showLoginInfo(LoginInfo loginInfo);

        LoginInfo getLoginInfo();
    }

    interface Presenter extends SmartPresenter {

        void login(LoginInfo loginInfo);
    }
}