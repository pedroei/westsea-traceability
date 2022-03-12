package ipvc.estg.westseatraceability.service;

import ipvc.estg.westseatraceability.model.Role;
import ipvc.estg.westseatraceability.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(UUID userUuid, String roleName);
    User getUser(UUID uuid);
    User getUser(String username);
    List<User> getUsers(); //TODO: paginate
}
