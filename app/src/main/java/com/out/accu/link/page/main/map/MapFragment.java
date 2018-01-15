package com.out.accu.link.page.main.map;

import android.app.Activity;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.cyou17173.android.arch.base.page.SmartFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.out.accu.link.R;
import com.out.accu.link.data.DataManager;
import com.out.accu.link.data.mode.Device;
import com.out.accu.link.data.mode.ModeData;
import com.out.accu.link.router.Navigation;
import com.out.accu.link.util.DeviceUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.List;

/**
 * <p>Title: 显示标注设备 --> </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <P>Company: 17173</p>
 *
 * @author
 * @version 2017-09-29
 */
public class MapFragment extends SmartFragment<MapContract.Presenter> implements MapContract.View,
        OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private static final LatLng lat1 = new LatLng(25.300942, 121.574210);
    private static final LatLng lat2 = new LatLng(25.008826, 122.004050);
    private static final LatLng lat3 = new LatLng(24.532498, 121.873587);
    private static final LatLng lat4 = new LatLng(21.894630, 120.851350);
    private static final LatLng lat5 = new LatLng(23.782829, 120.123506);

    private GoogleMap mMap;

    QMUITipDialog mLoadingDialog;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity.setTitle(getContext().getString(R.string.menu_map));
    }

    /**
     * 设置布局ID
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_map;
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mLoadingDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(getString(R.string.modifying))
                .create();
        mLoadingDialog.setCancelable(true);
    }

    /**
     * 注册事件，如事件监听、广播接收等
     */
    @Override
    public void registerEvent() {

    }

    /**
     * 注销事件
     */
    @Override
    public void unregisterEvent() {

    }

    /**
     * 创建对应的Presenter, 通常需要传递两个对象（视图和数据服务）
     */
    @Override
    public MapContract.Presenter createPresenter() {
        return new MapPresenter(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        map.setOnMapLongClickListener(this);
        map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                return true;
            }
        });

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                ModeData modeData = DataManager.getInstance().getModeData();
                for(Device device : modeData.getDevices()) {
                    if(marker.getTitle().contains(device.id)) {
                        Navigation.deviceDetail((Activity) getContext(), device.id);
                    }
                }
            }
        });

        // 显示的默认范围
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(lat1)
                .include(lat2)
                .include(lat3)
                .include(lat4)
                .include(lat5)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));

        refreshLocation();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        // 切换经纬度
        setLocation(latLng.latitude, latLng.longitude);
    }

    public void setLocation(double lat, double lng) {
        List<Device> devices = DataManager.getInstance().getModeData().getDevices();
        String[] deviceIds = new String[devices.size()];
        for(int i=0; i<deviceIds.length; i++) {
            deviceIds[i] = DeviceUtil.getDeviceName(devices.get(i));
        }
        final QMUIDialog.CheckableDialogBuilder builder = new QMUIDialog.CheckableDialogBuilder(getActivity());
        builder.setTitle(getString(R.string.set_device_location))
                .addItems(deviceIds, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPresenter().setLocation(devices.get(which).idBytes, lat, lng);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void showLoadingDialog() {
        mLoadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void refreshLocation() {
        mMap.clear();
        ModeData modeData = DataManager.getInstance().getModeData();
        // 标注设备位置
        if(modeData.getDevices() != null && modeData.getDevices().size() > 0) {
            for(Device device : modeData.getDevices()) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(device.lat, device.lng));
                markerOptions.title(DeviceUtil.getDeviceName(device));
                if(device.isOnline) {
                    if(device.isLowLowAlarm()) {
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_low_low_alarm));
                    } else if(device.isLowAlarm()) {
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_low_alarm));
                    } else {
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_online));
                    }
                } else {
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_offline));
                }
                String snippet = DeviceUtil.getDeviceDesc(device);
                if(!TextUtils.isEmpty(snippet)) {
                    markerOptions.snippet(snippet);
                }
                mMap.addMarker(markerOptions);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mMap != null) {
            refreshLocation();
        }
    }
}