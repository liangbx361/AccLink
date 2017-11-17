package com.out.accu.link.page.main.device;

import com.cyou17173.android.arch.base.mvp.SmartListView;
import com.cyou17173.android.arch.base.page.SmartListPresenterImpl;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.out.accu.link.data.BusAction;
import com.out.accu.link.data.DataManager;
import com.out.accu.link.data.mode.Device;
import com.out.accu.link.data.mode.ModeData;

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
        DataManager.getInstance().getDataService().getDevices();
    }

    @Override
    protected SmartListView getView() {
        return mView;
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_DEVICES)
            }
    )
    public void onDevices(ModeData modeData) {
        onRemoteLoadSuccess(modeData.devices, true);

        for(Device device : modeData.devices) {
            if(device.isOnline) {
                DataManager.getInstance().getDataService().getDevice(device);
            }
        }
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.ONLINE_DEVICE)
            }
    )
    public void onlineDevice(Device device) {
        onRemoteLoadSuccess(DataManager.getInstance().getModeData().devices, true);
    }
}
