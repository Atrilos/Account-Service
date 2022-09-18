package account.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class AddPaymentDTO {
    @JsonProperty("employee")
    @Email(regexp = ".*@acme\\.com")
    @NotBlank
    private String email;
    @NotNull
    @Pattern(regexp = "((0\\d)|(1[012]))-2\\d{3}", message = "Wrong date!")
    private String period;
    @Min(value = 0L, message = "Salary must be non negative!")
    @NotNull
    private Long salary;
}
