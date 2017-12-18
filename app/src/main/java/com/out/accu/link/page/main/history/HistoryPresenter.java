package com.out.accu.link.page.main.history;

import android.widget.Toast;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.out.accu.link.R;
import com.out.accu.link.data.BusAction;
import com.out.accu.link.data.DataService;
import com.out.accu.link.data.logger.AppLogger;
import com.out.accu.link.data.mode.DeviceHistory;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <P>Company: 17173</p>
 *
 * @author
 * @version 2017-10-15
 */
class HistoryPresenter implements HistoryContract.Presenter {

    private HistoryContract.View mView;
    private DataService mDataService;
    private DeviceHistory mDeviceHistory = new DeviceHistory();
    private PublishSubject<DeviceHistory> mPublishSubject;

    int count = 0;

    HistoryPresenter(HistoryContract.View view, DataService dataService) {
        mView = view;
        mDataService = dataService;
    }

    /**
     * 开始执行业务逻辑，如加载缓存、请求网络数据
     */
    @Override
    public void start() {
        mPublishSubject = PublishSubject.create();
        mPublishSubject.subscribeOn(Schedulers.io());
        mPublishSubject.observeOn(AndroidSchedulers.mainThread());

        mPublishSubject
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(history1 -> {
                    mView.hideLoading();
                    AppLogger.get().d("HistoryPresenter", "update");
                    mView.showHistory(mDeviceHistory);
                });
    }

    @Override
    public void search(byte[] deviceId, long start, long end) {
        mView.showLoading();
        mDeviceHistory.list.clear();
        count=0;
        mDataService.getHistory(deviceId, start/1000, end/1000);
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_HISTORY),
            }
    )
    public void respHistory(DeviceHistory history) {
            count++;
            mDeviceHistory.deviceId = history.deviceId;
            mDeviceHistory.list.addAll(history.list);
            mPublishSubject.doOnNext(history1 -> {
                // 时间比较
                Collections.sort(mDeviceHistory.list, (o1, o2) -> o1.time > o2.time ? 1 : -1);
            });
            mPublishSubject.onNext(mDeviceHistory);
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_HISTORY_ERROR),
            }
    )
    public void respHistoryError(Object o) {
        mView.hideLoading();
        Toast.makeText(mView.getActivity(), R.string.fetch_history_error, Toast.LENGTH_SHORT).show();
    }

    public DeviceHistory getTestData() {
        DeviceHistory history = new DeviceHistory();
        history.deviceId = "0";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(2001, 10, 1));
        for(int i=0; i<20; i++) {
            DeviceHistory.Item item = new DeviceHistory.Item();
            item.time = calendar.getTime().getTime();
            item.value = new Random().nextInt(50);
            history.list.add(item);
            calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+1);
        }
        return history;
    }
}
