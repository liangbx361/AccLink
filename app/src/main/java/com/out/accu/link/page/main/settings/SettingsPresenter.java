package com.out.accu.link.page.main.settings;

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
    private User mUser;

    SettingsPresenter(SettingsContract.View view, DataService service) {
        mView = view;
        mService = service;
    }

    /**
     * 开始执行业务逻辑，如加载缓存、请求网络数据
     */
    @Override
    public void start() {

    }

    /**
     * 重新加载
     */
    @Override
    public void retryLoad() {

    }

    @Override
    public User getUser() {
        return mUser;
    }

    @Override
    public void modifyUsername(String username) {
//        mService.setUsername(username)
//                .compose(SmartTransformer.applySchedulers())
//                .compose(mView.bindUntilDestroy())
//                .subscribe(aBoolean -> {
//                    mUser.username = username;
//                    mView.showUser(mUser);
//                    Toast.makeText(mView.getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
//                }, throwable -> {
//                    Toast.makeText(mView.getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
//                });
    }

    @Override
    public void modifyMobile(String mobile) {
//        mService.setMobile(mobile)
//                .compose(SmartTransformer.applySchedulers())
//                .compose(mView.bindUntilDestroy())
//                .subscribe(aBoolean -> {
//                    mUser.mobile = mobile;
//                    mView.showUser(mUser);
//                    Toast.makeText(mView.getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
//                }, throwable -> {
//                    Toast.makeText(mView.getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
//                });
    }

    @Override
    public void modifyPassword(String oldPwd, String newPwd) {
//        mService.setPassword(oldPwd, newPwd)
//                .compose(SmartTransformer.applySchedulers())
//                .compose(mView.bindUntilDestroy())
//                .subscribe(aBoolean -> {
//                    Toast.makeText(mView.getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
//                }, throwable -> {
//                    Toast.makeText(mView.getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
//                });
    }
}
