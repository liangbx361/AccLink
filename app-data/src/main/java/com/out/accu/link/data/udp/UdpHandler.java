package com.out.accu.link.data.udp;

import com.out.accu.link.data.DataManager;
import com.out.accu.link.data.logger.AppLogger;
import com.out.accu.link.data.mode.LoginInfo;
import com.out.accu.link.data.mode.Recode;
import com.out.accu.link.data.mode.Response;
import com.out.accu.link.data.mode.ResponseCmd;
import com.out.accu.link.data.util.ByteUtil;
import com.out.accu.link.data.util.PacketUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/10/1
 */

public class UdpHandler {

    private static final int PORT = 8900;

    private InetAddress mInetAddress;
    private int mPort;

    private Disposable mSendDisposable;
    private Disposable mReceiveDisposable;
    private DatagramSocket mSocket;
    private long mIdelTime;

    public UdpHandler(InetAddress inetAddress, int port) {
        mInetAddress = inetAddress;
        this.mPort = port;
    }

    public boolean send(byte[] data) throws IOException {
        printLog("udp->send", data);
        DatagramPacket packet = new DatagramPacket(data, data.length, mInetAddress, mPort);
        getSocket().send(packet);
        return true;
    }

    public boolean sendResp(short respId) throws IOException {
        byte[] idBytes = ByteUtil.shortToByte(respId);
        byte[] respData = new byte[6];
        respData[0] = 0x06;
        respData[2] = idBytes[0];
        respData[3] = idBytes[1];
        respData[4] = 0x01;
        return send(respData);
    }

    public byte[] getEmptyData() {
        byte[] idBytes = ByteUtil.shortToByte(TaskRecode.getInstance().getId());
        byte[] emptyData = new byte[6];
        emptyData[0] = 0x06;
        emptyData[2] = idBytes[0];
        emptyData[3] = idBytes[1];
        return emptyData;
    }

    public byte[] receive() throws IOException {
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        // 阻塞
        getSocket().receive(packet);
        printLog("udp->receive", buf);
        return buf;
    }

    private synchronized DatagramSocket getSocket() {
        if(mSocket == null) {
            try {
                mSocket = new DatagramSocket(PORT);
                return mSocket;
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        return mSocket;
    }

    public void startSend() {
        mSendDisposable = Observable.interval(1, 1, TimeUnit.MILLISECONDS)
                .doOnNext(time -> {
                    try {
                        Recode recode = TaskRecode.getInstance().getRecode();

                        if(recode == null) {
                            // 60秒未发送数据，发送心跳包
                            if(System.currentTimeMillis() - mIdelTime > 1000 * 60) {
                                mIdelTime = System.currentTimeMillis();
                                send(getEmptyData());
                            }
                            return;
                        }

                        switch (recode.stats) {
                            case Recode.STATUS_WAIT:
                                send(recode.reqData);
                                recode.stats = Recode.STATUS_RESP;
                                recode.sendTime = System.currentTimeMillis();
                                mIdelTime = System.currentTimeMillis();
                                break;
                            case Recode.STATUS_RESP:
                                if(Recode.isTimeOut(recode.sendTime)) {
                                    recode.stats = Recode.STATUS_WAIT;
                                }
                                break;
                            case Recode.STATUS_RESP_DATA:
                                if(Recode.isTimeOut(recode.sendTime)) {
                                    recode.stats = Recode.STATUS_WAIT;
                                }
                                break;
                            case Recode.STATUS_FINISH:
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).subscribe();
    }

    /**
     * 启动数据接收
     */
    public void startReceive() {
        mReceiveDisposable = Observable.interval(1, 1, TimeUnit.MILLISECONDS)
                .doOnNext(time -> {
                    try {
                        byte[] buf = receive();
                        Response response = PacketUtil.parserPacket(buf);

                        Recode recode = TaskRecode.getInstance().getRecode();
                        // 判断是否属于响应包
                        short pktLength = ByteUtil.getShort(buf, 0);
                        if(pktLength == 6) {
                            // 响应包
                            if(recode != null && recode.stats == Recode.STATUS_RESP) {
                                if(response.id == recode.reqId) {
                                    // 收到响应包
                                    recode.stats = Recode.STATUS_RESP_DATA;
                                }
                            }
                        } else {
                            // 数据包, 判断是否为当前等待数据
                            if(recode != null) {
                                for(ResponseCmd responseCmd : response.mResponseCmds) {
                                    if (responseCmd.type == PacketUtil.TYPE_RESPONSE && responseCmd.cmd[0] == recode.cmd[0]
                                            && responseCmd.cmd[1] == recode.cmd[1]) {
                                        if (recode.stats == Recode.STATUS_RESP_DATA) {
                                            recode.respId = response.id;
                                            recode.stats = Recode.STATUS_FINISH;
                                            TaskRecode.getInstance().removeRecode();
                                            AppLogger.get().d("response", "cmd -->" + ByteUtil.getCmd(responseCmd.cmd[0],
                                                    responseCmd.cmd[1]) + " --> finish");
                                        }
                                    }

                                    if(!handleOffline(responseCmd)) {
                                        PublishSubject<ResponseCmd> publishSubject = TaskQueue.getInstance().getTask(responseCmd.cmd);
                                        publishSubject.onNext(responseCmd);
                                    }
                                }
                            } else {
                                for(ResponseCmd responseCmd : response.mResponseCmds) {
                                    if(!handleOffline(responseCmd)) {
                                        PublishSubject<ResponseCmd> publishSubject = TaskQueue.getInstance().getTask(responseCmd.cmd);
                                        publishSubject.onNext(responseCmd);
                                    }
                                }
                            }

                            // 发送响应包
                            sendResp(response.id);
                            mIdelTime = System.currentTimeMillis();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).subscribe();
    }

    private boolean handleOffline(ResponseCmd responseCmd) {
        if(responseCmd.code != 3) {
            return false;
        }

        // 离线状态重新发送登录请求
        LoginInfo loginInfo = DataManager.getInstance().getDataService().getLoginInfo();
        if(loginInfo == null) {
            return false;
        }

        DataManager.getInstance().getDataService().login(loginInfo);
        AppLogger.get().d("offline", "re login");

        return true;
    }

    /**
     * 结束数据接收
     */
    public void stop() {
        if(mSendDisposable != null) {
            mSendDisposable.dispose();
        }

        if (mReceiveDisposable != null) {
            mReceiveDisposable.dispose();
        }

        if(mSocket != null) {
            mSocket.close();
        }
    }

    private void printLog(String tag, byte[] datas) {
        if(AppLogger.get().isDebug()) {
            StringBuffer sb = new StringBuffer();
            int length = ByteUtil.getShort(datas, 0);
            for (int i = 0; i < length; i++) {
                String hex = Integer.toHexString(datas[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                sb.append("0x" + hex + "|");
            }

            AppLogger.get().d(tag, sb.toString());
        }
    }
}
