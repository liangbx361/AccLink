package com.out.accu.link.page.login;

import android.widget.Toast;

import com.cyou17173.android.arch.base.page.SmartTransformer;
import com.out.accu.link.data.DataManager;
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

    LoginPresenter(LoginContract.View view) {
        mView = view;
    }

    /**
     * 开始执行业务逻辑，如加载缓存、请求网络数据
     */
    @Override
    public void start() {

    }

    @Override
    public void login(String username, String password) {
        DataManager.getInstance().getDataService()
                .login(username, password)
                .compose(SmartTransformer.applySchedulers())
                .compose(mView.bindUntilDestroy())
                .subscribe(aBoolean -> {
                    Navigation.main(mView.getActivity());
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(mView.getActivity(), "登录失败", Toast.LENGTH_SHORT).show();
                });
    }
}
