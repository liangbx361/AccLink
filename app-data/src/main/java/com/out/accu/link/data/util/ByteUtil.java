package com.out.accu.link.data.util;

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

    public static void arrayCopy(byte[] src,  int srcPos,
                                   byte[] dest, int destPos,
                                   int maxLength) {
        if(src.length < maxLength) {
            System.arraycopy(src, srcPos, dest, destPos, src.length);
        } else {
            System.arraycopy(src, srcPos, dest, destPos, maxLength);
        }
    }

    public static int getInt(byte[] data, int srcPos) {
        return data[srcPos+3] << 24 | data[srcPos+2] << 16
                | data[srcPos+1] << 8 | data[srcPos];
    }

    public static String getString(byte[] data, int srcPos, int length) {
        byte[] str = new byte[length];
        arrayCopy(data, srcPos, str, 0, length);
        return new String(str, java.nio.charset.Charset.forName("GB2312"));
    }

    public static boolean getBoolean(byte[] data, int srcPos) {
        return data[srcPos] == 1;
    }

    public static double getDouble(byte[] data, int srcPos){
        long l;

        l=data[srcPos];
        l&=0xff;
        l|=((long)data[srcPos+1]<<8);
        l&=0xffff;
        l|=((long)data[srcPos+2]<<16);
        l&=0xffffff;
        l|=((long)data[srcPos+3]<<24);
        l&=0xffffffffl;
        l|=((long)data[srcPos+4]<<32);
        l&=0xffffffffffl;

        l|=((long)data[srcPos+5]<<40);
        l&=0xffffffffffffl;
        l|=((long)data[srcPos+6]<<48);

        l|=((long)data[srcPos+7]<<56);
        return Double.longBitsToDouble(l);
    }

    public static byte[] stringToByte(String str, int length) {
        byte[] strByte = new byte[length];
        arrayCopy(str.getBytes(), 0, strByte, 0, length);
        return strByte;
    }

    public static byte[] intToByte(int value) {
        return new byte[] {
                (byte) ((value >> 24) & 0xFF),
                (byte) ((value >> 16) & 0xFF),
                (byte) ((value >> 8) & 0xFF),
                (byte) (value & 0xFF)
        };
    }

    public static byte[] doubleToByte(double d) {
        long value = Double.doubleToRawLongBits(d);
        byte[] byteRet = new byte[8];
        for (int i = 0; i < 8; i++) {
            byteRet[i] = (byte) ((value >> 8 * i) & 0xff);
        }
        return byteRet;
    }

    public static String getId(String deviceId) {
        byte[] data = deviceId.getBytes();
        StringBuffer sb = new StringBuffer();
        for(byte item : data) {
            String hex = Integer.toHexString(item & 0xFF);
            if(hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
