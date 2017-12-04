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

    String RESP_LOGIN_FAIL = "RESP_LOGIN_FAIL";

    String RESP_DEVICES = "RESP_DEVICES";

    String ONLINE_DEVICE = "ONLINE_DEVICE";

    // 更新设备数据
    String UPDATE_DEVICE_DATA = "UPDATE_DEVICE_DATA";

    String UPDATE_ALIAS_NAME = "UPDATE_ALIAS_NAME";

    String UPDATE_DEVICE_VALUE = "UPDATE_DEVICE_VALUE";

    String RESP_SET_CHANNEL = "RESP_SET_CHANNEL";

    String RESP_SET_VALUE = "RESP_SET_VALUE";

    // report_period
    String RESP_SET_REPORT_PERIOD = "RESP_SET_REPORT_PERIOD";

    String RESP_SET_LOW_ALARM_ENABLE = "RESP_SET_LOW_ALARM_ENABLE";

    String RESP_SET_LOW_ALARM_PARAMS = "RESP_SET_LOW_ALARM_PARAMS";

    String RESP_SET_LOW_LOW_ALARM_ENABLE = "RESP_SET_LOW_LOW_ALARM_ENABLE";

    String RESP_SET_LOW_LOW_ALARM_PARAMS = "RESP_SET_LOW_LOW_ALARM_PARAMS";

    String RESP_SET_ALIAS_NAME = "RESP_SET_ALIAS_NAME";

    String RESP_SET_ALIAS_NAME_FAIL = "RESP_SET_ALIAS_NAME_FAIL";

    String RESP_SET_DEFENSE_ENABLE = "RESP_SET_DEFENSE_ENABLE";

    String RESP_SET_LOCATION = "RESP_SET_LOCATION";

    String RESP_HISTORY = "RESP_HISTORY";

    String RESP_HISTORY_ERROR = "RESP_HISTORY_ERROR";

    String RESP_GET_USERNAME = "RESP_GET_USERNAME";

    String RESP_GET_USER_MOBILE = "RESP_GET_USER_MOBILE";

    String RESP_SET_USERNAME_SUCCESS = "RESP_SET_USERNAME_SUCCESS";

    String RESP_SET_USER_MOBILE_SUCCESS = "RESP_SET_USER_MOBILE_SUCCESS";

    String RESP_SET_PASSWORD_SUCCESS = "RESP_SET_PASSWORD_SUCCESS";
}
