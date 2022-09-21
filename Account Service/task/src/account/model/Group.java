package account.model;

import account.model.constant.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = ConcurrentHashMap.newKeySet();
    @JsonIgnore
    private boolean isBusiness;
    @JsonIgnore
    private boolean isAdministrative;

    public Group(Role role) {
        name = "ROLE_" + role.name();
        if (role == Role.ADMINISTRATOR) {
            isBusiness = false;
            isAdministrative = true;
        } else {
            isBusiness = true;
            isAdministrative = false;
        }
    }
}