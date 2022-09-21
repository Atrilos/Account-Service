package account.service;

import account.exception.UserNotFoundException;
import account.model.DTO.UserDto;
import account.model.User;
import account.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static account.configuration.messages.UserMessages.USER_NOT_FOUND_ERRORMSG;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(USER_NOT_FOUND_ERRORMSG);
        }
    }

    public User loadUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_ERRORMSG));
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
}
