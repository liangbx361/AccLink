package com.out.accu.link.data;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/11/14
 */

public interface BusAction {

    String RESP_LOGIN = "RESP_LOGIN";

    String RESP_DEVICES = "RESP_DEVICES";

    String ONLINE_DEVICE = "ONLINE_DEVICE";

    // 更新设备数据
    String UPDATE_DEVICE_DATA = "UPDATE_DEVICE_DATA";

    String RESP_SET_CHANNEL = "RESP_SET_CHANNEL";

    String RESP_SET_VALUE = "RESP_SET_VALUE";

    // report_period
    String RESP_SET_REPORT_PERIOD = "RESP_SET_REPORT_PERIOD";

    String RESP_SET_LOW_ALARM_ENABLE = "RESP_SET_LOW_ALARM_ENABLE";

    String RESP_SET_LOW_ALARM_PARAMS = "RESP_SET_LOW_ALARM_PARAMS";

    String RESP_SET_LOW_LOW_ALARM_ENABLE = "RESP_SET_LOW_LOW_ALARM_ENABLE";

    String RESP_SET_LOW_LOW_ALARM_PARAMS = "RESP_SET_LOW_LOW_ALARM_PARAMS";

    String RESP_SET_ALIAS_NAME = "RESP_SET_ALIAS_NAME";

    String RESP_SET_DEFENSE_ENABLE = "RESP_SET_DEFENSE_ENABLE";

    String RESP_SET_LOCATION = "RESP_SET_LOCATION";

}
