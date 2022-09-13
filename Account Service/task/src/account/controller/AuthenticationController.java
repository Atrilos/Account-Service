package account.controller;

import account.model.DTO.NewUserDTO;
import account.model.DTO.UserCreatedRequest;
import account.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserCreatedRequest> signup(@Valid @RequestBody NewUserDTO newUser) {
        return ResponseEntity.ok(authService.signup(newUser));
    }
}
