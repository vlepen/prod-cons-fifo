package training.prodconsfifo.command;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class AddCommand implements Command {
    Long userId;
    String userGuid;
    String userName;
}
