package com.itmo.r3135;

import com.itmo.r3135.System.Command;
import com.itmo.r3135.System.CommandList;
import com.itmo.r3135.System.ServerMessage;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;


public class ClientWorker {
    private SendReciveManager manager;
    private DatagramChannel datagramChannel = DatagramChannel.open();

    public ClientWorker(SocketAddress socketAddress) throws IOException {
        manager = new SendReciveManager(socketAddress, datagramChannel);
    }

    public void connectionCheck() throws IOException {

        manager.send(new Command(CommandList.CHECK, "Привет"));
        ServerMessage recive = manager.recive();
        if (recive!=null){
            System.out.println(recive.getMessage());
        }
    }

}
