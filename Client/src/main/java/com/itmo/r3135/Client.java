package com.itmo.r3135;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Client {
    public static int port = 2622;
    static byte[] b = {1, 2, 3, 4, 5, 62, 3, 4, 3, 5};

    public static void main(String[] args) throws IOException {
        System.out.println("Запуск клиента....");
        SocketAddress addres = new InetSocketAddress("localhost", port);
        System.out.println("Запуск прошёл успешно, Потр: " + port + ". Адрес: " + addres);
        System.out.println("Открытае канала");
        DatagramChannel channel = DatagramChannel.open();
        //channel.socket().bind(addres);
       // channel.connect(addres);
        System.out.println("Канал открыт");
        System.out.println(addres);
        ByteBuffer buffer = ByteBuffer.wrap(b);
       // buffer.flip();
        System.out.println("Отправление");
        channel.send(buffer,addres);
        System.out.println("Отправлено");
        buffer.clear();
        channel.receive(buffer);
        buffer.flip();
        channel.close();
        System.out.println("Канал закрыт. ");
        System.out.println("Принято:");
        for (byte j : b) {
            System.out.println(j);
        }
    }
}
