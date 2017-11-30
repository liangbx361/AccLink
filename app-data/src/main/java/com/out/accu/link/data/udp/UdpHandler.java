package com.out.accu.link.data.udp;

import android.util.Log;

import com.out.accu.link.data.logger.AppLogger;
import com.out.accu.link.data.mode.Response;
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

    private Disposable mDisposable;
    private DatagramSocket mSocket;

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

    /**
     * 启动数据接收
     */
    public void startReceive() {
        mDisposable = Observable.interval(1, 1, TimeUnit.MILLISECONDS)
                .map(time -> {
                    try {
                        byte[] buf = receive();
                        Response response = PacketUtil.parserPacket(buf);
                        PublishSubject<Response> publishSubject = TaskQueue.getInstance().getTask(response.cmd);
                        if (response.isSuccess()) {
                            publishSubject.onNext(response);
                        } else {
                            publishSubject.onError(new Exception());
                        }
                        return response;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return new Response();
                }).subscribe();
    }

    /**
     * 结束数据接收
     */
    public void stopReceive() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }

        if(mSocket != null) {
            mSocket.close();
        }
    }

    private void printLog(String tag, byte[] datas) {
        if(AppLogger.get().isDebug()) {
            StringBuffer sb = new StringBuffer();
            int length = datas[2] + (datas[3] << 8);
            for (int i = 0; i < length; i++) {
                String hex = Integer.toHexString(datas[i + 6] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                sb.append("0x" + hex + "|");
            }
            Log.d(tag, sb.toString());
        }
    }
}
