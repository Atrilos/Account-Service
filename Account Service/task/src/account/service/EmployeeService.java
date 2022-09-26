package account.service;

import account.model.Account;
import account.model.DTO.GetPaymentDto;
import account.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final AccountService accountService;
    private final SimpleDateFormat formatter;
    @Qualifier("fullDateFormat")
    private final SimpleDateFormat fullMonthNameFormatter;

    public GetPaymentDto getPaymentByPeriod(User user, String period) throws ParseException {
        Optional<Account> foundAccount = accountService.getAccountByEmailAndPeriod(
                user.getUsername(), formatter.parse(period));
        if (foundAccount.isEmpty())
            return null;

        Account account = foundAccount.get();
        return createGetPaymentDTO(user, account);
    }

    public List<GetPaymentDto> getPayments(User user) {
        List<Account> foundAccountList = accountService.getAccountsByEmail(user.getUsername());
        List<GetPaymentDto> result = new ArrayList<>();
        for (Account account : foundAccountList) {
            GetPaymentDto entry = createGetPaymentDTO(user, account);
            result.add(entry);
        }
        return result;
    }

    private GetPaymentDto createGetPaymentDTO(User user, Account account) {
        GetPaymentDto result = new GetPaymentDto();
        result.setName(user.getName());
        result.setLastname(user.getLastname());
        result.setPeriod(fullMonthNameFormatter.format(account.getPeriod()));
        result.setSalary(parseSalary(account.getSalary()));
        return result;
    }

    private String parseSalary(Long salary) {
        long dollars = salary / 100;
        long cents = salary % 100;
        return "%d dollar(s) %d cent(s)".formatted(dollars, cents);
    }
}
