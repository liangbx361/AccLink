package com.out.accu.link;

import com.cyou17173.android.arch.base.app.SmartApplication;
import com.cyou17173.android.arch.base.app.SmartConfig;
import com.out.accu.link.data.DataManager;
import com.out.accu.link.data.config.Platform;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/9/29
 */

public class App extends SmartApplication {

    @Override
    protected void initConfig(SmartConfig smartConfig) {

    }

    @Override
    protected void asyncInit() {
        DataManager.init(Platform.TEST);
    }

    @Override
    protected void syncInit() {

    }
}
