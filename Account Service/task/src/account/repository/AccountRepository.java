package account.repository;

import account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("""
            SELECT a FROM Account a WHERE a.email = ?1 AND a.period = ?2
            """)
    Optional<Account> getAccountByEmailAndPeriod(String email, Date period);

    @Query("""
    SELECT a FROM Account a WHERE a.email = ?1
    ORDER BY a.period DESC
    """)
    List<Account> getAccountsByEmail(String email);
}
