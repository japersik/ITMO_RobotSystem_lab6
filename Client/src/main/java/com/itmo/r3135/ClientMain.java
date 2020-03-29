package com.itmo.r3135;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Scanner;
// Полезная статья про сериализацию: https://habr.com/ru/post/431524/
//Обязанности клиентского приложения:
//Чтение команд из консоли.
//Валидация вводимых данных.
//Сериализация введённой команды и её аргументов.
//Отправка полученной команды и её аргументов на сервер.
//Обработка ответа от сервера (вывод результата исполнения команды в консоль).

public class ClientMain {
    //   public static int port = 2622;
    //   static byte[] b = {1, 2, 3, 4, 5, 2, 3, 4, 3, 5};

    public static void main(String[] args) {


        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Для начала работы с коллекцией ведите адрес сервера в формате \"адрес:порт\" или 'exit' для завершенеия программы.");
            System.out.print("//: ");
            String inputString = input.nextLine();
            if (inputString.equals("exit")) {
                System.out.println("Работа программы завершена.");
                System.exit(0);
            } else {
                try {
                    String[] trimString = inputString.trim().split(":", 2);
                    String addres = trimString[0];
                    int port = Integer.valueOf(trimString[1]);
                    SocketAddress socketAddress = new InetSocketAddress(addres, port);
                    System.out.println("Запуск прошёл успешно, Потр: " + port + ". Адрес: " + socketAddress);
                    ClientWorker worker = new ClientWorker(socketAddress);
                    System.out.println("Проверка соединения:");
                    worker.connectionCheck();
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка в записи номера порта.");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Адрес введён некорректно.");
                }catch (IOException e){
                    System.out.println("Opps, IOException");
                }
            }
        }
    }/*

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
        channel.send(buffer, addres);
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
*/
}
