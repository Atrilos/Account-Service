package account.service;

import account.exception.RemoveUserException;
import account.model.DTO.ChangeAccessDto;
import account.model.DTO.ChangeRoleDto;
import account.model.DTO.UserDto;
import account.model.Group;
import account.model.User;
import account.model.constant.Action;
import account.model.constant.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static account.configuration.messages.AdminMessages.*;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserService userService;
    private final GroupService groupService;
    private final AuditService auditService;

    public UserDto changeRole(ChangeRoleDto changeRoleDTO) {
        User user = userService.loadUserByEmail(changeRoleDTO.getUser());
        Group group = groupService.getByName(changeRoleDTO.getRole().toString());
        switch (changeRoleDTO.getRoleOperation()) {
            case GRANT -> {
                return grantRole(user, group);
            }
            case REMOVE -> {
                return revokeRole(user, group);
            }
            default -> throw new IllegalStateException("Unexpected value: " + changeRoleDTO.getRoleOperation());
        }
    }

    private UserDto revokeRole(User user, Group group) {
        if (group.isAdministrative())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, REMOVE_ADMIN_ERRORMSG);
        if (!user.removeRole(group))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ROLE_DOESNT_EXIST_ERRORMSG);
        if (user.getRoles().size() == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, NO_ROLE_ERRORMSG);
        userService.save(user);
        auditService.addEvent(Action.REMOVE_ROLE,
                "Remove role %s from %s".formatted(group.getName().substring(5), user.getUsername()));
        return new UserDto(user);
    }

    private UserDto grantRole(User user, Group group) {
        user.addRole(group);
        checkRolesIntersection(user.getRoles().stream().map(Group::getName).toList());
        userService.save(user);
        auditService.addEvent(Action.GRANT_ROLE,
                "Grant role %s to %s".formatted(group.getName().substring(5), user.getUsername()));
        return new UserDto(user);
    }

    public Collection<UserDto> getAllUsers() {
        return userService.getAll();
    }

    public Map<String, String> removeUser(User admin, String email) {
        User user = userService.loadUserByEmail(email);
        if (admin.getId().equals(user.getId())) {
            throw new RemoveUserException(REMOVE_ADMIN_ERRORMSG);
        }
        userService.removeUser(user);
        auditService.addEvent(Action.DELETE_USER, user.getUsername());
        return Map.of("user", email,
                "status", SUCCESSFUL_REMOVAL_MSG);
    }

    public Map<String, String> changeAccess(ChangeAccessDto changeAccessDto) {
        User user = userService.loadUserByEmail(changeAccessDto.getUser());
        switch (changeAccessDto.getOperation()) {
            case LOCK -> userService.lockUser(user);
            case UNLOCK -> userService.unlockUser(user);
        }
        return Map.of("status", "User %s %s!".formatted(user.getUsername(), changeAccessDto.getOperation().name()));
    }

    private void checkRolesIntersection(List<String> roles) {
        if (roles.stream().anyMatch(r -> Role.getAdministrativeRoles().contains(r))
            && roles.stream().anyMatch(r -> Role.getBusinessRoles().contains(r)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, DIFFERENT_ROLE_TYPES_CONFLICT_ERRORMSG);
    }
}
