package account.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class NewUserDto {
    @NotBlank
    private String name;
    @NotBlank
    private String lastname;
    @Email(regexp = ".*@acme\\.com")
    @NotBlank
    private String email;
    @Size(min = 12, message = "Password length must be 12 chars minimum!")
    @NotBlank
    private String password;
}
