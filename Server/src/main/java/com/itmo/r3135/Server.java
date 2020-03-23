package com.itmo.r3135;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @author daniil
 * @author vladislav
 */
public class Server {
    private static int port = 2622;
    static byte[] b = new byte[10];

    public static void main(String[] args) throws IOException {
        System.out.println("Инициализация сервера:");
        DatagramSocket socket = new DatagramSocket(port);
        System.out.println("Запуск прошёл успешно, Потр: " + port);
        DatagramPacket input = new DatagramPacket(b, b.length);
        while (true) {
            System.out.println("Ожидание");
            socket.receive(input);
            System.out.println(input);
            System.out.println("Принято:");
            for (byte j : b) {
                System.out.println(j);
            }
            System.out.println("Обработка (умножение на 2)");
            for (int ki = 0; ki < 10; ki++) {
                b[ki] *= 2;
            }
            InetAddress addres = input.getAddress();
            int outPort = input.getPort();
            System.out.println("Отправка");
            DatagramPacket output = new DatagramPacket(b, b.length, addres, outPort);
            socket.send(output);
            System.out.println("Опправлено.");
        }
    }
}
