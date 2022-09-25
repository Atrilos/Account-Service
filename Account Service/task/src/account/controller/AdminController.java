package account.controller;

import account.model.DTO.ChangeAccessDto;
import account.model.DTO.ChangeRoleDto;
import account.model.DTO.UserDto;
import account.model.User;
import account.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Validated
public class AdminController {
    private final AdminService adminService;

    @PutMapping("/user/role")
    public ResponseEntity<UserDto> changeRole(@RequestBody @Valid ChangeRoleDto changeRoleDTO) {
        return ResponseEntity.ok(adminService.changeRole(changeRoleDTO));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @DeleteMapping("/user/{email}")
    public ResponseEntity<Map<String, String>> removeUser(@AuthenticationPrincipal User admin,
                                                          @PathVariable
                                                          @Email
                                                          @NotBlank String email) {
        return ResponseEntity.ok(adminService.removeUser(admin, email));
    }

    @PutMapping("/user/access")
    public ResponseEntity<Map<String, String>> access(@Valid ChangeAccessDto changeAccessDto) {
        return ResponseEntity.ok(adminService.changeAccess(changeAccessDto));
    }
}
