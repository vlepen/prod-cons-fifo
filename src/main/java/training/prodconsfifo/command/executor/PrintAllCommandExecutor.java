package training.prodconsfifo.command.executor;

import lombok.AllArgsConstructor;
import training.prodconsfifo.command.PrintAllCommand;
import training.prodconsfifo.persistent.UserRepository;

@AllArgsConstructor
public class PrintAllCommandExecutor implements CommandExecutor<PrintAllCommand> {
    private final UserRepository userRepository;

    @Override
    public void execute(PrintAllCommand command) {
        System.out.println("Executing commmand " + command);

        userRepository.findAll().forEach(System.out::println);
    }
}
