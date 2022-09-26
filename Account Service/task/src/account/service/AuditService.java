package account.service;

import account.model.SecurityEvent;
import account.model.constant.Action;
import account.repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuditService {
    private final AuditRepository auditRepository;
    private final HttpServletRequest request;

    public void addEvent(Action action, String object) {
        String subject = getCurrentUser();
        SecurityEvent securityEvent = getSecurityEvent(action, subject, object);
        auditRepository.save(securityEvent);
    }

    public void addEvent(Action action, String subject, String object) {
        SecurityEvent securityEvent = getSecurityEvent(action, subject, object);
        auditRepository.save(securityEvent);
    }

    private SecurityEvent getSecurityEvent(Action action, String subject, String object) {
        return SecurityEvent.builder()
                .action(action)
                .subject(subject)
                .object(object)
                .path(request.getRequestURI())
                .build();
    }

    private String getCurrentUser() {
        Principal principal = request.getUserPrincipal();
        return principal == null ? "Anonymous" : principal.getName();
    }

    public Collection<SecurityEvent> getAll() {
        return auditRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}
