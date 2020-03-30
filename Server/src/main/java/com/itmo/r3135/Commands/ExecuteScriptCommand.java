package com.itmo.r3135.Commands;

import com.itmo.r3135.Collection;
import com.itmo.r3135.Mediator;
import com.itmo.r3135.System.Command;
import com.itmo.r3135.System.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Класс обработки комадны execute_script
 */
public class ExecuteScriptCommand extends AbstractCommand {
    public ExecuteScriptCommand(Collection collection, Mediator serverWorker) {
        super(collection, serverWorker);
    }
    /**
     * Выполняет скрипт записанный в файле.
     * В программе стоит ограничение на выполнение рекурсивных итераций в цикле - 20 вложенных циклов. Мы не рекомендуем вызывать скрипты в самом скрипте.

     */
    @Override
    public ServerMessage activate(Command command) {
        try {
            System.out.println("Начинается анализ скрипта. Это может занять некоторое время");
            VirtualStack virtualStack = new VirtualStack();
            ArrayList<String> executeCommands = virtualStack.stackGenerate(command.getString());
            if (!executeCommands.isEmpty()) {
                System.out.println("Запуск выполнения скрипта.");
                for (String executeCommand : executeCommands) {
                    //serverWorker.processing(exetuceCommand.StringToCommand);
                    //serverWorker.processing(command);
                }
                System.out.println("Скрипт выполнен.");
            } else {
                System.out.println("Невозможно прочитать скрипт или скрипт пуст.");
            }
        } catch (IOException e) {
            System.out.println("Ошибка работы с файлами.");
        }
    return null;
    }
}
