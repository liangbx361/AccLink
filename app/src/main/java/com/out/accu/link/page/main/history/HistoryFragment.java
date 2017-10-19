package com.out.accu.link.page.main.history;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cyou17173.android.arch.base.app.Smart;
import com.cyou17173.android.arch.base.page.SmartStateFragment;
import com.cyou17173.android.component.state.view.StateManager;
import com.github.mikephil.charting.charts.LineChart;
import com.out.accu.link.R;
import com.out.accu.link.data.mode.Device;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.out.accu.link.AppConfig.DEVICE_LIST;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <P>Company: 17173</p>
 *
 * @author
 * @version 2017-10-15
 */
public class HistoryFragment extends SmartStateFragment<HistoryContract.Presenter> implements HistoryContract.View {

    @BindView(R.id.devices)
    Spinner mSpDevices;
    @BindView(R.id.startTime)
    TextView mTvStartTime;
    @BindView(R.id.endTime)
    TextView mTvEndTime;
    @BindView(R.id.chart)
    LineChart mChart;

    private List<Device> mDevices;

    private int mStartYear;
    private int mStartMonth;
    private int mStartDay;
    private int mEndYear;
    private int mEndMonth;
    private int mEndDay;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        View view = rootView.findViewById(R.id.chart);
        StateManager.Builder builder = buildStateView(view);
        builder.onClickListener(this);
        setStateManager(builder.build());
        return rootView;
    }

    /**
     * 设置布局ID
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_device_history;
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        ButterKnife.bind(this, getView());
        mDevices = Smart.getApp().getConfig()
                .getRuntimeConfig().getParcelableArrayList(DEVICE_LIST);

        List<String> deviceDesc = new ArrayList<>();
        for(Device device : mDevices) {
            deviceDesc.add(device.aliasName);
        }

        ArrayAdapter adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, deviceDesc);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpDevices.setAdapter(adapter);

        final Calendar c = Calendar.getInstance();
        mStartYear = c.get(Calendar.YEAR);
        mStartMonth = c.get(Calendar.MONTH);
        mStartDay = c.get(Calendar.DAY_OF_MONTH);
        mEndYear = mStartYear;
        mEndMonth = mStartMonth;
        mEndDay = mStartDay;
    }

    /**
     * 注销事件
     */
    @Override
    public void unregisterEvent() {

    }

    /**
     * 注册事件，如事件监听、广播接收等
     */
    @Override
    public void registerEvent() {
        mTvStartTime.setOnClickListener(v -> {
            new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
                mStartYear = year;
                mStartMonth = month;
                mStartDay = dayOfMonth;
                mTvStartTime.setText(year + "-" + month + "-" + dayOfMonth);
            }, mStartYear, mStartMonth, mStartDay).show();
        });

        mTvEndTime.setOnClickListener(v -> {
            new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
                mEndYear = year;
                mEndMonth = month;
                mEndDay = dayOfMonth;
                mTvEndTime.setText(year + "-" + month + "-" + dayOfMonth);
            }, mStartYear, mStartMonth, mStartDay).show();
        });
    }

    /**
     * 创建对应的Presenter, 通常需要传递两个对象（视图和数据服务）
     */
    @Override
    public HistoryContract.Presenter createPresenter() {
        return new HistoryPresenter(this);
    }

}