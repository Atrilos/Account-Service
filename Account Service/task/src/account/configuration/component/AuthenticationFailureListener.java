package account.configuration.component;

import account.model.constant.Action;
import account.service.AuditService;
import account.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class AuthenticationFailureListener
        implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final UserService userService;
    private final AuditService auditService;
    private HttpServletRequest request;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String email = (String) event.getAuthentication().getPrincipal();
        String path = request.getRequestURI();
        auditService.addEvent(Action.LOGIN_FAILED, path);
        userService.registerFailedLoginAttempt(email);
    }
}
