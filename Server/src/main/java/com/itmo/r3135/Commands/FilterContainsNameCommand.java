package com.itmo.r3135.Commands;

import com.itmo.r3135.Collection;
import com.itmo.r3135.Mediator;
import com.itmo.r3135.System.Command;
import com.itmo.r3135.System.ServerMessage;
import com.itmo.r3135.World.Product;

import java.util.HashSet;

/**
 * Класс обработки комадны filter_contains_name
 */
public class FilterContainsNameCommand extends AbstractCommand {
    public FilterContainsNameCommand(Collection collection, Mediator serverWorker) {
        super(collection,serverWorker);
    }

    /**
     * Выводит элементы, значение поля name которых содержит заданную подстроку.
     *
     */
    @Override
    public ServerMessage activate(Command command) {
        HashSet<Product> products = collection.getProducts();
        int findProdukts = 0;
        if (products.size() > 0) {
            if (!command.getString().isEmpty() && command.getString() != null) {
                for (Product p : products) {
                    if (p.getName().contains(command.getString())) {
                        System.out.println(p);
                        findProdukts++;
                    }
                }
                System.out.println("Всего найдено " + findProdukts + " элементов.");
            } else System.out.println("Ошибка ввода имени.");
        } else System.out.println("Коллекция пуста.");
        return null;
    }
}
