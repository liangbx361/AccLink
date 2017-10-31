package com.out.accu.link.data.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <P>Company: 17173</p>
 *
 * @author liangbx
 * @version 2017/6/6
 */

public enum Platform {
    MOCK("", 0),
    TEST("211.72.229.132", 60003),
//    TEST("127.0.0.1", 60003),
//    TEST("10.0.3.2", 60003),
//    TEST("192.168.2.118", 60003),
    RELEASE("", 0);

    private String mIp;
    private int mPort;

    Platform(String ip, int port) {
        this.mIp = ip;
        this.mPort = port;
    }

    public String getIp() {
        return mIp;
    }

    public InetAddress getInet() {
        try {
            return InetAddress.getByName(mIp);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getPort() {
        return mPort;
    }
}