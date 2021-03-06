package com.out.accu.link.page.main.history;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;

import com.cyou17173.android.arch.base.page.SmartFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.out.accu.link.R;
import com.out.accu.link.data.DataManager;
import com.out.accu.link.data.mode.DeviceHistory;
import com.out.accu.link.util.ProgressHelper;
import com.out.accu.link.util.TimeUtil;

import java.util.ArrayList;

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
public class HistoryFragment extends SmartFragment<HistoryContract.Presenter> implements HistoryContract.View,
        SearchDialog.OnSearchListener{

    @BindView(R.id.chart)
    LineChart mChart;
    @BindView(R.id.search)
    FloatingActionButton mBtnSearch;
    SearchDialog mSearchDialog;

    ProgressDialog mProgressDialog;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activity.setTitle(getContext().getString(R.string.menu_graph));
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

        mProgressDialog = ProgressHelper.getProgressBar(getContext());

        mSearchDialog = new SearchDialog();
        mSearchDialog.initView(getView());
        mSearchDialog.setOnSearchListener(this);
        mSearchDialog.hide();

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

        XAxis xAxis = mChart.getXAxis();
        xAxis.setValueFormatter((value, axis) -> TimeUtil.format(value));
    }

    /**
     * 注册事件，如事件监听、广播接收等
     */
    @Override
    public void registerEvent() {
        mBtnSearch.setOnClickListener(v -> {
            mSearchDialog.show();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mSearchDialog.updateDevices();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            mSearchDialog.updateDevices();
        }
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
    public HistoryContract.Presenter createPresenter() {
        return new HistoryPresenter(this, DataManager.getInstance().getDataService());
    }

    @Override
    public void showHistory(DeviceHistory history) {

        if(history.list == null) {
            return;
        }

        if(history.list.size() == 0) {
            return;
        }

        ArrayList<Entry> values = new ArrayList<>();
        for(DeviceHistory.Item item : history.list) {
            Entry entry = new Entry(item.time, item.value/1000);
            values.add(entry);
        }

        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
            mChart.requestLayout();
        } else {
            set1 = new LineDataSet(values, "History");
            set1.setDrawIcons(false);

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            mChart.setData(data);
            mChart.notifyDataSetChanged();
            mChart.requestLayout();
        }
    }

    @Override
    public void showLoading() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        mProgressDialog.dismiss();
    }

    @Override
    public void onSearch(byte[] id, long start, long end) {
        getPresenter().search(id, start, end);
    }
}