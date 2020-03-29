package com.itmo.r3135;

import com.itmo.r3135.System.ClientMessage;
import com.itmo.r3135.System.ServerMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class SendReciveManager {
    SocketAddress socketAddress;
    DatagramChannel datagramChannel;

    public SendReciveManager(SocketAddress socketAddress, DatagramChannel datagramChannel) {
        this.socketAddress = socketAddress;
        this.datagramChannel = datagramChannel;
    }

    public void send(ClientMessage message) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream =new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(message);
            byte[] bytearray = byteArrayOutputStream.toByteArray();
            objectOutputStream.close();
            ByteBuffer buffer = ByteBuffer.wrap(bytearray);
            datagramChannel.send(buffer, socketAddress);
            buffer.clear();
            System.out.println("Сообщение отправлено");
        } catch (IOException e) {
            System.out.println("IOException во время отправки");
        }
    }

    public ServerMessage recive() {
        //обраотка
        return new ServerMessage("Good connect");
    }
}
