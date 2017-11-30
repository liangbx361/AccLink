package com.out.accu.link.util;

import org.junit.Assert;
import org.junit.Test;

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
public class NumberUtilTest {
    @Test
    public void formateInt() throws Exception {
        int value = 2200;
        Assert.assertEquals(2.2d, NumberUtil.formateInt(value), 0.001);
        value = 2000;
        Assert.assertEquals(2d, NumberUtil.formateInt(value), 0.001);

        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        System.out.println(df.format(2.2));

        NumberFormat numberFormat= NumberFormat.getNumberInstance() ;
        numberFormat.setMaximumFractionDigits(3);
        System.out.println(numberFormat.format(2.2));
    }

    @Test
    public void formateDouble() throws Exception {
    }

}