package account.service;

import account.exception.UserNotFoundException;
import account.model.DTO.UserDto;
import account.model.Group;
import account.model.User;
import account.model.constant.Action;
import account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Optional;

import static account.configuration.messages.UserMessages.USER_NOT_FOUND_ERRORMSG;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private final UserRepository userRepository;
    private final AuditService auditService;
    private final HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_ERRORMSG));
    }

    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_ERRORMSG));
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Collection<UserDto> getAll() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(UserDto::new)
                .toList();
    }

    public void removeUser(User user) {
        userRepository.delete(user);
    }

    public void resetFailedLoginAttempts(User user) {
        userRepository.resetFailedLoginAttempts(user.getId());
    }

    public void registerFailedLoginAttempt(String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        if (foundUser.isEmpty()) {
            return;
        }
        User user = foundUser.get();
        if (user.addFailedLoginAttempt() >= MAX_FAILED_ATTEMPTS) {
            auditService.addEvent(Action.BRUTE_FORCE, request.getRequestURI());
            if (!isAdministrative(user)) {
                lockUser(user);
            }
        }
    }

    private static boolean isAdministrative(User user) {
        return user.getRoles().stream().anyMatch(Group::isAdministrative);
    }

    public void lockUser(User user) {
        if (isAdministrative(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Can't lock the ADMINISTRATOR!");
        } else if (!user.isAccountNonLocked()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User is already locked!");
        }
        user.setAccountNonLocked(false);
        userRepository.save(user);
        auditService.addEvent(Action.LOCK_USER, "Lock user " + user.getUsername());
    }

    public void unlockUser(User user) {
        if (user.isAccountNonLocked()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User is already unlocked!");
        }
        user.setAccountNonLocked(true);
        user.setFailedLoginAttempts(0);
        userRepository.save(user);
        auditService.addEvent(Action.UNLOCK_USER,
                "Unlock user %s".formatted(user.getUsername()));
    }
}
