package training.prodconsfifo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import training.prodconsfifo.command.AddCommand;
import training.prodconsfifo.command.Command;
import training.prodconsfifo.command.DeleteAllCommand;
import training.prodconsfifo.command.PrintAllCommand;
import training.prodconsfifo.command.executor.CommandExecutor;
import training.prodconsfifo.command.executor.CommandExecutorsProvider;
import training.prodconsfifo.persistent.DBTemplate;
import training.prodconsfifo.persistent.UserRepository;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

public class ProdConsFIFOTest {
    private UserRepository userRepository;
    private Map<Class<? extends Command>, CommandExecutor> commandExecutors;
    private BlockingQueue<Command> queue;

    @BeforeEach
    void setup() {
        initDB();
        userRepository = new UserRepository();
        CommandExecutorsProvider commandExecutorsProvider = new CommandExecutorsProvider(userRepository);
        commandExecutors = commandExecutorsProvider.provide();
        queue = new LinkedBlockingQueue<>(100);
    }

    @Test
    public void shouldProduceAndConsume() {
       createAndStartConsumer();
       createAndStartConsumer();

        CommandProducer p1 = new CommandProducer(queue, asList(
            new AddCommand(1L, "a1", "Robert"),
            new AddCommand(2L, "a2", "Martin"),
            new PrintAllCommand()
        ));
        new Thread(p1).start();

        await()
            .pollDelay(500, MILLISECONDS)
            .atMost(5, SECONDS)
            .until(() -> userRepository.findAll().size() == 2);

        CommandProducer p2 = new CommandProducer(queue, asList(new DeleteAllCommand(), new PrintAllCommand()));
        new Thread(p2).start();

        await()
            .pollDelay(500, MILLISECONDS)
            .atMost(5, SECONDS)
            .until(() -> userRepository.findAll().isEmpty());
    }

    private void createAndStartConsumer(){
        new Thread(new CommandConsumer(queue, commandExecutors)).start();
    }

    private void initDB() {
        DBTemplate.executeUpdateStatement("CREATE TABLE SUSERS ("
            + " user_id int8 primary key, user_guid varchar(255) not null, user_name varchar (255) not null"
            + " )"
        );
    }
}