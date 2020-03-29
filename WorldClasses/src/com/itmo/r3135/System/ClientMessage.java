package com.itmo.r3135.System;

import com.itmo.r3135.World.Product;

import java.io.Serializable;

public class ClientMessage implements Serializable {
    private CommandList command;
    private Product product;
    private String string;
    private int intValue;

    public ClientMessage(CommandList command) {
        this.command = command;
    }

    public ClientMessage(CommandList command, Product product) {
        this.command = command;
        this.product = product;
    }

    public ClientMessage(CommandList command, String string) {
        this.command = command;
        this.string = string;
    }

    public ClientMessage(CommandList command, int intValue) {
        this.command = command;
        this.intValue = intValue;
    }

    public CommandList getCommand() {
        return command;
    }

    public String getString() {
        return string;
    }
}
