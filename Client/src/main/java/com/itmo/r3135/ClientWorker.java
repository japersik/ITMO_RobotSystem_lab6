package com.itmo.r3135;

import com.itmo.r3135.System.Command;
import com.itmo.r3135.System.CommandList;
import com.itmo.r3135.System.ServerMessage;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.channels.DatagramChannel;


public class ClientWorker {
    private SendReciveManager manager;
    private DatagramChannel datagramChannel = DatagramChannel.open();
    private SocketAddress socketAddress;

    public ClientWorker(SocketAddress socketAddress) throws IOException {
        this.socketAddress = socketAddress;
        manager = new SendReciveManager(socketAddress, datagramChannel);
        //    datagramChannel.socket().bind(new InetSocketAddress(10000));
        datagramChannel.configureBlocking(false);

    }

    public void start() throws IOException, InterruptedException {
        while (true) {
            Thread.sleep(1000);
            this.connectionCheck();
        }
    }

    public boolean connectionCheck() throws IOException, InterruptedException {
        try {
            datagramChannel.connect(socketAddress);
            datagramChannel.disconnect();
             datagramChannel.socket().setSoTimeout(1000);
            // InetAddress llil= datagramChannel.socket().getInetAddress();
            //  datagramChannel.socket().connect(llil,1000);
            manager.send(new Command(CommandList.CHECK, "Привет"));
            ServerMessage recive = manager.recive();
            if (recive != null) {
                System.out.println(recive.getMessage());
                if (recive.getMessage().equals("Good connect. Hallo from server!")) {
                    return true;
                } else return false;
            }
            else return false;
        } catch (SocketTimeoutException e) {
            System.out.println("rfr");
            return false;
        }
    }

}
