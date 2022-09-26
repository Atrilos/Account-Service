package account.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Setter
@Getter
@ToString
@Entity
@Table(indexes = {@Index(columnList = "employee", name = "Index_acc_employee")},
        uniqueConstraints = {@UniqueConstraint(name = "UC_employee_period", columnNames = {"employee", "period"})})
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("employee")
    @Column(name = "employee")
    private String email;
    @Temporal(TemporalType.DATE)
    private Date period;
    private Long salary;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return id != null && Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
