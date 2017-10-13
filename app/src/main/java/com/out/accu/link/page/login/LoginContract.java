package com.out.accu.link.page.login;

import com.cyou17173.android.arch.base.mvp.SmartPresenter;
import com.cyou17173.android.arch.base.mvp.SmartView;

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

    }

    interface Presenter extends SmartPresenter {

        void login(String username, String password);
    }
}