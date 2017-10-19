package com.out.accu.link.page.main.device.detail;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.cyou17173.android.arch.base.page.SmartStateActivity;
import com.out.accu.link.R;
import com.out.accu.link.data.mode.Device;
import com.qmuiteam.qmui.widget.QMUILoadingView;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <P>Company: 17173</p>
 *
 * @author
 * @version 2017-10-13
 */
public class DeviceDetailActivity extends SmartStateActivity<DeviceDetailContract.Presenter> implements
        DeviceDetailContract.View, View.OnClickListener {

//    @BindView(R.id.topbar)
//    QMUITopBar mTopbar;
    @BindView(R.id.groupListView)
    QMUIGroupListView mGroupListView;

    Device mDevice;

    QMUICommonListItemView channel1Item;
    QMUICommonListItemView channel2Item;

    /**
     * 设置布局ID
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_device_detail;
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        ButterKnife.bind(this);
        mDevice = getIntent().getParcelableExtra("device");
        showData(mDevice);
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
    public DeviceDetailContract.Presenter createPresenter() {
        return new DeviceDetailPresenter(this);
    }

    @Override
    public void showData(Device device) {
        channel1Item = mGroupListView.createItemView("频道1量程");
        channel1Item.setText(device.channel1Range+"mA");

        channel2Item = mGroupListView.createItemView("频道2量程");
        channel2Item.setText(device.channel2Range+"mA");

        QMUIGroupListView.newSection(this)
                .setTitle("两路量程")
                .addItemView(channel1Item, this)
                .addItemView(channel2Item, this)
                .addTo(mGroupListView);
    }

    private void initGroupListView() {


        QMUICommonListItemView itemWithDetail = mGroupListView.createItemView("Item 2");
        itemWithDetail.setDetailText("在右方的详细信息");

        QMUICommonListItemView itemWithDetailBelow = mGroupListView.createItemView("Item 3");
        itemWithDetailBelow.setOrientation(QMUICommonListItemView.VERTICAL);
        itemWithDetailBelow.setDetailText("在标题下方的详细信息");

        QMUICommonListItemView itemWithChevron = mGroupListView.createItemView("Item 4");
        itemWithChevron.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView itemWithSwitch = mGroupListView.createItemView("Item 5");
        itemWithSwitch.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
        itemWithSwitch.getSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getActivity(), "checked = " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });

        QMUICommonListItemView itemWithCustom = mGroupListView.createItemView("Item 6");
        itemWithCustom.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        QMUILoadingView loadingView = new QMUILoadingView(getActivity());
        itemWithCustom.addAccessoryCustomView(loadingView);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof QMUICommonListItemView) {
                    CharSequence text = ((QMUICommonListItemView) v).getText();
                    Toast.makeText(getActivity(), text + " is Clicked", Toast.LENGTH_SHORT).show();
                }
            }
        };

        QMUIGroupListView.newSection(this)
                .setTitle("两路量程")
//                .addItemView(normalItem, onClickListener)
                .addItemView(itemWithDetail, onClickListener)
                .addItemView(itemWithDetailBelow, onClickListener)
                .addItemView(itemWithChevron, onClickListener)
                .addItemView(itemWithSwitch, onClickListener)
                .addTo(mGroupListView);

        QMUIGroupListView.newSection(this)
                .setTitle("Section 2: 自定义右侧 View")
                .addItemView(itemWithCustom, onClickListener)
                .addTo(mGroupListView);
    }

    @Override
    public void onClick(View v) {

    }
}