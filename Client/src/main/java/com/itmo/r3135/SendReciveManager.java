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
            System.out.println(e);
        }
    }

    public ServerMessage recive() throws IOException, InterruptedException {
        byte[] b = new byte[10000];
        ByteBuffer buffer = ByteBuffer.wrap(b);
        SocketAddress from = null;
        for (int i = 0; i < 10; i++) {
            from = datagramChannel.receive(buffer);
            if (from != null) break;
            System.out.println("Попытка считать ответ" + i);
            Thread.sleep(1000);
        }
        buffer.flip();

        System.out.println("Принято:");
        if (from != null) {
            return fromSerial(b);
        }
        buffer.clear();
        System.out.println("Ответ не был получен!");
        return null;
    }

    private ServerMessage fromSerial(byte[] b) {
        try (
                ObjectInputStream objectInputStream = new ObjectInputStream(
                        new ByteArrayInputStream(b));
        ) {
            ServerMessage serverMessage = (ServerMessage) objectInputStream.readObject();
            objectInputStream.close();
            return serverMessage;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка десериализации.");
            System.out.println(e);
            return null;
        }
    }
}
