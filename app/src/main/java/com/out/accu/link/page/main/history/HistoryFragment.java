package com.out.accu.link.page.main.history;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cyou17173.android.arch.base.page.SmartStateFragment;
import com.cyou17173.android.component.state.view.StateManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.out.accu.link.R;
import com.out.accu.link.data.DataManager;
import com.out.accu.link.data.mode.Device;
import com.out.accu.link.data.mode.DeviceHistory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.search)
    Button mBtnSearch;

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
        mDevices = DataManager.getInstance().getModeData().getDevices();

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

        // ============= 图表 =============
        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.LTGRAY);

        mChart.animateX(2500);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setYOffset(11f);
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

        mBtnSearch.setOnClickListener(v -> {
            getPresenter().search(0, 0);
        });
    }

    /**
     * 创建对应的Presenter, 通常需要传递两个对象（视图和数据服务）
     */
    @Override
    public HistoryContract.Presenter createPresenter() {
        return new HistoryPresenter(this, DataManager.getInstance().getDataService());
    }

    @Override
    public void showHistory(List<DeviceHistory> histories) {

        ArrayList<Entry> values = new ArrayList<>();
        for(DeviceHistory history : histories) {
            Entry entry = new Entry(history.time, history.value);
            values.add(entry);
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "History");
            set1.setDrawIcons(false);

            // set the line to be drawn like this "- - - - - -"
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            }
            else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            mChart.setData(data);
        }
    }
}