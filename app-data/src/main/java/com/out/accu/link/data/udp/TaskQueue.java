package com.out.accu.link.data.udp;

import com.out.accu.link.data.mode.Response;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import io.reactivex.subjects.PublishSubject;

/**
 * <p>Title: 任务队列<／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/11/14
 */

public class TaskQueue {

    private static volatile TaskQueue sTaskQueue;

    private ConcurrentMap<String, PublishSubject<Response>> task = new ConcurrentHashMap<>();

    public static TaskQueue getInstance() {
        if(sTaskQueue == null) {
            synchronized (TaskQueue.class) {
                if(sTaskQueue == null) {
                    sTaskQueue = new TaskQueue();
                }
            }
        }

        return sTaskQueue;
    }

    public synchronized PublishSubject<Response> createTaskIfNotExit(byte[] cmd) {
        String key = getKey(cmd);
        if(!task.containsKey(key)) {
            PublishSubject<Response> publishSubject = PublishSubject.create();
            task.put(key, publishSubject);
            return publishSubject;
        } else {
            return getTask(cmd);
        }
    }

    public synchronized PublishSubject<Response> getTask(byte[] cmd) {
        String key = getKey(cmd);
        return task.get(key);
    }

    // 同时访问，导致线程死锁
    public synchronized String getKey(byte[] cmd) {
        StringBuilder keySb = new StringBuilder();
        keySb.append(cmd[0]);
        keySb.append(cmd[1]);
        return keySb.toString();
    }
}
