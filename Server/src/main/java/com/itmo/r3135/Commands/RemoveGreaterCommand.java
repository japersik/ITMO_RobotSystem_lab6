package com.itmo.r3135.Commands;


import com.google.gson.JsonSyntaxException;
import com.itmo.r3135.Collection;
import com.itmo.r3135.Mediator;
import com.itmo.r3135.System.Command;
import com.itmo.r3135.System.ServerMessage;
import com.itmo.r3135.World.Product;

import java.util.HashSet;

/**
 * Класс обработки комадны remove_greater
 */
public class RemoveGreaterCommand extends AbstractCommand {
    public RemoveGreaterCommand(Collection collection, Mediator serverWorker) {
        super(collection, serverWorker);
    }

    /**
     * Удаляет из коллекции все элементы, превышающие заданный.
     */
    @Override
    public ServerMessage activate(Command command) {
        HashSet<Product> products = collection.getProducts();
        try {
            int startSize = products.size();
            if (startSize != 0) {
                Product maxProduct = command.getProduct();
                products.removeIf(p -> (p != null && p.compareTo(maxProduct) > 0));
                return new ServerMessage("Удалено " + (startSize - products.size()) + " элементов");

            } else return new ServerMessage("Коллекция пуста.");
        } catch (JsonSyntaxException ex) {
            return new ServerMessage("Возникла ошибка синтаксиса Json.");
        }
    }
}
