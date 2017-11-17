package com.out.accu.link.data.udp;

import com.out.accu.link.data.mode.Response;

import java.util.HashMap;
import java.util.Map;

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

    private Map<String, PublishSubject<Response>> task = new HashMap<>();

    public static TaskQueue getInstance() {
        if(sTaskQueue == null) {
            sTaskQueue = new TaskQueue();
        }

        return sTaskQueue;
    }

    public PublishSubject<Response> createTaskIfNotExit(byte[] cmd) {
        String key = getKey(cmd);
        if(!task.containsKey(key)) {
            PublishSubject<Response> publishSubject = PublishSubject.create();
            task.put(key, publishSubject);
            return publishSubject;
        } else {
            return getTask(cmd);
        }
    }

    public PublishSubject<Response> getTask(byte[] cmd) {
        String key = getKey(cmd);
        return task.get(key);
    }

    private String getKey(byte[] cmd) {
        StringBuilder keySb = new StringBuilder();
        keySb.append(cmd[0]);
        keySb.append(cmd[1]);
        return keySb.toString();
    }
}
