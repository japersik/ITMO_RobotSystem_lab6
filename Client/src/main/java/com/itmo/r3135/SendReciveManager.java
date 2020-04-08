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
            ByteBuffer buffer = ByteBuffer.wrap(bytearray);
            datagramChannel.send(buffer, socketAddress);
            //  buffer.clear();
            objectOutputStream.close();
            System.out.println("Сообщение " + message.getCommand() + " отправлено");
        } catch (IOException e) {
            System.out.println("IOException во время отправки");
            System.out.println(e);
        }
    }

    public ServerMessage recive() throws IOException, InterruptedException {
        byte[] b = new byte[10000];
        ByteBuffer buffer = ByteBuffer.wrap(b);
        SocketAddress from = null;
        Thread.sleep(5);
        for (int i = 0; i < 1000; i++) {
            if (i % 200 == 0) System.out.println("Попытка считать ответ № " + (i / 200 + 1));
            from = datagramChannel.receive(buffer);
            if (from != null) break;
            Thread.sleep(10);
        }
//        buffer.flip();

        if (from != null) {
            System.out.println("Ответ принят.");
            return fromSerial(b);
        }
//            buffer.clear();
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
