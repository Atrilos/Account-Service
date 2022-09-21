package account.service;

import account.exception.UserExistException;
import account.model.DTO.*;
import account.model.Group;
import account.model.User;
import account.model.constant.Role;
import account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static account.configuration.messages.AuthMessages.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final GroupService groupService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PasswordBreachValidatorService passwordBreachValidator;

    public UserDto signup(NewUserDto newUser) {
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new UserExistException("User exist!");
        }
        passwordBreachValidator.validate(newUser.getPassword());
        User addedUser = new User().toBuilder()
                .name(newUser.getName())
                .lastname(newUser.getLastname())
                .email(newUser.getEmail().toLowerCase())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .build();
        Group group;
        if (userRepository.count() > 0) {
            group = groupService.getByName(Role.USER.toString());
            addedUser.addRole(group);
        } else {
            group = groupService.getByName(Role.ADMINISTRATOR.toString());
            addedUser.addRole(group);
        }
        return new UserDto(userRepository.save(addedUser));
    }

    public PasswordChangedResponse changePass(User currentUser, NewPasswordDto newPassword) {
        if (passwordEncoder.matches(newPassword.getNewPassword(), currentUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, SAME_PASSWORD_ERRORMSG);
        }
        passwordBreachValidator.validate(newPassword.getNewPassword());
        currentUser.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
        userRepository.save(currentUser);
        return new PasswordChangedResponse(currentUser.getUsername(),
                PASSWORD_CHANGED_MSG);
    }
}
