package com.out.accu.link.data.util;

import com.out.accu.link.data.DataManager;
import com.out.accu.link.data.logger.AppLogger;
import com.out.accu.link.data.mode.Response;
import com.out.accu.link.data.mode.ResponseCmd;

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

    // 请求重试时间
    public static int REQ_RETRY_TIME = 10;

    public static byte[] CMD_DEVICE_VALUE_UPLOAD = {(byte) 0xA1, 0x01}; // -94
    public static byte[] CMD_DEVICE_ONLINE_UPLOAD = {(byte) 0xA1, 0x05}; // -90

    public static byte[] CMD_SET_CHANNEL_RANGE = {(byte) 0xA2, 0x01}; // -93
    public static byte[] CMD_SET_VALUE_RANGE = {(byte) 0xA2, 0x02};
    public static byte[] CMD_SET_REPORT_PERIOD = {(byte) 0xA2, 0x03};
    public static byte[] CMD_SET_LOW_ALARM_ENABLE = {(byte) 0xA2, 0x04};
    public static byte[] CMD_SET_LOW_ALARM_PARAMS = {(byte) 0xA2, 0x05};
    public static byte[] CMD_SET_LOW_LOW_ALARM_ENABLE = {(byte) 0xA2, 0x06};
    public static byte[] CMD_SET_LOW_LOW_ALARM_PARAMS = {(byte) 0xA2, 0x07};
    public static byte[] CMD_SET_ALIAS_NAME = {(byte) 0xA2, 0x08};
    public static byte[] CMD_SET_LOCATION = {(byte) 0xA2, 0x0A};
    public static byte[] CMD_SET_DEFENSE_ENABLE = {(byte) 0xA2, 0x09};

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
    public static byte[] CMD_GET_TEM = {(byte) 0xA3, 0x0B};
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
     * @param cmd  命令
     * @param type 请求、响应、上报
     * @param data 数据
     * @return
     */
    public static byte[] getPacket(byte[] cmd, short id, byte type, byte[] data) {
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
        return addHeader(id, allData);
    }

    /**
     * 可能包含多个条命令，需要处理
     */
    public static Response parserPacket(byte[] respData) {
        Response response = new Response();
        short pkgLength = ByteUtil.getShort(respData, 0);
        response.id = ByteUtil.getShort(respData, 2);

        // 数据总长度
        short dataLength = (short) (pkgLength - 6);
        if (dataLength == 0) {
            return response;
        }

        byte[] data = new byte[dataLength];
        ByteUtil.arrayCopy(respData, 6, data, 0, dataLength);

        boolean isMore;
        int offset = 0;
        do {
            ResponseCmd responseCmd = new ResponseCmd();
            byte[] cmd = new byte[2];
            cmd[0] = data[20+offset];
            cmd[1] = data[21+offset];

            byte type = data[22+offset];
            byte code = data[23+offset];

            responseCmd.type = type;
            responseCmd.code = code;
            responseCmd.cmd = cmd;

            int length = ByteUtil.getShort(data, 2+offset) - 18;
            responseCmd.data = new byte[length];
            System.arraycopy(data, 24+offset, responseCmd.data, 0, length);
            response.mResponseCmds.add(responseCmd);

            AppLogger.get().d("response", "cmd ->" + ByteUtil.getCmd(cmd[0], cmd[1]));

            //包的实际长度
            int cmdLength = ByteUtil.getShort(data, 2+offset)+12;
            if(dataLength == cmdLength) {
                isMore = false;
            } else {
                isMore = true;
                dataLength -= cmdLength;
                offset += cmdLength;
            }
        } while (isMore);

        return response;
    }

    /**
     * 长度（2）+ 序号（2）+ 执行码（1）+ 打包标志位（1）
     */
    public static byte[] addHeader(short id, byte[] data) {
        short length = (short) (data.length + 6);
        byte[] packageData = new byte[length];
        byte[] lengthBytes = ByteUtil.shortToByte(length);
        byte[] idBytes = ByteUtil.shortToByte(id);
        packageData[0] = lengthBytes[0];
        packageData[1] = lengthBytes[1];
        packageData[2] = idBytes[0];
        packageData[3] = idBytes[1];
        packageData[4] = 0;
        packageData[5] = 0;
        ByteUtil.arrayCopy(data, 0, packageData, 6, data.length);
        return packageData;
    }

    public static byte[] getResp(short id) {
        byte[] idBytes = ByteUtil.shortToByte(id);
        byte[] resp = new byte[6];
        resp[0] = 0x06;
        resp[1] = 0x00;
        resp[2] = idBytes[0];
        resp[3] = idBytes[1];
        resp[4] = 0x01;
        resp[5] = 0x00;
        return resp;
    }
}
