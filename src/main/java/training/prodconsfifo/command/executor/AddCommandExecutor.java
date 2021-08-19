package training.prodconsfifo.command.executor;

import lombok.AllArgsConstructor;
import training.prodconsfifo.command.AddCommand;
import training.prodconsfifo.persistent.UserRepository;

@AllArgsConstructor
public class AddCommandExecutor implements CommandExecutor<AddCommand> {
    private final UserRepository userRepository;

    @Override
    public void execute(AddCommand command) {
        System.out.println("Executing commmand " + command);

        userRepository.create(command.getUserId(), command.getUserGuid(), command.getUserName());
    }
}
