package account.controller;

import account.model.DTO.GetPaymentDTO;
import account.model.User;
import account.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.text.ParseException;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/empl")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping(value = "/payment", params = "period")
    public ResponseEntity<?> paymentByPeriod(@AuthenticationPrincipal User user,
                                                 @RequestParam @Pattern(regexp = "((0\\d)|(1[012]))-2\\d{3}") String period) throws ParseException {
        GetPaymentDTO result = employeeService.getPaymentByPeriod(user, period);
        return ResponseEntity.ok(result == null ? "{}" : result);
    }

    @GetMapping("/payment")
    public ResponseEntity<List<GetPaymentDTO>> payments(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(employeeService.getPayments(user));
    }
}
