package account.configuration.component;

import account.model.User;
import account.model.constant.Action;
import account.service.AuditService;
import account.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class AuthenticationEvents {

    private final UserService userService;
    private final AuditService auditService;
    private final HttpServletRequest request;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        User user = (User) event.getAuthentication().getPrincipal();
        userService.resetFailedLoginAttempts(user);
    }

    @EventListener
    public void onFailure(AuthenticationFailureBadCredentialsEvent event) {
        String email = (String) event.getAuthentication().getPrincipal();
        String path = request.getRequestURI();
        auditService.addEvent(Action.LOGIN_FAILED, email, path);
        userService.registerFailedLoginAttempt(email);
    }
}
