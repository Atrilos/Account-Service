package account.model.DTO;

import account.model.constant.AccessOperation;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ChangeAccessDto {
    @Email
    @NotBlank
    private String user;
    @NotNull
    @Enumerated(EnumType.STRING)
    private AccessOperation operation;
}
