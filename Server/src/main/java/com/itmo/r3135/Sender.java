package com.itmo.r3135;

import com.itmo.r3135.System.ServerMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Sender {
    private DatagramSocket socket;

    public Sender(DatagramSocket socket) {
        this.socket = socket;
    }

    public void send(ServerMessage serverMessage, DatagramPacket inputPacket) throws IOException {
        byte[] message = toSerial(serverMessage);
        InetAddress addres = inputPacket.getAddress();
        int outPort = inputPacket.getPort();
        System.out.println("Отправка");
        DatagramPacket output = new DatagramPacket(message, message.length, addres, outPort);
        socket.send(output);
        System.out.println("Опправлено.");
    }

    private byte[] toSerial(ServerMessage serverMessage) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(serverMessage);
        byte[] bytearray = byteArrayOutputStream.toByteArray();
        objectOutputStream.close();
        return bytearray;

    }

}
