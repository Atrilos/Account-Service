package account.service;

import account.exception.UserExistException;
import account.model.DTO.NewPasswordDTO;
import account.model.DTO.NewUserDTO;
import account.model.DTO.PasswordChangedResponse;
import account.model.DTO.UserCreatedResponse;
import account.model.User;
import account.model.constant.Role;
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
    private final BCryptPasswordEncoder passwordEncoder;
    private final PasswordBreachValidatorService passwordBreachValidator;

    public UserCreatedResponse signup(NewUserDTO newUser) {
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new UserExistException("User exist!");
        }
        passwordBreachValidator.validate(newUser.getPassword());
        User addedUser = new User();
        addedUser.setName(newUser.getName());
        addedUser.setLastname(newUser.getLastname());
        addedUser.setEmail(newUser.getEmail().toLowerCase());
        addedUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        addedUser.grantAuthority(Role.ROLE_USER);
        return new UserCreatedResponse(userRepository.save(addedUser));
    }

    public PasswordChangedResponse changePass(User currentUser, NewPasswordDTO newPassword) {
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
