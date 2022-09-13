package account.model.DTO;

import account.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreatedRequest {
    private Long id;
    private String name;
    private String lastname;
    private String email;

    public UserCreatedRequest(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.email = user.getUsername();
    }
}
