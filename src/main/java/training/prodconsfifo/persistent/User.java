package training.prodconsfifo.persistent;

import lombok.Value;

@Value
public class User {
    Long userId;
    String userGuid;
    String userName;
}
