package account.controller;

import account.model.SecurityEvent;
import account.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/security")
@RequiredArgsConstructor
public class AuditController {
    private final AuditService auditService;

    @GetMapping("/events")
    public ResponseEntity<?> getEvents() {
        Collection<SecurityEvent> securityEvents = auditService.getAll();
        return ResponseEntity.ok(securityEvents);
    }
}
