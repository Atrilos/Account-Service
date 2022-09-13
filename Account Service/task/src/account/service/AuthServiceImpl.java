package account.service;

import account.model.DTO.NewUserDTO;
import account.model.User;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class AuthServiceImpl implements AuthService {

    private final ConcurrentMap<String, User> userRepository = new ConcurrentHashMap<>();

    @Override
    public User signup(NewUserDTO newUser) {
        User addedUser = new User(
                newUser.getName(),
                newUser.getLastname(),
                newUser.getEmail()
        );
        userRepository.put(newUser.getEmail(), addedUser);
        return addedUser;
    }
}
