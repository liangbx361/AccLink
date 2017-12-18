package com.out.accu.link.data.mode;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/12/17
 */

public class Recode {

    public static final int STATUS_WAIT = 0;
    public static final int STATUS_SEND = 1;
    public static final int STATUS_RESP = 2;
    public static final int STATUS_RESP_DATA = 3;
    public static final int STATUS_FINISH = 4;

    public short reqId;

    public short respId;

    public byte[] cmd;

    public byte[] reqData;

    public int stats = STATUS_WAIT;

    public long sendTime;

    public static boolean isTimeOut(long sendTime) {
        long time = System.currentTimeMillis() - sendTime;
        return time > 5000;
    }
}
