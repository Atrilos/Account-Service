package account.service;

import account.model.DTO.NewUserDTO;
import account.model.DTO.UserCreatedRequest;

public interface AuthService {
    UserCreatedRequest signup(NewUserDTO newUser);
}
