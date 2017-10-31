package com.out.accu.link.data.udp;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

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

    private DatagramSocket getSocket() {
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

    public void startReceive() {
        mDisposable = Observable.interval(10, TimeUnit.MILLISECONDS)
                .doOnNext(time -> {
                    byte[] buf = new byte[1024];
                    // 解析数据包
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    mSocket.receive(packet);

                    String ip = packet.getAddress().getHostAddress();
                    buf = packet.getData();
                    String data = new String(buf, 0, packet.getLength());

                    System.out.println("收到 " + ip + " 发来的消息：" + data);
                })
                .subscribe(time -> {

                });
    }

    public void stop() {
        if (mDisposable != null) {
            mSocket.close();
            mDisposable.dispose();
        }
    }

    private void printLog(String tag, byte[] datas) {
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<datas.length; i++) {
            String hex = Integer.toHexString(datas[i] & 0xFF);
            if(hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append("0x"+hex+"|");
        }
        Log.d(tag, sb.toString());
    }
}
