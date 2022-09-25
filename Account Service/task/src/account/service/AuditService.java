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
    private final HttpServletRequest httpServletRequest;

    public void addEvent(Action action, String object) {
        String subject = getCurrentUser();
        SecurityEvent securityEvent = SecurityEvent.builder()
                .action(action)
                .subject(subject)
                .object(object)
                .path(httpServletRequest.getRequestURI())
                .build();
        auditRepository.save(securityEvent);
    }

    private String getCurrentUser() {
        Principal principal = httpServletRequest.getUserPrincipal();
        return principal == null ? "Anonymous" : principal.getName();
    }

    public Collection<SecurityEvent> getAll() {
        return auditRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}
