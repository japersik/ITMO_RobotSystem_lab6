package com.itmo.r3135.Commands;

import com.itmo.r3135.Collection;
import com.itmo.r3135.Mediator;
import com.itmo.r3135.System.Command;
import com.itmo.r3135.System.ServerMessage;
import com.itmo.r3135.World.Product;

import java.util.HashSet;

/**
 * Класс обработки комадны group_counting_by_coordinates
 */
public class GroupCountingByCoordinatesCommand extends AbstractCommand {

    public GroupCountingByCoordinatesCommand(Collection collection, Mediator serverWorker) {
        super(collection, serverWorker);
    }

    /**
     * Группирует элементы коллекции по кординатам на 4 четверти.
     */
    @Override
    public ServerMessage activate(Command command) {
        HashSet<Product> products = collection.getProducts();
        if (!products.isEmpty()) {
            for (int i = 1; i <= 4; i++) {
                System.out.println("Элементы " + i + " координатной четверти:");
                for (Product p : products) {
                    float x = p.getCoordinates().getX();
                    double y = p.getCoordinates().getY();
                    switch (i) {
                        case 1:
                            if (x >= 0 & y >= 0)
                                System.out.println(p);
                            break;
                        case 2:
                            if (x < 0 & y >= 0)
                                System.out.println(p);
                            break;
                        case 3:
                            if (x < 0 & y < 0)
                                System.out.println(p);
                            break;
                        case 4:
                            if (x >= 0 & y < 0)
                                System.out.println(p);
                            break;
                    }
                }
            }
        } else System.out.println("Коллекция пуста.");
        return null;
    }
}
