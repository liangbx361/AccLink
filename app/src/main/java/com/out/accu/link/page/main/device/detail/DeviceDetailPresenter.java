package com.out.accu.link.page.main.device.detail;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <P>Company: 17173</p>
 *
 * @author
 * @version 2017-10-13
 */
class DeviceDetailPresenter implements DeviceDetailContract.Presenter {

    private DeviceDetailContract.View mView;

    DeviceDetailPresenter(DeviceDetailContract.View view) {
        mView = view;
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
}
