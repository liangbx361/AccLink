package com.out.accu.link.page.main.device;

import com.cyou17173.android.arch.base.mvp.SmartListView;
import com.cyou17173.android.arch.base.page.SmartListPresenterImpl;
import com.cyou17173.android.arch.base.page.SmartTransformer;
import com.out.accu.link.data.DataManager;
import com.out.accu.link.data.mode.Device;
import com.out.accu.link.util.DeviceHelper;

import java.util.List;

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
    private List<Device> mDevices;

    DeviceListPresenter(DeviceListContract.View view) {
        mView = view;
    }

    @Override
    public void loadCache() {
        onCacheLoadFail();
    }

    @Override
    protected void loadRemote() {
        DataManager.getInstance().getDataService().getDevices()
                .compose(SmartTransformer.applySchedulers())
                .subscribe(devices -> {
                    onRemoteLoadSuccess(devices, true);
                    DeviceHelper.saveDevices(devices);
                }, throwable -> onRemoteLoadFail());
    }

    @Override
    protected SmartListView getView() {
        return mView;
    }
}
