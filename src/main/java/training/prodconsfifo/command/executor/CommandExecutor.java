package training.prodconsfifo.command.executor;

import training.prodconsfifo.command.Command;

public interface CommandExecutor<C extends Command> {
    void execute(C command);
}
