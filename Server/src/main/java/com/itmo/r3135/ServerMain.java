package com.itmo.r3135;

import java.io.IOException;
import java.net.*;
//Обязанности серверного приложения:
//Работа с файлом, хранящим коллекцию.
//Управление коллекцией объектов.
//Назначение автоматически генерируемых полей объектов в коллекции.
//Ожидание подключений и запросов от клиента.
//Обработка полученных запросов (команд).

// Серверное приложение должно состоять из следующих модулей (реализованных в виде одного или нескольких классов):
//Модуль приёма подключений. ------Хм.... непонятно
//Модуль чтения запроса. ------ Ну ок, Reader
//Модуль обработки полученных команд. ------Commander + commands-Package
//Модуль отправки ответов клиенту.
//Сервер должен работать в однпоточном режиме.


/**
 * @author daniil
 * @author vladislav
 */
public class ServerMain {
    private final static String FILENAME = "file.json";
    private static int port = 2622;
    static byte[] b = new byte[10];

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.out.println("При запуске не был указан рбочий порт. Укаютите его в аргументах камандной строки");
            System.exit(0);
        }
        try {
            if (Integer.valueOf(args[0]) < 0 || Integer.valueOf(args[0]) > 65535) {
                System.out.println("Порт - число от 0 до 65535.");
                System.exit(0);
            }
            port = Integer.valueOf(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка в формате записи числа.");
        }

        ServerManager serverManager = new ServerManager(port, FILENAME);
        serverManager.start();


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
