package com.out.accu.link.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/11/21
 */

public class TimeUtil {

    public static String format(float time) {
        long timeL = (long) time;
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        return format.format(new Date(timeL));
    }
}
