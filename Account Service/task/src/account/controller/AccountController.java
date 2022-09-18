package account.controller;

import account.model.DTO.AddPaymentDTO;
import account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/acct")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/payments")
    @Transactional(rollbackFor = ParseException.class)
    public ResponseEntity<?> addPayments(@RequestBody List<@Valid AddPaymentDTO> payments) throws ParseException {
        return ResponseEntity.ok(accountService.addPayments(payments));
    }

    @PutMapping("/payments")
    public ResponseEntity<?> updatePayments(@RequestBody @Valid AddPaymentDTO payment) throws ParseException {
        return ResponseEntity.ok(accountService.updatePayment(payment));
    }
}
