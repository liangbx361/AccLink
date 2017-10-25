package com.out.accu.link.page.main.history;

import com.cyou17173.android.arch.base.page.SmartTransformer;
import com.out.accu.link.data.DataService;

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

    HistoryPresenter(HistoryContract.View view, DataService dataService) {
        mView = view;
        mDataService = dataService;
    }

    /**
     * 开始执行业务逻辑，如加载缓存、请求网络数据
     */
    @Override
    public void start() {

    }

    /**
     * 重新加载
     */
    @Override
    public void retryLoad() {

    }

    @Override
    public void search(long start, long end) {
        mDataService.getHistory(start, end)
                .compose(SmartTransformer.applySchedulers())
                .compose(mView.bindToLifecycle())
                .subscribe(deviceHistories -> {
                    mView.showHistory(deviceHistories);
                }, throwable -> throwable.printStackTrace());
    }
}
