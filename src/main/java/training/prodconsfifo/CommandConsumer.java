package training.prodconsfifo;

import lombok.AllArgsConstructor;
import training.prodconsfifo.command.Command;
import training.prodconsfifo.command.executor.CommandExecutor;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

@AllArgsConstructor
public class CommandConsumer implements Runnable {
    private final BlockingQueue<Command> queue;
    private final Map<Class<? extends Command>, CommandExecutor> commandExecutors;

    public void run() {
        try {
            while (true) {
                consume(queue.take());
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    void consume(Command command) {
        System.out.println(String.format("%s consuming %s", this, command));

        commandExecutors.get(command.getClass()).execute(command);
    }
}
