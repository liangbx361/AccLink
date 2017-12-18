package com.out.accu.link.data.udp;

import com.out.accu.link.data.mode.Recode;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * <p>Title: 任务记录 <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/12/17
 */

public class TaskRecode {

    private static volatile TaskRecode sTaskRecode;
    // 自增ID
    private short mId = 0;
    // key = CMD + ID, value = byte[] request
    private Queue<Recode> mRecodes = new ConcurrentLinkedQueue<>();

    public static TaskRecode getInstance() {
        if(sTaskRecode == null) {
            synchronized (TaskRecode.class) {
                if(sTaskRecode == null) {
                    sTaskRecode = new TaskRecode();
                }
            }
        }

        return sTaskRecode;
    }

    public synchronized void addRecode(byte[] cmd, short id, byte[] reqData) {
        Recode recode = new Recode();
        recode.reqId = id;
        recode.cmd = cmd;
        recode.reqData = reqData;
        mRecodes.add(recode);
    }

    public synchronized Recode getRecode() {
        return mRecodes.peek();
    }

    public void removeRecode() {
        mRecodes.poll();
    }

    public synchronized short getId() {
        short id = mId;
        mId++;
        return id;
    }
}
