package account.repository;

import account.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Query("UPDATE User u SET u.failedLoginAttempts = 0 WHERE u.id = ?1")
    void resetFailedLoginAttempts(Long id);

    @Query("""
           SELECT u FROM User u
           WHERE UPPER(u.username) = UPPER(?1)
           """)
    Optional<User> findByEmail(String email);
}
