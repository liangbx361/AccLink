package com.out.accu.link.data.remote;

import com.out.accu.link.data.DataManager;
import com.out.accu.link.data.DataService;
import com.out.accu.link.data.config.Platform;

import org.junit.Before;
import org.junit.Test;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/10/14
 */
//@RunWith(RobolectricTestRunner.class)
//@Config(constants = BuildConfig.class, sdk = 21)
public class RemoteServiceTest {

//    @Rule
//    public ImmediateSchedulersRule mRule = new ImmediateSchedulersRule();

    DataService mService;

    @Before
    public void setUp() throws Exception {
        DataManager.init(Platform.TEST);
        DataManager.getInstance().getUdpHandler().startReceive();
        mService = DataManager.getInstance().getDataService();
    }

    @Test
    public void login() throws Exception {
//        mService.login("user1", "123456");
//
//        Thread.sleep(1000);
//
//        mService.getDevices();
//
//        while (true) {
//
//        }
    }

}