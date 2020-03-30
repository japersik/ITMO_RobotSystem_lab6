package com.itmo.r3135.Commands;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.itmo.r3135.Collection;
import com.itmo.r3135.Mediator;
import com.itmo.r3135.System.Command;
import com.itmo.r3135.System.ServerMessage;
import com.itmo.r3135.World.Product;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashSet;

/**
 * Класс загрузки коллекции
 */
public class LoadCollectionCommand extends AbstractCommand {
    public LoadCollectionCommand(Collection collection, Mediator serverWorker) {
        super(collection, serverWorker);
    }
    /**
     * Загружает коллекцию
     */
    @Override
    public ServerMessage activate(Command command) {

        Gson gson = new Gson();
        File jsonFile = collection.getJsonFile();
        HashSet<Product> products = collection.getProducts();
        int startSize = products.size();
        if (!jsonFile.exists()) {
            System.out.println(("Файл по указанному пути (" + jsonFile.getAbsolutePath() + ") не существует."));
            System.exit(666);
        }
        if (!jsonFile.canRead() || !jsonFile.canWrite()) {
            System.out.println("Файл защищён от чтения и(или) записи. Для работы коректной программы нужны оба разрешения.");
            System.exit(666);
        }
        if (jsonFile.length() == 0) {
            System.out.println("Файл пуст. Возможно только добавление элементов в коллекцию.");
            return new ServerMessage("Файл пуст. Возможно только добавление элементов в коллекцию.");
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonFile))) {
            System.out.println("Идет загрузка коллекции из файла " + jsonFile.getAbsolutePath());
            StringBuilder stringBuilder = new StringBuilder();
            String nextString;
            while ((nextString = bufferedReader.readLine()) != null) {
                stringBuilder.append(nextString);
            }
            Type typeOfCollectoin = new TypeToken<HashSet<Product>>() {
            }.getType();
            try {
                HashSet<Product> addedProduct = gson.fromJson(stringBuilder.toString(), typeOfCollectoin);
                for (Product p : addedProduct) {
                    if (p.checkNull()) {
                        throw new JsonSyntaxException("");
                    }
                    products.add(p);
                }
            } catch (JsonSyntaxException e) {
                System.out.println("Ошибка синтаксиса Json. Файл не может быть загружен.");
                System.exit(666);
            }catch (Exception e){
                System.out.println("Чувак, у тебя битый json, делай новый");
                System.exit(666);
            }
            System.out.println("Коллекций успешно загружена. Добавлено " + (products.size() - startSize) + " элементов.");
        } catch (IOException e) {
            System.out.println("При чтении строк возникла ошибка");
        }
        return null;
    }
}
