package com.out.accu.link.data.vm;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/10/18
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({ItemDataType.RANGE})
public @interface ItemDataType {

    // 量程
    int RANGE = 0;

    // 数据上报周期
    int REPORT_PERIOD = 1;

    // 数据低报
    int LOW_ALARM = 2;

    // 数据低低报
    int LOW_LOW_ALARM = 3;

    // 地理位置坐标
    int LOCATION = 4;

    // 设备布防状态

    // 温湿度

    // 模块号码

    // LTE工作状态

    // 网络流量统计信息
}
