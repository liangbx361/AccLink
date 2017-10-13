package com.out.accu.link.page.main.device;

import com.cyou17173.android.arch.base.page.SmartListFragment;

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

    /**
     * 为Adapter注册Provider
     */
    @Override
    protected void registerProvider(MultiTypeAdapter adapter) {
        // adapter.register(SimpleItem.class, new SimpleItemProvider());
    }

    /**
     * 创建对应的Presenter, 通常需要传递两个对象（视图和数据服务）
     */
    @Override
    public DeviceListContract.Presenter createPresenter() {
        return new DeviceListPresenter(this);
    }
}