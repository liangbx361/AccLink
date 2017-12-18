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

    // 序号
    public short id;

    // 响应类型
    public int type;

    // 响应码
    public int code;

    // 命令
    public byte[] cmd;

    // 数据
    public byte[] data;

    public boolean isSuccess() {
        return code == 0;
    }

    public boolean isUpload() {
        return code == 2;
    }
}
