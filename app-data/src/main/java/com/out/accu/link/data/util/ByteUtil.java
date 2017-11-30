package com.out.accu.link.data.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/10/14
 */

public class ByteUtil {

    public static void arrayCopy(byte[] src, int srcPos,
                                 byte[] dest, int destPos,
                                 int maxLength) {
        if (src.length < maxLength) {
            System.arraycopy(src, srcPos, dest, destPos, src.length);
        } else {
            System.arraycopy(src, srcPos, dest, destPos, maxLength);
        }
    }

    public static int getInt(byte[] data, int srcPos) {
        int value;
        value = (data[srcPos] & 0xFF)
                | ((data[srcPos + 1] & 0xFF) << 8)
                | ((data[srcPos + 2] & 0xFF) << 16)
                | ((data[srcPos + 3] & 0xFF) << 24);
        return value;
    }

    public static byte[] intToByte(int value) {
        return new byte[]{
                (byte) ((value) & 0xFF),
                (byte) ((value >> 8) & 0xFF),
                (byte) ((value >> 16) & 0xFF),
                (byte) (value >> 24 & 0xFF)
        };
    }

    public static String getString(byte[] data, int srcPos, int length) {
        byte[] str = new byte[length];
        arrayCopy(data, srcPos, str, 0, length);
        return new String(str, java.nio.charset.Charset.forName("GB2312")).trim();
    }

    public static boolean getBoolean(byte[] data, int srcPos) {
        return data[srcPos] == 1;
    }

    public static long getLong(byte[] data, int srcPos) {
        long l;

        l = data[srcPos];
        l &= 0xff;
        l |= ((long) data[srcPos + 1] << 8);
        l &= 0xffff;
        l |= ((long) data[srcPos + 2] << 16);
        l &= 0xffffff;
        l |= ((long) data[srcPos + 3] << 24);
        l &= 0xffffffffl;
        l |= ((long) data[srcPos + 4] << 32);
        l &= 0xffffffffffl;

        l |= ((long) data[srcPos + 5] << 40);
        l &= 0xffffffffffffl;
        l |= ((long) data[srcPos + 6] << 48);

        l |= ((long) data[srcPos + 7] << 56);

        return l;
    }

    public static double getDouble(byte[] data, int srcPos) {
        long value = 0;
        for (int i = 0; i < 8; i++) {
            value |= ((long) (data[i + srcPos] & 0xff)) << (8 * i);
        }
        return Double.longBitsToDouble(value);
    }

    public static byte[] stringToByte(String str, int length) {
        byte[] strByte = new byte[length];
        arrayCopy(str.getBytes(), 0, strByte, 0, length);
        return strByte;
    }


    public static byte[] doubleToByte(double d) {
        long value = Double.doubleToRawLongBits(d);
        byte[] byteRet = new byte[8];
        for (int i = 0; i < 8; i++) {
            byteRet[i] = (byte) ((value >> 8 * i) & 0xff);
        }
        return byteRet;
    }

    public static byte[] longToByte(long value) {
        return new byte[]{
                (byte) ((value >> 0) & 0xFF),
                (byte) ((value >> 8) & 0xFF),
                (byte) ((value >> 16) & 0xFF),
                (byte) ((value >> 24) & 0xFF),
                (byte) ((value >> 32) & 0xFF),
                (byte) ((value >> 40) & 0xFF),
                (byte) ((value >> 48) & 0xFF),
                (byte) ((value >> 56) & 0xFF)
        };
    }

    public static String getId(String deviceId) {
        byte[] data = deviceId.getBytes();
        StringBuffer sb = new StringBuffer();
        for (byte item : data) {
            String hex = Integer.toHexString(item & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static byte[] formatTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time * 1000));
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        byte[] timeByte = new byte[8];
        timeByte[0] = (byte) (year & 0xff);
        timeByte[1] = (byte) ((year >> 8) & 0xff);
        timeByte[2] = (byte) (month & 0xff);
        timeByte[3] = (byte) (day & 0xff);
        timeByte[4] = (byte) (hour & 0xff);
        timeByte[5] = (byte) (minute & 0xff);
        timeByte[6] = (byte) (second & 0xff);
        return timeByte;
    }

    public static long formatTime(byte[] timeByte, int index) {
        int year = ((timeByte[index + 1] << 8) & 0xff00) | (timeByte[index] & 0xff);
        int month = timeByte[index + 2];
        int day = timeByte[index + 3];
        int hour = timeByte[index + 4];
        int minute = timeByte[index + 5];
        int second = timeByte[index + 6];

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
