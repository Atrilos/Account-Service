package account.model;

import account.model.constant.Action;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Builder.Default
    private LocalDate date = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private Action action;
    private String subject;
    private String object;
    private String path;
}