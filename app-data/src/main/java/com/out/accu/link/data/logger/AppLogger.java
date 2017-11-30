package com.out.accu.link.data.logger;

import com.cyou17173.android.component.logger.DefaultLoggerImpl;
import com.cyou17173.android.component.logger.Logger;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/11/28
 */

public class AppLogger {

    private static volatile Logger sLogger;

    public static Logger get() {
        if (sLogger == null) {
            synchronized (AppLogger.class) {
                if (sLogger == null) {
                    sLogger = new DefaultLoggerImpl();
                }
            }
        }
        return sLogger;
    }

    public static synchronized void set(Logger logger) {
        sLogger = logger;
    }
}
