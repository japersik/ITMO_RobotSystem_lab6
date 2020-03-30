package com.itmo.r3135.Commands;

import com.google.gson.Gson;
import com.itmo.r3135.Collection;
import com.itmo.r3135.Mediator;
import com.itmo.r3135.System.Command;
import com.itmo.r3135.System.ServerMessage;
import com.itmo.r3135.World.Product;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

/**
 * Класс обработки комадны save
 */
public class SaveCommand extends AbstractCommand {
    public SaveCommand(Collection collection, Mediator serverWorker) {
        super(collection, serverWorker);
    }

    /**
     * Сохраняет все изменения коллекции в открытый файл.
     */
    @Override
    public ServerMessage activate(Command command) {
        HashSet<Product> products = collection.getProducts();
        File jsonFile = collection.getJsonFile();
        Gson gson = new Gson();
        try {
            if (!jsonFile.exists()) {
                System.out.println(("Невозможно сохранить файл. Файл по указанному пути (" + jsonFile.getAbsolutePath() + ") не существует."));
            } else if (!jsonFile.canRead() || !jsonFile.canWrite()) {
                System.out.println("Невозможно сохранить файл. Файл защищён от чтения и(или) записи.");
            } else {
                FileWriter fileWriter = new FileWriter(jsonFile);
                try {
                    fileWriter.write(gson.toJson(products));
                    fileWriter.flush();
                    System.out.println("Файл успешно сохранён.");
                } catch (Exception ex) {
                    System.out.println("При записи файла что-то пошло не так.");
                } finally {
                    fileWriter.close();
                }
            }
            collection.updateDateSave();
        } catch (IOException e) {
            System.out.println("Возникла ошибка работы с файлом");
        }
        return null;
    }
}
