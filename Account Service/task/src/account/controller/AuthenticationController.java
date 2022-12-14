package account.controller;

import account.model.DTO.NewPasswordDto;
import account.model.DTO.NewUserDto;
import account.model.DTO.UserDto;
import account.model.User;
import account.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@Valid @RequestBody NewUserDto newUser) {
        return ResponseEntity.ok(authService.signup(newUser));
    }

    @PostMapping("/changepass")
    public ResponseEntity<Map<String, String>> changepass(@AuthenticationPrincipal User currentUser,
                                                          @Valid @RequestBody NewPasswordDto newPassword) {
        return ResponseEntity.ok(authService.changePass(currentUser, newPassword));
    }
}
