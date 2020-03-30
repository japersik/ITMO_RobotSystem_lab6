
package com.itmo.r3135.Commands;

import com.itmo.r3135.Collection;
import com.itmo.r3135.Mediator;
import com.itmo.r3135.System.Command;
import com.itmo.r3135.System.ServerMessage;
import com.itmo.r3135.World.Product;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Класс обработки комадны print_field_descending_price
 */
public class PrintFieldDescendingPriceCommand extends AbstractCommand {

    public PrintFieldDescendingPriceCommand(Collection collection, Mediator serverWorker) {
        super(collection, serverWorker);
    }

    /**
     * Выводит коллекцию, отсортированную по цене в порядке убывания.
     */
    @Override
    public ServerMessage activate(Command command) {
        HashSet<Product> products = collection.getProducts();
        if (!products.isEmpty()) {
            ArrayList<Product> list = new ArrayList<>();
            for (Product p : products) {
                list.add(p);
            }
            list.sort((o1, o2) -> (int) ((o1.getPrice() - o2.getPrice()) * 100));
            System.out.printf("%-12s%5s%n", "ID", "Price");
            for (Product p : list) {
                System.out.printf("%-12d%5.2f%n", p.getId(), p.getPrice());
            }
        } else System.out.println("Коллекция пуста.");
        return null;
    }
}
