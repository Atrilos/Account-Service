package account.service;

import account.exception.UserExistException;
import account.model.DTO.NewUserDTO;
import account.model.DTO.UserCreatedRequest;
import account.model.User;
import account.model.constant.Role;
import account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserCreatedRequest signup(NewUserDTO newUser) {
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new UserExistException("User exist!");
        }
        User addedUser = new User();
        addedUser.setName(newUser.getName());
        addedUser.setLastname(newUser.getLastname());
        addedUser.setEmail(newUser.getEmail());
        addedUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        addedUser.grantAuthority(Role.ROLE_USER);
        return new UserCreatedRequest(userRepository.save(addedUser));
    }
}
