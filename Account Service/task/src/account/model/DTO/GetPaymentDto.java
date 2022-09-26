package account.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetPaymentDto {
    private String name;
    private String lastname;
    private String period;
    private String salary;
}
