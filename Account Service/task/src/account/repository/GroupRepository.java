package account.repository;

import account.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("select g from Group g where upper(g.name) = upper(?1)")
    Optional<Group> findByName(String name);
}