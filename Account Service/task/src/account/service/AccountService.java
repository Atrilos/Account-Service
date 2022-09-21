package account.service;

import account.exception.PaymentNotFoundException;
import account.model.Account;
import account.model.DTO.AddPaymentDto;
import account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static account.configuration.messages.AccountMessages.*;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;
    private final SimpleDateFormat formatter;

    public Map<String, String> addPayments(List<AddPaymentDto> paymentDTOList) throws ParseException {
        for (AddPaymentDto addPaymentDTO : paymentDTOList) {
            userService.loadUserByUsername(addPaymentDTO.getEmail());
            Account payment = parseAccount(addPaymentDTO);
            accountRepository.save(payment);
        }
        return Map.of("status", SUCCESSFUL_ADDITION_MSG);
    }

    public Map<String, String> updatePayment(AddPaymentDto paymentDTO) throws ParseException {
        userService.loadUserByUsername(paymentDTO.getEmail());
        Account payment = parseAccount(paymentDTO);
        Optional<Account> foundAccount = getAccountByEmailAndPeriod(
                payment.getEmail(), formatter.parse(paymentDTO.getPeriod())
        );
        payment.setId(foundAccount
                .orElseThrow(() -> new PaymentNotFoundException(PAYMENT_NOT_FOUND_ERRORMSG))
                .getId());
        accountRepository.save(payment);
        return Map.of("status", SUCCESSFUL_MODIFY_MSG);
    }

    public Optional<Account> getAccountByEmailAndPeriod(String email, Date period) {
        return accountRepository.getAccountByEmailAndPeriod(email, period);
    }

    public List<Account> getAccountsByEmail(String email) {
        return accountRepository.getAccountsByEmail(email);
    }

    private Account parseAccount(AddPaymentDto addPaymentDTO) throws ParseException {
        Account payment = new Account();
        payment.setEmail(addPaymentDTO.getEmail());
        payment.setPeriod(formatter.parse(addPaymentDTO.getPeriod()));
        payment.setSalary(addPaymentDTO.getSalary());
        return payment;
    }
}
