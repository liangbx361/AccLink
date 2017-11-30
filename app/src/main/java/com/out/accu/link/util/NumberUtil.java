package com.out.accu.link.util;

import java.text.NumberFormat;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/11/30
 */

public class NumberUtil {

    public static String formateInt(int value) {
        float fValue = value / 1000.0f;
        NumberFormat numberFormat= NumberFormat.getNumberInstance() ;
        numberFormat.setMaximumFractionDigits(3);
        return numberFormat.format(fValue);
    }

    public static int formateDouble(double value) {
        return (int) (value * 1000);
    }
}
