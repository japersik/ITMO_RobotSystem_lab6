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
                System.out.println("Удалено " + (startSize - products.size()) + " элементов");

            } else System.out.println("Коллекция пуста.");
        } catch (JsonSyntaxException ex) {
            System.out.println("Возникла ошибка синтаксиса Json.");
        }
        return null;
    }
}
