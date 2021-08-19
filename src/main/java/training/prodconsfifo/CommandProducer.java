package training.prodconsfifo;

import lombok.AllArgsConstructor;
import training.prodconsfifo.command.Command;

import java.util.List;
import java.util.concurrent.BlockingQueue;

@AllArgsConstructor
public class CommandProducer implements Runnable {
    private final BlockingQueue<Command> queue;
    private final List<Command> commands;

    public void run() {
        try {
            for (Command cmd : commands) {
                System.out.println("Producing command " + cmd);
                queue.put(cmd);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
