package com.out.accu.link.data.mode;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/10/14
 */

public class Response {

    public int type;

    public int code;

    public byte[] cmd;

    public byte[] data;

    public boolean isSuccess() {
        return code == 0;
    }
}
