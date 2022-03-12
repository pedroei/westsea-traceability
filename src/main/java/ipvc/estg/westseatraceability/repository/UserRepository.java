package ipvc.estg.westseatraceability.repository;

import ipvc.estg.westseatraceability.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUuid(UUID uuid);
    User findByUsername(String username);
}
