package com.out.accu.link.data.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.cyou17173.android.arch.base.app.Smart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/12/21
 */

public class MacUtil {

    private static final String marshmallowMacAddress = "02:00:00:00:00:00";
    private static final String fileAddressMac = "/sys/class/net/wlan0/address";

    public static byte[] realId() {
        String macStr = Smart.getApp().getConfig().getAppConfig().readString("mac", null);
        byte[] idBytes = null;
        if(TextUtils.isEmpty(macStr)) {
            idBytes = getMac(Smart.getApp());
        }

        if(idBytes == null) {
            idBytes = new byte[6];
            ByteUtil.arrayCopy(Build.SERIAL.getBytes(), 0, idBytes, 0, 6);
        } else {
            String saveMac = formatMac(idBytes);
            Smart.getApp().getConfig().getAppConfig().saveString("mac", saveMac);
        }

        Log.d("Mac", formatMac(idBytes));

        return idBytes;
    }

    public static byte[] getMac(Context context) {
        WifiManager wifiMan = (WifiManager)context.getSystemService(Context.WIFI_SERVICE) ;
        WifiInfo wifiInf = wifiMan.getConnectionInfo();

        if(wifiInf !=null && marshmallowMacAddress.equals(wifiInf.getMacAddress())){
            byte[] result;
            try {
                result= getMacByInterface();
                if (result != null){
                    return result;
                } else {
                    result = getMacByFile(wifiMan);
                    return result;
                }
            } catch (IOException e) {
                Log.e("MobileAccess", "Erreur lecture propriete Adresse MAC");
            } catch (Exception e) {
                Log.e("MobileAcces", "Erreur lecture propriete Adresse MAC ");
            }
        } else{
            if (wifiInf != null && wifiInf.getMacAddress() != null) {
                return MacUtil.formatMac(wifiInf.getMacAddress());
            }
        }

        return null;
    }

    private static byte[] getMacByInterface(){
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (nif.getName().equalsIgnoreCase("wlan0")) {
                    return nif.getHardwareAddress();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 通过开启WIFI来获取MAC地址
     */
    private static byte[] getMacByFile(WifiManager wifiMan) throws Exception {
        byte[] ret;
        int wifiState = wifiMan.getWifiState();

        if(wifiState != WifiManager.WIFI_STATE_ENABLED) {
            wifiMan.setWifiEnabled(true);
        }
        File fl = new File(fileAddressMac);
        FileInputStream fin = new FileInputStream(fl);
        ret = MacUtil.formatMac(getString(fin));
        fin.close();

        boolean enabled = WifiManager.WIFI_STATE_ENABLED == wifiState;
        wifiMan.setWifiEnabled(enabled);
        return ret;
    }

    private static String getString(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[2048];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                int counter;
                while ((counter = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, counter);
                }
            } finally {
                inputStream.close();
            }
            String mac = writer.toString();
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(mac);
            mac = m.replaceAll("");
            return mac;
        } else {
            return "No Contents";
        }
    }

    public static byte[] formatMac(String mac) {
        byte[] macBytes = new byte[6];
        String[] macItems = mac.split(":");
        for(int i=0; i<macItems.length; i++) {
            macBytes[i] = (byte) (Integer.valueOf(macItems[i], 16) & 0xff);
        }
        return macBytes;
    }

    public static String formatMac(byte[] macBytes) {
        StringBuilder res1 = new StringBuilder();
        for (byte b : macBytes) {
            res1.append(String.format("%02X:",b));
        }

        if (res1.length() > 0) {
            res1.deleteCharAt(res1.length() - 1);
        }
        return res1.toString();
    }
}
