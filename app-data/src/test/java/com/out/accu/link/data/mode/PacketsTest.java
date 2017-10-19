package com.out.accu.link.data.mode;

import com.out.accu.link.data.DataManager;
import com.out.accu.link.data.DataService;
import com.out.accu.link.data.config.Platform;
import com.out.accu.link.data.util.PacketUtil;

import org.junit.Before;
import org.junit.Test;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/10/13
 */
public class PacketsTest {

    private DataService mDataService;

    @Before
    public void setUp() throws Exception {
        DataManager.init(Platform.TEST);
        mDataService = DataManager.getInstance().getDataService();
    }

    @Test
    public void getPacket() throws Exception {
        PacketUtil packets = new PacketUtil();
        String userName = "xiaoming";
//        packets.getPacket(Packets.CMD_LOGIN, Packets.TYPE_REQUEST, )
    }

}