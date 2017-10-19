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
public class RemoteServiceTest {

    DataService mService;

    @Before
    public void setUp() throws Exception {
        DataManager.init(Platform.TEST);
        mService = DataManager.getInstance().getDataService();
    }

    @Test
    public void login() throws Exception {
        mService.login("user1", "123456")
                .subscribe(login -> {
                    System.out.println("result -->" + login.isSuccess);
                });
    }

}