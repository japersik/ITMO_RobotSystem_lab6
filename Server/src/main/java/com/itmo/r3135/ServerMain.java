package com.itmo.r3135;

import java.io.IOException;
import java.util.Scanner;
//Обязанности серверного приложения:
//Работа с файлом, хранящим коллекцию.
//Управление коллекцией объектов.
//Назначение автоматически генерируемых полей объектов в коллекции.
//Ожидание подключений и запросов от клиента.
//Обработка полученных запросов (команд).

// Серверное приложение должно состоять из следующих модулей (реализованных в виде одного или нескольких классов):
//Модуль приёма подключений. ------Worker
//Модуль чтения запроса. ------ Ну ок, Reader
//Модуль обработки полученных команд. ------ commands-Package
//Модуль отправки ответов клиенту. -------sender
//Сервер должен работать в однпоточном режиме.


/**
 * @author daniil
 * @author vladislav
 */
public class ServerMain {
    private final static String FILENAME = "file.json";

    public static void main(String[] args) throws IOException {
//        System.out.println("Инициализация сервера:");
//        DatagramSocket socket = new DatagramSocket(port);
//        System.out.println("Запуск прошёл успешно, Потр: " + port);
//        DatagramPacket input = new DatagramPacket(b, b.length);
//        while (true) {
//            try {
//                socket.receive(input);
//                ObjectInputStream objectInputStream = new ObjectInputStream(
//                        new ByteArrayInputStream(b));
//                Command command = (Command) objectInputStream.readObject();
//                objectInputStream.close();
//                System.out.println("Принято:");
//                System.out.println(command.getCommand());
//                System.out.println(command.getString());
//                Thread.sleep(1000);
//            } catch (ClassNotFoundException | InterruptedException e) {
//                System.out.println("Ошибка сериализации");
//            }
//        }
        //Это раюотает,нопока закомментировано для проверки связи
        Scanner input = new Scanner(System.in);
        while (true) {

            System.out.println("Для начала работы сервера введите порт или 'exit' для завершенеия программы.");
            System.out.print("//: ");

            String inputString = input.nextLine();
            if (inputString.equals("exit")) {
                System.out.println("Работа программы завершена.");
                System.exit(0);

            } else {
                try {
                    int port = Integer.valueOf(inputString);
                    if (port < 0 || port > 65535) {
                        System.out.println("Порт - число от 0 до 65535.");
                    } else {
                        ServerWorker worker = new ServerWorker(port, FILENAME);
                        worker.start();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка в записи номера порта.");
                }
            }
        }

//Так должен работать приём - передача на сервере
/*
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
    */
    }
}
