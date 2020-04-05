package com.itmo.r3135;

import com.google.gson.Gson;
import com.itmo.r3135.Commands.*;
import com.itmo.r3135.System.Command;
import com.itmo.r3135.System.CommandList;
import com.itmo.r3135.System.ServerMessage;

import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;


public class ServerWorker implements Mediator {
    private int port;
    private DatagramSocket socket;
    private Gson gson;
    private Collection collection;
    private Sender sender;
    private Reader reader;

    private AbstractCommand loadCollectionCommand;
    private AbstractCommand addCommand;
    private AbstractCommand showCommand;
    private AbstractCommand updeteIdCommand;
    private AbstractCommand helpCommand;
    private AbstractCommand removeByIdCommand;
    private AbstractCommand groupCountingByCoordinatesCommand;
    private AbstractCommand addIfMinCommand;
    private AbstractCommand clearCommand;
    private AbstractCommand printFieldDescendingPriceCommand;
    private AbstractCommand filterContainsNameCommand;
    private AbstractCommand removeLowerCommand;
    private AbstractCommand removeGreaterCommand;
    private AbstractCommand executeScriptCommand;

    private AbstractCommand saveCommand;
    private AbstractCommand exitCommand;
    private boolean workingReady;
    private static final Semaphore SEMAPHORE = new Semaphore(1, true);

    {
        gson = new Gson();
        collection = new Collection();
        addCommand = new AddCommand(collection, this);
        showCommand = new ShowCommand(collection, this);
        updeteIdCommand = new UpdeteIdCommand(collection, this);
        helpCommand = new HelpCommand(collection, this);
        removeByIdCommand = new RemoveByIdCommand(collection, this);
        groupCountingByCoordinatesCommand = new GroupCountingByCoordinatesCommand(collection, this);
        addIfMinCommand = new AddIfMinCommand(collection, this);
        loadCollectionCommand = new LoadCollectionCommand(collection, this);
        clearCommand = new ClearCommand(collection, this);
        printFieldDescendingPriceCommand = new PrintFieldDescendingPriceCommand(collection, this);
        filterContainsNameCommand = new FilterContainsNameCommand(collection, this);
        removeLowerCommand = new RemoveLowerCommand(collection, this);
        removeGreaterCommand = new RemoveGreaterCommand(collection, this);
        executeScriptCommand = new ExecuteScriptCommand(collection, this);
        saveCommand = new SaveCommand(collection, this);
        exitCommand = new ExitCommand(collection, this);
    }

    public ServerWorker(int port, String fileName) {
        this.port = port;
        if (fileName == null) {
            System.out.println("Путь к файлу json не обнаружен.");
            System.exit(1);
        }
        File jsonPath = new File(fileName);
        if (jsonPath.exists()) {
            collection.setJsonFile(jsonPath);
            System.out.println("Адрес " + this.collection.getJsonFile().toString() + " успешно обнаружен");
        } else {
            System.out.println("Указанного пути не существует.");
            System.exit(1);
        }
        if (!jsonPath.isFile()) {
            System.out.println("Путь " + jsonPath.toString() + " не содержит имени файла");
            System.exit(1);
        } else {
            System.out.println("Файл " + jsonPath.toString() + " успещно обнаружен.");
        }
        if (!(fileName.lastIndexOf(".json") == fileName.length() - 5)) {
            System.out.println("Заданный файл не в формате .json");
            System.exit(1);
        }


    }

//    public void startWork() throws SocketException {
//        System.out.println("Инициализация сервера.");
//        socket = new DatagramSocket(port);
//        sender = new Sender(socket);
//        reader = new Reader(socket);
//        System.out.println("Загрузка коллекции.");
//        loadCollectionCommand.activate(new Command(CommandList.LOAD));
//        System.out.println("Запуск прошёл успешно, Потр: " + port);
//        Thread lal = new Thread(this);
//        lal.setDaemon(true);
//        lal.start();
//        Scanner input = new Scanner(System.in);
//        while (true) {
////            try {
////            if (input.hasNextLine())
//                System.out.println(input.nextLine());
//            else break;
//                Command command = reader.nextCommand();
//                System.out.println("Принято:");
//                System.out.println(command.getCommand());
//                System.out.println(command.getString());
//                sender.send(processing(command), reader.getInput());
//                Thread.sleep(3000);
//            } catch (IOException | InterruptedException e) {
//                System.out.println("Ошибка сериализации");
//        }
//        }
//
//    }

