package ipvc.estg.westseatraceability.repository;

import ipvc.estg.westseatraceability.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
