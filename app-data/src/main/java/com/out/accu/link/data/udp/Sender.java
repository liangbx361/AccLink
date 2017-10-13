package com.out.accu.link.data.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import io.reactivex.Observable;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/10/1
 */

public class Sender {

    private InetAddress mInetAddress;
    private int mPort;

    public Sender(InetAddress inetAddress, int port) {
        mInetAddress = inetAddress;
        this.mPort = port;
    }

    public Observable<Boolean> send(byte[] data) {
        return Observable.just(data)
                .map(bytes -> {
                    DatagramSocket socket = new DatagramSocket(8001);
                    DatagramPacket packet = new DatagramPacket(data, data.length, mInetAddress, mPort);
                    socket.send(packet);
                    socket.close();
                    return true;
                });
    }
}
