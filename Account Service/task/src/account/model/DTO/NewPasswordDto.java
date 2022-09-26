package account.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class NewPasswordDto {

    @JsonProperty("new_password")
    @Size(min = 12, message = "Password length must be 12 chars minimum!")
    @NotBlank
    private String newPassword;
}
