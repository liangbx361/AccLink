package com.out.accu.link.page.main.history;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.out.accu.link.R;
import com.out.accu.link.data.DataManager;
import com.out.accu.link.data.mode.Device;
import com.out.accu.link.data.util.ByteUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/11/19
 */

public class SearchDialog {

    @BindView(R.id.condition)
    ViewGroup mViewGroup;
    @BindView(R.id.devices)
    Spinner mSpDevices;
    @BindView(R.id.startTime)
    TextView mTvStartTime;
    @BindView(R.id.endTime)
    TextView mTvEndTime;
    @BindView(R.id.searchBtn)
    Button mBtnSearch;
    @BindView(R.id.cancelBtn)
    Button mBtnCancel;

    private OnSearchListener mOnSearchListener;

    private List<Device> mDevices;
    private Date mStartDate;
    private Date mEndDate;

    public void initView(View view) {
        ButterKnife.bind(this, view);

        mDevices = DataManager.getInstance().getModeData().getDevices();

        List<String> deviceDesc = new ArrayList<>();
        for (Device device : mDevices) {
            deviceDesc.add(ByteUtil.getId(device.id));
        }
        ArrayAdapter adapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_item, deviceDesc);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpDevices.setAdapter(adapter);

        mTvStartTime.setOnClickListener(v -> {
            //时间选择器
            TimePickerView pvTime = new TimePickerView.Builder(view.getContext(),
                    (date, v1) -> {//选中事件回调
                        mStartDate = date;
                        mTvStartTime.setText(getTime(date));
                    }).build();
            pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
            pvTime.show();
        });

        mTvEndTime.setOnClickListener(v -> {
            //时间选择器
            TimePickerView pvTime = new TimePickerView.Builder(view.getContext(),
                    (date, v1) -> {//选中事件回调
                        mEndDate = date;
                        mTvEndTime.setText(getTime(date));
                    }).build();
            pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
            pvTime.show();
        });

        mBtnSearch.setOnClickListener(v -> {
            int position = mSpDevices.getSelectedItemPosition();
            String deviceId = mDevices.get(position).id;
            mOnSearchListener.onSearch(deviceId, mStartDate.getTime(), mEndDate.getTime());
        });

        mBtnCancel.setOnClickListener(v -> {
            hide();
        });
    }

    public interface OnSearchListener {
        void onSearch(String id, long start, long end);
    }

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        mOnSearchListener = onSearchListener;
    }

    private String getTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public void show() {
        mViewGroup.setVisibility(View.VISIBLE);
    }

    public void hide() {
        mViewGroup.setVisibility(View.GONE);
    }
}
