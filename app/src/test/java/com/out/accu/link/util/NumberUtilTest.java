package com.out.accu.link.util;

import com.out.accu.link.data.mode.DeviceHistory;

import org.junit.Test;

import java.util.Collections;

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
//        int value = 2200;
//        Assert.assertEquals(2.2d, NumberUtil.formateInt(value), 0.001);
//        value = 2000;
//        Assert.assertEquals(2d, NumberUtil.formateInt(value), 0.001);
//
//        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
//        System.out.println(df.format(2.2));
//
//        NumberFormat numberFormat= NumberFormat.getNumberInstance() ;
//        numberFormat.setMaximumFractionDigits(3);
//        System.out.println(numberFormat.format(2.2));
    }

    @Test
    public void formateDouble() throws Exception {
    }

    @Test
    public void compare() throws Exception {
        DeviceHistory deviceHistory = new DeviceHistory();
        DeviceHistory.Item item = new DeviceHistory.Item();
        item.time = 2;
        deviceHistory.list.add(item);
        item = new DeviceHistory.Item();
        item.time = 1;
        deviceHistory.list.add(item);

        Collections.sort(deviceHistory.list, (o1, o2) -> o1.time > o2.time ? 1 : -1);

        System.out.println(deviceHistory.list);
    }

}