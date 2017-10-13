package com.out.accu.link.page.main.device;

import com.cyou17173.android.arch.base.mvp.SmartListView;
import com.cyou17173.android.arch.base.page.SmartListPresenterImpl;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <P>Company: 17173</p>
 *
 * @author
 * @version 2017-09-29
 */
class DeviceListPresenter extends SmartListPresenterImpl implements DeviceListContract.Presenter {

    private DeviceListContract.View mView;

    DeviceListPresenter(DeviceListContract.View view) {
        mView = view;
    }

    @Override
    public void loadCache() {

    }

    @Override
    protected void loadRemote() {

    }

    @Override
    protected SmartListView getView() {
        return mView;
    }
}
