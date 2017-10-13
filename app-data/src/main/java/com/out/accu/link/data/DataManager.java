package com.out.accu.link.data;

import android.os.Build;

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

public class DataManager {
    private static volatile  DataManager sDataManager;

    private Platform mPlatform;
    // TODO 6字节
    private String deviceId;

    private DataService mDataService;

    private DataManager(Platform platform) {
        mPlatform = platform;
        deviceId = Build.ID;
    }

    public static DataManager init(Platform platform) {
        if(sDataManager == null) {
            synchronized (DataManager.class) {
                if(sDataManager == null) {
                    sDataManager = new DataManager(platform);
                }
            }
        }

        return sDataManager;
    }

    public static DataManager getDataManager() {
        return sDataManager;
    }

    public Platform getPlatform() {
        return mPlatform;
    }

    public static DataManager getInstance() {
        return sDataManager;
    }

    public DataService getDataService() {
        return mDataService;
    }
}
