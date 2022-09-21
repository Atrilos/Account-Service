package account.service;

import account.exception.RemoveUserException;
import account.model.DTO.ChangeRoleDto;
import account.model.DTO.UserDto;
import account.model.Group;
import account.model.User;
import account.model.constant.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserService userService;
    private final GroupService groupService;

    public UserDto changeRole(ChangeRoleDto changeRoleDTO) {
        User user = userService.loadUserByEmail(changeRoleDTO.getUser());
        Group group = groupService.getByName("ROLE_" + changeRoleDTO.getRole().name());
        switch (changeRoleDTO.getOperation()) {
            case GRANT -> {
                return grantRole(user, group);
            }
            case REMOVE -> {
                return revokeRole(user, group);
            }
            default -> throw new IllegalStateException("Unexpected value: " + changeRoleDTO.getOperation());
        }
    }

    private UserDto revokeRole(User user, Group group) {
        if (group.isAdministrative())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't remove ADMINISTRATOR role!");
        if (!user.removeRole(group))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user does not have a role!");
        if (user.getRoles().size() == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user must have at least one role!");
        userService.save(user);
        return new UserDto(user);
    }

    private UserDto grantRole(User user, Group group) {
        user.addRole(group);
        checkRolesIntersection(user.getRoles().stream().map(Group::getName).toList());
        userService.save(user);
        return new UserDto(user);
    }

    public Collection<UserDto> getAllUsers() {
        return userService.getAll();
    }

    public Map<String, String> removeUser(User admin, String email) {
        User user = userService.loadUserByEmail(email);
        if (admin.getId().equals(user.getId())) {
            throw new RemoveUserException("Can't remove ADMINISTRATOR role!");
        }
        userService.removeUser(user);
        return Map.of("user", email,
                "status", "Deleted successfully!");
    }

    private void checkRolesIntersection(List<String> roles) {
        if (roles.stream().anyMatch(r -> Role.getAdministrativeRoles().contains(r))
            && roles.stream().anyMatch(r -> Role.getBusinessRoles().contains(r)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user cannot combine administrative and business roles!");
    }
}
