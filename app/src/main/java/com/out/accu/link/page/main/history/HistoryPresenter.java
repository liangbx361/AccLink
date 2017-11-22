package com.out.accu.link.page.main.history;

import com.cyou17173.android.arch.base.page.SmartTransformer;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.out.accu.link.data.BusAction;
import com.out.accu.link.data.DataService;
import com.out.accu.link.data.mode.DeviceHistory;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
        mPublishSubject
//                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(history1 -> {
                    mView.showHistory(mDeviceHistory);
                });

        Observable.defer(() -> Observable.just(getTestData()))
                .compose(SmartTransformer.applySchedulers())
                .subscribe(history -> {
                    mView.showHistory(history);
                });

    }

    @Override
    public void search(String deviceId, long start, long end) {
        mDataService.getHistory(deviceId, start/1000, end/1000);
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_HISTORY),
            }
    )
    public void respHistory(DeviceHistory history) {
        mDeviceHistory.deviceId = history.deviceId;
        mDeviceHistory.list.addAll(history.list);
        mPublishSubject.onNext(mDeviceHistory);
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
