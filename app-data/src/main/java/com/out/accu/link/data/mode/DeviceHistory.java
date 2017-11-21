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
 * @version 2017/10/15
 */

public class DeviceHistory {

    public String deviceId;
    public List<Item> list = new ArrayList<>();

    public static class Item {

        // 时间
        public long time;

        // 值
        public int value;

        // 时间显示用
        public String timeDesc;
    }
}
