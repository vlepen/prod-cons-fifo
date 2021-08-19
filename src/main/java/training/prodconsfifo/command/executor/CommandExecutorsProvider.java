package training.prodconsfifo.command.executor;

import lombok.AllArgsConstructor;
import training.prodconsfifo.command.AddCommand;
import training.prodconsfifo.command.Command;
import training.prodconsfifo.command.DeleteAllCommand;
import training.prodconsfifo.command.PrintAllCommand;
import training.prodconsfifo.persistent.UserRepository;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class CommandExecutorsProvider {
    private final UserRepository userRepository;

    /**
     * Provide a collection of Command executors indexed by Command type
     * @return a collection of CommandExecutor
     */
    public Map<Class<? extends Command>, CommandExecutor> provide(){
        return new HashMap<Class<? extends Command>, CommandExecutor>() {{
            put(AddCommand.class, new AddCommandExecutor(userRepository));
            put(DeleteAllCommand.class, new DeleteAllCommandExecutor(userRepository));
            put(PrintAllCommand.class, new PrintAllCommandExecutor(userRepository));
        }};
    }
}
