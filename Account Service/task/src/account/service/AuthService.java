package account.service;

import account.exception.UserExistException;
import account.model.DTO.*;
import account.model.Group;
import account.model.User;
import account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
            group = groupService.getByName("ROLE_USER");
            addedUser.addRole(group);
        } else {
            group = groupService.getByName("ROLE_ADMINISTRATOR");
            addedUser.addRole(group);
        }
        return new UserDto(userRepository.save(addedUser));
    }

    public PasswordChangedResponse changePass(User currentUser, NewPasswordDto newPassword) {
        if (passwordEncoder.matches(newPassword.getNewPassword(), currentUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The passwords must be different!");
        }
        passwordBreachValidator.validate(newPassword.getNewPassword());
        currentUser.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
        userRepository.save(currentUser);
        return new PasswordChangedResponse(currentUser.getUsername(),
                "The password has been updated successfully");
    }
}
