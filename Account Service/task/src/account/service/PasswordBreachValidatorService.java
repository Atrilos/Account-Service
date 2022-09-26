package account.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

import static account.configuration.messages.AuthMessages.PASSWORD_HACKED_ERRORMSG;

@Service
public class PasswordBreachValidatorService {

    private final Set<String> breachedPasswords = Set.of(
            "PasswordForJanuary",
            "PasswordForFebruary",
            "PasswordForMarch",
            "PasswordForApril",
            "PasswordForMay",
            "PasswordForJune",
            "PasswordForJuly",
            "PasswordForAugust",
            "PasswordForSeptember",
            "PasswordForOctober",
            "PasswordForNovember",
            "PasswordForDecember");

    public void validate(String password) {
        if (breachedPasswords.contains(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PASSWORD_HACKED_ERRORMSG);
        }
    }
}
