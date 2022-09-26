package account.model;

import account.model.constant.Action;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "security_event")
public class SecurityEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private Action action;
    private String subject;
    private String object;
    private String path;
}