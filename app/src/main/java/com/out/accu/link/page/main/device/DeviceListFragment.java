package com.out.accu.link.page.main.device;

import android.app.Activity;

import com.cyou17173.android.arch.base.page.SmartListFragment;
import com.out.accu.link.R;
import com.out.accu.link.data.mode.Device;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <P>Company: 17173</p>
 *
 * @author
 * @version 2017-09-29
 */
public class DeviceListFragment extends SmartListFragment<DeviceListContract.Presenter> implements DeviceListContract.View {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity.setTitle(getContext().getString(R.string.menu_device));
    }

    /**
     * 为Adapter注册Provider
     */
    @Override
    protected void registerProvider(MultiTypeAdapter adapter) {
         adapter.register(Device.class, new DeviceListViewBinder());
    }

    /**
     * 创建对应的Presenter, 通常需要传递两个对象（视图和数据服务）
     */
    @Override
    public DeviceListContract.Presenter createPresenter() {
        return new DeviceListPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
}