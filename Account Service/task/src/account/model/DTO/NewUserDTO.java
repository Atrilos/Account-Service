package account.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class NewUserDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String lastname;
    @Email(regexp = ".*@acme\\.com")
    private String email;
    @NotBlank
    private String password;
}
