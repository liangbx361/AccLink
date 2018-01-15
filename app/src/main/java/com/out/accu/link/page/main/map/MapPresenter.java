package com.out.accu.link.page.main.map;

import android.widget.Toast;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.out.accu.link.R;
import com.out.accu.link.data.BusAction;
import com.out.accu.link.data.DataManager;
import com.out.accu.link.data.mode.Device;
import com.out.accu.link.data.mode.RespResult;
import com.out.accu.link.data.util.ByteUtil;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <P>Company: 17173</p>
 *
 * @author
 * @version 2017-09-29
 */
class MapPresenter implements MapContract.Presenter {

    private MapContract.View mView;
    private Device mDevice = new Device();

    MapPresenter(MapContract.View view) {
        mView = view;
    }

    /**
     * 开始执行业务逻辑，如加载缓存、请求网络数据
     */
    @Override
    public void start() {

    }

    @Override
    public void setLocation(byte[] deviceId, double lat, double lng) {
        mView.showLoadingDialog();
        mDevice.id = ByteUtil.getIdString(deviceId);
        mDevice.idBytes = deviceId;
        mDevice.lat = lat;
        mDevice.lng = lng;
        DataManager.getInstance().getDataService().setGps(deviceId, lat, lng);
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_SET_LOCATION)
            }
    )
    public void onSetLocation(RespResult respResult) {
        mView.hideLoadingDialog();
        if(respResult.isSuccess) {
            Device device = DataManager.getInstance().getModeData().getDevice(respResult.deviceId);
            device.lat = mDevice.lat;
            device.lng = mDevice.lng;
            mView.refreshLocation();
            Toast.makeText(mView.getActivity(), R.string.modify_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mView.getActivity(), R.string.modify_fail, Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.UPDATE_DEVICE_VALUE),
                    @Tag(BusAction.UPDATE_DEVICE_DATA)

            }
    )
    public void updateDeviceData(Object o) {
        if(mView.isVisible()) {
            mView.refreshLocation();
        }
    }
}
