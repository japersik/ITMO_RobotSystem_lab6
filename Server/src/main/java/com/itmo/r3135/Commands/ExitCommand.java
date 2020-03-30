package com.itmo.r3135.Commands;

import com.itmo.r3135.Collection;
import com.itmo.r3135.Mediator;
import com.itmo.r3135.System.Command;
import com.itmo.r3135.System.ServerMessage;

/**
 * Класс обработки комадны help
 */
public class ExitCommand extends AbstractCommand {
    public ExitCommand(Collection collection, Mediator serverWorker) {
        super(collection, serverWorker);
    }

    /**
     * Закрывает программу без сохранения.
     */
    @Override
    public ServerMessage activate(Command command) {
        System.out.println("Работа программы завершена командой 'exit'");
        System.exit(0);
    return null;
    }
}
