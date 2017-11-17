package com.out.accu.link.data.util;

import com.out.accu.link.data.DataManager;
import com.out.accu.link.data.mode.Response;

/**
 * <p>Title: <／p>
 * <p>Description:
 * Flag: header 2 + length 2 = 4;
 * Header: dst_id 6+src_id 6+maj 1+min 1+mcmd 1+scmd 1+dir 1+code 1 = 18
 * Body: xxx = N
 * End: sum 4 + tail 2 = 6;
 * <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/10/13
 */

public class PacketUtil {

    public static byte[] FLAG_START = {(byte) 0xFF, 0x5A};
    public static byte[] VERSION = {0x1, 0x0};
    public static byte[] FLAG_END = {0x55, (byte) 0xFF};
    public static byte[] DST_ID = {(byte) 0XFF, (byte) 0XFF, (byte) 0XFF, (byte) 0XFF, (byte) 0XFF, (byte) 0XFF};

    public static byte TYPE_REQUEST = 0;
    public static byte TYPE_RESPONSE = 1;
    public static byte TYPE_UPLOAD = 2;

    public static int FLAG_START_LENGTH = 6;
    public static int HEADER_LENGTH = 18;
    public static int FLAG_END_LENGTH = 6;

    public static byte[] CMD_DEVICE_VALUE_UPLOAD = {(byte) 0xA1, 0x01};
    public static byte[] CMD_DEVICE_ONLINE_UPLOAD = {(byte) 0xA1, 0x05};

    public static byte[] CMD_SET_CHANNEL_RANGE = {(byte) 0xA2, 0x01};
    public static byte[] CMD_SET_VALUE_RANGE = {(byte) 0xA2, 0x02};
    public static byte[] CMD_SET_REPORT_PERIOD = {(byte) 0xA2, 0x03};
    public static byte[] CMD_SET_LOW_ALARM_ENABLE = {(byte) 0xA2, 0x04};
    public static byte[] CMD_SET_LOW_ALARM_PARAMS = {(byte) 0xA2, 0x05};
    public static byte[] CMD_SET_LOW_LOW_ALARM_ENABLE = {(byte) 0xA2, 0x06};
    public static byte[] CMD_SET_LOW_LOW_ALARM_PARAMS = {(byte) 0xA2, 0x07};
    public static byte[] CMD_SET_ALIAS_NAME = {(byte) 0xA2, 0x08};
    public static byte[] CMD_SET_LOCATION = {(byte) 0xA2, 0x09};
    public static byte[] CMD_SET_DEFENSE_ENABLE = {(byte) 0xA2, 0x0A};

    public static byte[] CMD_GET_CHANNEL_RANGE = {(byte) 0xA3, 0x01};
    public static byte[] CMD_GET_VALUE_RANGE = {(byte) 0xA3, 0x02};
    public static byte[] CMD_GET_REPORT_PERIOD = {(byte) 0xA3, 0x03};
    public static byte[] CMD_GET_LOW_ALARM_ENABLE = {(byte) 0xA3, 0x04};
    public static byte[] CMD_GET_LOW_ALARM_PARAMS = {(byte) 0xA3, 0x05};
    public static byte[] CMD_GET_LOW_LOW_ALARM_ENABLE = {(byte) 0xA3, 0x06};
    public static byte[] CMD_GET_LOW_LOW_ALARM_PARAMS = {(byte) 0xA3, 0x07};
    public static byte[] CMD_GET_ALIAS_NAME = {(byte) 0xA3, 0x08};
    public static byte[] CMD_GET_LOCATION = {(byte) 0xA3, 0x09};
    public static byte[] CMD_GET_DEFENSE_ENABLE = {(byte) 0xA3, 0x0A}; //defenseEnable
    public static byte[] CMD_GET_TEM= {(byte) 0xA3, 0x0B};
    public static byte[] CMD_GET_PHONE_NUMBER = {(byte) 0xA3, 0x0C};
    public static byte[] CMD_GET_LTE_STATUS = {(byte) 0xA3, 0x0D};
    public static byte[] CMD_GET_TX = {(byte) 0xA3, 0x0E};
    public static byte[] CMD_GET_LAST_DATA = {(byte) 0xA3, 0x0F};
    public static byte[] CMD_GET_HISTORY = {(byte) 0xA3, 0x10};

