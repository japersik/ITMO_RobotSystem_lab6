package com.itmo.r3135;

import com.itmo.r3135.System.Command;
import com.itmo.r3135.System.CommandList;
import com.itmo.r3135.System.ServerMessage;
import com.itmo.r3135.World.Product;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;


public class ClientWorker {
    private SendReciveManager manager;
    private DatagramChannel datagramChannel = DatagramChannel.open();
    private SocketAddress socketAddress;
    private StringCommandManager stringCommandManager;

    {
        stringCommandManager = new StringCommandManager();
    }

    public ClientWorker(SocketAddress socketAddress) throws IOException {
        this.socketAddress = socketAddress;
        manager = new SendReciveManager(socketAddress, datagramChannel);
        datagramChannel.configureBlocking(false);
    }

    public void start() throws IOException, InterruptedException {
        String commandString = "";
        try (Scanner commandReader = new Scanner(System.in)) {
            System.out.print("//: ");
            while (!commandString.equals("exit")) {
                if (!commandReader.hasNextLine()) {
                    break;
                } else {
                    commandString = commandReader.nextLine();
                    Command command = stringCommandManager.getCommandFromString(commandString);
                    if (command != null)
                        if (this.connectionCheck()) {
                            manager.send(command);
                            ServerMessage message = manager.recive();
                            if (message != null) {
                                if (message.getMessage() != null)
                                    System.out.println(message.getMessage());
                                if (message.getProducts() != null)
                                    for (Product p : message.getProducts()) System.out.println(p);
                            } else System.out.println("Ответ cервера некорректен");
                        } else System.out.println("Подключение потеряно.");
                }
                System.out.print("//: ");
            }
        }
    }

    public boolean connectionCheck() throws IOException, InterruptedException {
        System.out.println("Проверка соединения:");
        datagramChannel.connect(socketAddress);
        datagramChannel.disconnect();
        datagramChannel.socket().setSoTimeout(1000);
        manager.send(new Command(CommandList.CHECK, "Привет"));
        ServerMessage recive = manager.recive();
        if (recive != null) {
            System.out.println(recive.getMessage());
            if (recive.getMessage().equals("Good connect. Hallo from server!")) {
                return true;
            } else return false;
        } else return false;
    }

}
