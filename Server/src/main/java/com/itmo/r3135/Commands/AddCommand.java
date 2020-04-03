package com.itmo.r3135.Commands;

import com.google.gson.JsonSyntaxException;
import com.itmo.r3135.Collection;
import com.itmo.r3135.Mediator;
import com.itmo.r3135.System.Command;
import com.itmo.r3135.System.ServerMessage;
import com.itmo.r3135.World.Product;

import java.util.HashSet;
import java.util.Random;

/**
 * Класс обработки комадны add
 */
public class AddCommand extends AbstractCommand {
    public AddCommand(Collection collection, Mediator serverWorker) {
        super(collection, serverWorker);
    }

    @Override
    public ServerMessage activate(Command command) {
        HashSet<Product> products = this.collection.getProducts();
        try {
            Product addProduct = command.getProduct();
            addProduct.setCreationDate(java.time.LocalDateTime.now());
            addProduct.setId(uniqueoIdGeneration(products));
            if (addProduct.checkNull()) {
                System.out.println("Элемент не удовлетворяет требованиям коллекции");
                return new ServerMessage(printRequest());
            } else if (products.add(addProduct)) {
                return new ServerMessage("Элемент успешно добавлен.");
            } else return new ServerMessage("Ошибка добавления элеемнта в коллекцию");
        } catch (JsonSyntaxException ex) {
            return new ServerMessage("Возникла ошибка синтаксиса Json. Элемент не был добавлен");
        }
    }

    private int uniqueoIdGeneration(HashSet<Product> products) {
        Random r = new Random();
        int newId;
        int counter;
        while (true) {
            counter = 0;
            newId = Math.abs(r.nextInt());
            for (Product product : products) {
                if (product.getId() == newId) {
                    break;
                } else counter++;
            }
            if (counter == products.size()) {
                return newId;
            }
        }
    }

    private String printRequest() {
        return ("------------------------\n" +
                "Требования к элементу:\n" +
                "------------------------\n" +
                "Product: {\n" +
                "    String name --- Поле не может быть null, Строка не может быть пустой\n" +
                "    Coordinates coordinates; --- Поле не может быть null\n" +
                "    Long price --- Поле может быть null, Значение поля должно быть больше 0\n" +
                "    String partNumber --- Длина строки должна быть не меньше 21, Поле не может быть null\n" +
                "    manufactureCost --- Поле не может быть null;\n" +
                "    UnitOfMeasure unitOfMeasure --- Поле не может быть null\n" +
                "    Person owner --- Поле не может быть null\n" +
                "}\n" +
                "Coordinates: {\n" +
                "    Float x --- Максимальное значение поля: 82, Поле не может быть null\n" +
                "    Long y --- Значение поля должно быть больше -244, Поле не может быть null\n" +
                "}\n" +
                "Person: {\n" +
                "    String name --- Поле не может быть null, Строка не может быть пустой\n" +
                "    java.time.LocalDateTime birthday --- Поле не может быть null\n" +
                "    Color eyeColor --- Поле не может быть null\n" +
                "    Color hairColor --- Поле не может быть null\n" +
                "}\n" +
                "UnitOfMeasure: {\n" +
                "    PCS,\n" +
                "    LITERS,\n" +
                "    GRAMS,\n" +
                "    MILLIGRAMS;\n" +
                "}\n" +
                "Color: {\n" +
                "    GREEN,\n" +
                "    RED,\n" +
                "    BLACK,\n" +
                "    BLUE,\n" +
                "    YELLOW;\n}");
    }

}
