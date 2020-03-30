package com.itmo.r3135;

import com.itmo.r3135.System.Command;
import com.itmo.r3135.System.ServerMessage;

import java.io.*;
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

    public void send(Command message) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
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

    public ServerMessage recive() throws IOException {
        byte[] b = new byte[10000];
        ByteBuffer buffer = ByteBuffer.wrap(b);
        datagramChannel.receive(buffer);
        buffer.flip();
        System.out.println("Канал закрыт. ");
        System.out.println("Принято:");

        return fromSerial(b);
    }

    private ServerMessage fromSerial(byte[] b) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(
                    new ByteArrayInputStream(b));
            ServerMessage serverMessage = (ServerMessage) objectInputStream.readObject();
            objectInputStream.close();
            return serverMessage;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка десериализации.");
            return null;
        }
    }
}
