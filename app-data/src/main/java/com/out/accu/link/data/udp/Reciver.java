package com.out.accu.link.data.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.TimeUnit;

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

public class Reciver {

    public void reciver() {
        try {
            DatagramSocket socket = new DatagramSocket(8001);
            Observable.interval(500, TimeUnit.MILLISECONDS)
                    .doOnNext(time -> {
                        byte[] buf = new byte[1024];
                        DatagramPacket packet = new DatagramPacket(buf, buf.length);
                        socket.receive(packet);
                        packet.getData();
                        String ip = socket.getInetAddress().getHostAddress();
                        String data = new String();
                    });
        } catch (SocketException e) {
            e.printStackTrace();
        }


    }
}
