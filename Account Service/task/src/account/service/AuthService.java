package account.service;

import account.model.DTO.NewUserDTO;
import account.model.User;

public interface AuthService {
    User signup(NewUserDTO newUser);
}
