package com.itmo.r3135;

import com.itmo.r3135.System.ClientMessage;
import com.itmo.r3135.System.CommandList;

import java.io.IOException;
import java.net.*;

import java.nio.channels.DatagramChannel;


public class ClientWorker {
    private SendReciveManager manager;
    private DatagramChannel datagramChannel = DatagramChannel.open();

    public ClientWorker(SocketAddress socketAddress) throws IOException {
        manager = new SendReciveManager(socketAddress, datagramChannel);
    }

    public void connectionCheck() {

        manager.send(new ClientMessage(CommandList.CHECK,"Привет"));

    }
}
