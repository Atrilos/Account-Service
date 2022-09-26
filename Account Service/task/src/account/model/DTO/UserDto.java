package account.model.DTO;

import account.model.Group;
import account.model.User;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link User} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "name", "lastname", "email", "roles"})
public class UserDto implements Serializable {
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private Set<String> roles;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getUsername();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.roles = user.getRoles().stream()
                .map(Group::getName)
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}