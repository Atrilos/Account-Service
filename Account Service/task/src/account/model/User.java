package account.model;

import lombok.Data;

@Data
public class User {
    private final String name;
    private final String lastname;
    private final String email;
}
