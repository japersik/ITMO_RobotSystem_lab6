package com.itmo.r3135;

import java.io.IOException;
import java.net.BindException;
import java.util.Scanner;

/**
 * @author daniil
 * @author vladislav
 */
public class ServerMain {
    private final static String FILENAME = "file.json";

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        while (true) {

            System.out.println("Для начала работы сервера введите порт или 'exit' для завершенеия программы.");
            System.out.print("//: ");
            if (!input.hasNextLine()) {
                break;
            }
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
                        worker.startWork();
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка в записи номера порта.");
                } catch (BindException e) {
                    System.out.println("Этот порт уже занят.");
                }
            }
            System.out.println("Работа сервера заверщена.");
        }
    }
}
