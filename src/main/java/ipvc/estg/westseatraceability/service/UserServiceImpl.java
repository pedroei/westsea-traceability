package ipvc.estg.westseatraceability.service;

import ipvc.estg.westseatraceability.model.Role;
import ipvc.estg.westseatraceability.model.User;
import ipvc.estg.westseatraceability.repository.RoleRepository;
import ipvc.estg.westseatraceability.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not found!");
            throw new UsernameNotFoundException("User not found!");
        } else {
            log.info("User with username {} was found", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {} to the DB", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the DB", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(UUID userUuid, String roleName) {
        User user = userRepository.findByUuid(userUuid);
        Role role = roleRepository.findByName(roleName);

        log.info("Adding role {} to the User {}", role.getName(), user.getName());
        user.getRoles().add(role);
    }

    @Override
    public User getUser(UUID uuid) {
        log.info("Fetching user with the uuid: {}", uuid);
        return userRepository.findByUuid(uuid);
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user with the username: {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }
}
