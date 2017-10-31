package com.out.accu.link.data;

import android.annotation.SuppressLint;
import android.os.Build;

import com.out.accu.link.data.config.Platform;
import com.out.accu.link.data.mock.MockService;
import com.out.accu.link.data.remote.RemoteService;
import com.out.accu.link.data.util.ByteUtil;

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
    private static volatile DataManager sDataManager;

    private Platform mPlatform;
    // TODO 6字节
    private byte[] deviceId = new byte[] {0x1, 0x2, 0x3, 0x4, 0x5, 0x6};

    private DataService mDataService;

    @SuppressLint("HardwareIds")
    private DataManager(Platform platform) {
        mPlatform = platform;

        switch (platform) {
            case MOCK:
                mDataService = new MockService();
                break;
            case TEST:
                ByteUtil.arrayCopy(Build.SERIAL.getBytes(), 0, deviceId, 0, 6);
                mDataService = new RemoteService(platform);
                break;
            case RELEASE:
                ByteUtil.arrayCopy(Build.SERIAL.getBytes(), 0, deviceId, 0, 6);
                mDataService = new RemoteService(platform);
                break;
        }
    }

    public static void init(Platform platform) {
        if (sDataManager == null) {
            synchronized (DataManager.class) {
                if (sDataManager == null) {
                    sDataManager = new DataManager(platform);
                }
            }
        }
    }

    public static DataManager getInstance() {
        return sDataManager;
    }

    public Platform getPlatform() {
        return mPlatform;
    }

    public DataService getDataService() {
        return mDataService;
    }

    public byte[] getDeviceId() {
        return deviceId;
    }
}
