package com.out.accu.link.page.main.history;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.out.accu.link.R;
import com.out.accu.link.data.DataManager;
import com.out.accu.link.data.mode.Device;
import com.out.accu.link.util.DeviceUtil;

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

    private List<Device> mDevices = new ArrayList<>();
    private Date mStartDate = new Date();
    private Date mEndDate = new Date();

    private TimePickerView startTimeView;
    private TimePickerView endTimeView;

    public void initView(View view) {
        ButterKnife.bind(this, view);

        //时间选择器
        startTimeView = new TimePickerView.Builder(view.getContext(),
                (date, v1) -> {//选中事件回调
                    mStartDate = date;
                    mTvStartTime.setText(getTime(date));
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .build();
        Calendar start = Calendar.getInstance();
        start.setTime(mStartDate);
        startTimeView.setDate(start);//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。

        mTvStartTime.setOnClickListener(v -> {
            startTimeView.show();
        });

        //时间选择器
        endTimeView = new TimePickerView.Builder(view.getContext(),
                (date, v1) -> {//选中事件回调
                    mEndDate = date;
                    mTvEndTime.setText(getTime(date));
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .build();
        Calendar end = Calendar.getInstance();
        end.setTime(mEndDate);
        endTimeView.setDate(end);//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        mTvEndTime.setOnClickListener(v -> {
            endTimeView.show();
        });

        mBtnSearch.setOnClickListener(v -> {
            if(mStartDate == null) {
                Toast.makeText(v.getContext(), R.string.start_time_empty, Toast.LENGTH_SHORT).show();
                return;
            }

            if(mEndDate == null) {
                Toast.makeText(v.getContext(), R.string.end_time_empty, Toast.LENGTH_SHORT).show();
                return;
            }

            if(mStartDate.compareTo(mEndDate) == 1) {
                Toast.makeText(v.getContext(), R.string.start_time_must_less_end_time, Toast.LENGTH_SHORT).show();
                return;
            }

            mStartDate.setHours(0);
            mStartDate.setMinutes(0);
            mStartDate.setSeconds(0);
            mEndDate.setHours(23);
            mEndDate.setMinutes(59);
            mEndDate.setSeconds(59);

            try {
                int position = mSpDevices.getSelectedItemPosition();
                byte[] deviceId = mDevices.get(position).idBytes;
                mOnSearchListener.onSearch(deviceId, mStartDate.getTime(), mEndDate.getTime());
                hide();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mBtnCancel.setOnClickListener(v -> {
            hide();
        });

        mTvStartTime.setText(getTime(mStartDate));
        mTvEndTime.setText(getTime(mEndDate));
    }

    public interface OnSearchListener {
        void onSearch(byte[] id, long start, long end);
    }

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        mOnSearchListener = onSearchListener;
    }

    private String getTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public void show() {
        mViewGroup.setVisibility(View.VISIBLE);
    }

    public void hide() {
        mViewGroup.setVisibility(View.GONE);
    }

    public void updateDevices() {
       List<Device> allDevices = DataManager.getInstance().getModeData().getDevices();

        mDevices.clear();
        List<String> devicesName = new ArrayList<>();
        for (Device device : allDevices) {
            mDevices.add(device);
            devicesName.add(DeviceUtil.getDeviceName(device));
        }
        ArrayAdapter adapter = new ArrayAdapter<>(mViewGroup.getContext(), R.layout.item_device_id, devicesName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpDevices.setAdapter(adapter);
    }
}
