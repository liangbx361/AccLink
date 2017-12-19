package com.out.accu.link.data.mode;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/12/19
 */

public class Response {

    // 序号
    public short id;

    public List<ResponseCmd> mResponseCmds = new ArrayList<>();

    public boolean isCmdPkg() {
        return mResponseCmds.size() > 0;
    }
}