    public static byte[] CMD_LOGIN = {(byte) 0xA4, 0x01};
    public static byte[] CMD_LOGOUT = {(byte) 0xA4, 0x02};
    public static byte[] CMD_SET_PASSWORD = {(byte) 0xA4, 0x03};
    public static byte[] CMD_SET_NAME = {(byte) 0xA4, 0x04};
    public static byte[] CMD_SET_PHONE = {(byte) 0xA4, 0x05};
    public static byte[] CMD_GET_USER_NAME = {(byte) 0xA4, 0x06};
    public static byte[] CMD_GET_USER_PHONE = {(byte) 0xA4, 0x07};
    public static byte[] CMD_GET_DEVICES = {(byte) 0xA4, 0x08};

    public static int sCount;

    /**
     *
     * @param cmd 命令
     * @param type 请求、响应、上报
     * @param data 数据
     * @return
     */
    public static byte[] getPacket(byte[] cmd, byte type, byte[] data) {
        int dataLength = HEADER_LENGTH + data.length;
        int length = FLAG_START_LENGTH + dataLength + FLAG_END_LENGTH;
        byte[] allData = new byte[length];
        System.arraycopy(DST_ID, 0, allData, 6, 6);
        System.arraycopy(DataManager.getInstance().getDeviceId(), 0,
                allData, 12, 6);
        System.arraycopy(FLAG_START, 0, allData, 0, FLAG_START.length);
        allData[2] = (byte) (dataLength & 0xFF);
        allData[3] = (byte) ((dataLength & 0xFF00) >> 8);
        allData[18] = VERSION[0];
        allData[19] = VERSION[1];
        allData[20] = cmd[0];
        allData[21] = cmd[1];
        allData[22] = type;
        System.arraycopy(data, 0, allData, FLAG_START_LENGTH + HEADER_LENGTH, data.length);
        allData[28 + data.length] = FLAG_END[0];
        allData[29 + data.length] = FLAG_END[1];
//        return addHeader(allData);
        return allData;
    }

    public static Response parserPacket(byte[] data) {
        byte[] cmd = new byte[2];
        cmd[0] = data[20];
        cmd[1] = data[21];
        byte type = data[22];
        byte code = data[23];

        Response response = new Response();
        response.type = type;
        response.code = code;
        response.cmd = cmd;
        int length = (data[2] + (data[3] << 8)) - 18;
        response.data = new byte[length];
        System.arraycopy(data, 24, response.data, 0, length);


        return response;
    }

    /**
     * 长度（2）+ 序号（2）+ 执行码（1）+ 标志位 + 1
     */
    public static byte[] addHeader(byte[] data) {
        int length = data.length + 6;
        byte[] allData = new byte[length];
        allData[0] = (byte) (length & 0xFF);
        allData[1] = (byte) ((length & 0xFF00) >> 8);
        allData[2] = (byte) (sCount & 0xFF);
        allData[3] = (byte) ((sCount & 0xFF00) >> 8);
        allData[4] = 0;
        allData[5] = 0;
        ByteUtil.arrayCopy(data, 0, allData, 6, data.length);
        return allData;
    }

    public static byte[] receive() {
        byte[] receive = new byte[6];
        receive[0] = 6;
        receive[1] = 0;
        receive[2] = (byte) (sCount & 0xFF);
        receive[3] = (byte) ((sCount & 0xFF00) >> 8);
        receive[4] = 1;
        receive[5] = 0;
        sCount++;
        return receive;
    }
}