    public void startWork() throws SocketException {

        System.out.println("Инициализация сервера.");
        socket = new DatagramSocket(port);
        sender = new Sender(socket);
        reader = new Reader(socket);
        System.out.println("Загрузка коллекции.");
        loadCollectionCommand.activate(new Command(CommandList.LOAD));
        System.out.println("Запуск прошёл успешно, Потр: " + port);
        workingReady = true;
        Thread keyBoard = new Thread(() -> keyBoardWork());
        Thread datagramm = new Thread(() -> datagrammWork());
        keyBoard.setDaemon(false);
        datagramm.setDaemon(true);
        keyBoard.start();
        datagramm.start();

    }

    public void keyBoardWork() {
        try (Scanner input = new Scanner(System.in);) {
            while (true) {
                System.out.println("//:");
                if (input.hasNextLine()) {
                    String inputString = input.nextLine();
                    SEMAPHORE.acquire();
                    switch (inputString) {
                        case "exit":
                            processing(new Command(CommandList.SAVE));
                            processing(new Command(CommandList.EXIT));
                            break;
                        case "save":
                            processing(new Command(CommandList.SAVE));
                            break;
                        default:
                            System.out.println("Доступные команды сервера: save, exit.");
                    }
                    SEMAPHORE.release();
                } else processing(new Command(CommandList.EXIT));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void datagrammWork() {
        while (true) {
            try {
                Command command = reader.nextCommand();
                SEMAPHORE.acquire();
                System.out.println("Принято:");
                System.out.println(command.getCommand());
                System.out.println(command.getString());
                sender.send(processing(command), reader.getInput());
                Thread.sleep(3000);
                SEMAPHORE.release();

            } catch (IOException | InterruptedException e) {
                System.out.println("Ошибка сериализации");
            }
        }
    }

    @Override
    public ServerMessage processing(Command command) {
        try {
            try {
                switch (command.getCommand()) {
                    case CHECK:
                        System.out.println("Попытка соединиться");
                        return new ServerMessage("Good connect. Hallo from server!");
                    case HELP:
                        return helpCommand.activate(command);
                    case INFO:
                        //    info();
                        break;
                    case SHOW:
                        return showCommand.activate(command);
                    case ADD:
                        return addCommand.activate(command);
                    //      dateChange = new Date();
                    case UPDATE:
                        return updeteIdCommand.activate(command);
                    //    dateChange = new Date();
                    case REMOVE_BY_ID:
                        return removeByIdCommand.activate(command);
                    //    dateChange = new Date();
                    case CLEAR:
                        return clearCommand.activate(command);
                    //    dateChange = new Date();
//                    case "save":
//                        saveCommand.activate();
//                        break;
                    case EXECUTE_SCRIPT:
                        return executeScriptCommand.activate(command);
//                    case "exit":
//                        exitCommand.activate();
//                        break;
                    case ADD_IF_MIN:
                        return addIfMinCommand.activate(command);
                    //      dateChange = new Date();
                    case REMOVE_GREATER:
                        return removeGreaterCommand.activate(command);
                    //     dateChange = new Date();
                    case REMOVE_LOWER:
                        return removeLowerCommand.activate(command);
                    //      dateChange = new Date();
                    case GROUP_COUNTING_BY_COORDINATES:
                        return groupCountingByCoordinatesCommand.activate(command);
                    case FILTER_CONTAINS_NAME:
                        return filterContainsNameCommand.activate(command);
                    case PRINT_FIELD_DESCENDING_PRICE:
                        return printFieldDescendingPriceCommand.activate(command);
                    case SAVE:
                        return saveCommand.activate(command);
                    case EXIT:
                        return exitCommand.activate(command);
                    default:
                        System.out.println("Неопознанная команда.");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Где-то проблема с форматом записи числа.Команда не выполнена");
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Отсутствует аргумент.");
        }
        return null;
    }
}
