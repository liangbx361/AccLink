package com.out.accu.link.page.main.settings;

import android.widget.Toast;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.out.accu.link.R;
import com.out.accu.link.data.BusAction;
import com.out.accu.link.data.DataManager;
import com.out.accu.link.data.DataService;
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
class SettingsPresenter implements SettingsContract.Presenter {

    private SettingsContract.View mView;
    private DataService mService;
    private User mTmpUser = new User();

    SettingsPresenter(SettingsContract.View view, DataService service) {
        mView = view;
        mService = service;
    }

    /**
     * 开始执行业务逻辑，如加载缓存、请求网络数据
     */
    @Override
    public void start() {
        mView.showUser(getUser());
    }

    /**
     * 重新加载
     */
    @Override
    public void retryLoad() {

    }

    @Override
    public User getUser() {
        return DataManager.getInstance().getModeData().user;
    }

    @Override
    public void modifyUsername(String username) {
        mView.showLoadingDialog();
        mTmpUser.username = username;
        mService.setUsername(username);
    }

    @Override
    public void modifyMobile(String mobile) {
        mView.showLoadingDialog();
        mTmpUser.mobile = mobile;
        mService.setMobile(mobile);
    }

    @Override
    public void modifyPassword(String oldPwd, String newPwd) {
        mView.showLoadingDialog();
        mService.setPassword(oldPwd, newPwd);
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_GET_USERNAME),
                    @Tag(BusAction.RESP_GET_USER_MOBILE)
            }
    )
    public void updateDate(Object o) {
        mView.showUser(getUser());
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_SET_USERNAME_SUCCESS)
            }
    )
    public void onSetUserName(Object o) {
        mView.hideLoadingDialog();
        getUser().username = mTmpUser.username;
        mView.showUser(getUser());
        Toast.makeText(mView.getActivity(), R.string.modify_success, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_SET_USER_MOBILE_SUCCESS)
            }
    )
    public void onSetMobile(Object o) {
        mView.hideLoadingDialog();
        getUser().mobile = mTmpUser.mobile;
        mView.showUser(getUser());
        Toast.makeText(mView.getActivity(), R.string.modify_success, Toast.LENGTH_SHORT).show();
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_SET_PASSWORD_SUCCESS)
            }
    )
    public void onSetPassword(Object o) {
        mView.hideLoadingDialog();
        Toast.makeText(mView.getActivity(), R.string.modify_success, Toast.LENGTH_SHORT).show();
    }
}
