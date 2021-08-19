package training.prodconsfifo.command.executor;

import lombok.AllArgsConstructor;
import training.prodconsfifo.command.DeleteAllCommand;
import training.prodconsfifo.persistent.UserRepository;

@AllArgsConstructor
public class DeleteAllCommandExecutor implements CommandExecutor<DeleteAllCommand> {
    private final UserRepository userRepository;

    @Override
    public void execute(DeleteAllCommand command) {
        System.out.println("Executing commmand " + command);

        userRepository.deleteAll();
    }
}
