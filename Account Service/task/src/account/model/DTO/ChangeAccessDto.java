package account.model.DTO;

import account.model.constant.AccessOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeAccessDto {
    @Email
    @NotNull
    private String user;
    @Enumerated(EnumType.STRING)
    private AccessOperation operation;
}
