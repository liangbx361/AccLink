package com.out.accu.link.page.login;

import android.widget.Toast;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.out.accu.link.BuildConfig;
import com.out.accu.link.R;
import com.out.accu.link.data.BusAction;
import com.out.accu.link.data.DataManager;
import com.out.accu.link.data.mode.Login;
import com.out.accu.link.data.mode.LoginInfo;
import com.out.accu.link.data.mode.ResponseCmd;
import com.out.accu.link.router.Navigation;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <P>Company: 17173</p>
 *
 * @author
 * @version 2017-09-29
 */
class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private LoginInfo mLoginInfo;

    LoginPresenter(LoginContract.View view) {
        mView = view;
    }

    /**
     * 开始执行业务逻辑，如加载缓存、请求网络数据
     */
    @Override
    public void start() {
        if(!BuildConfig.DEBUG) {
            LoginInfo loginInfo = DataManager.getInstance().getDataService().getLoginInfo();
            mView.showLoginInfo(loginInfo);
        } else {
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.user = "user1";
            loginInfo.password = "123456";
            loginInfo.server = "59.124.45.214";
            loginInfo.port = 60003;
            mView.showLoginInfo(loginInfo);
        }
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_LOGIN)
            }
    )
    public void onLogin(Login login) {
        mView.getProgress().dismiss();
        if (login.isSuccess) {
            Navigation.main(mView.getActivity());
            DataManager.getInstance().getDataService().saveLoginInfo(mLoginInfo);
        } else {
            Toast.makeText(mView.getActivity(), R.string.login_fail, Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_LOGIN_FAIL)
            }
    )
    public void onLoginFail(ResponseCmd response) {
        mView.getProgress().dismiss();
        Toast.makeText(mView.getActivity(), R.string.login_fail, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void login(LoginInfo loginInfo) {
        mView.getProgress().show();
        DataManager.getInstance().getDataService().login(loginInfo);
        mLoginInfo = loginInfo;
    }
}
