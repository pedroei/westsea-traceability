package ipvc.estg.westseatraceability.service;

import ipvc.estg.westseatraceability.dto.CreateUserDto;
import ipvc.estg.westseatraceability.dto.UserDto;
import ipvc.estg.westseatraceability.exception.NotFoundException;
import ipvc.estg.westseatraceability.mapper.UserMapper;
import ipvc.estg.westseatraceability.enumeration.RoleEnum;
import ipvc.estg.westseatraceability.model.User;
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

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        log.info("User with username {} was found", username);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.name())));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public UserDto saveUser(CreateUserDto dto) {
        log.info("Saving new user {} to the DB", dto.getName());

        User user = userMapper.createDtoToUser(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User newUser = userRepository.save(user);

        return userMapper.userToDto(newUser);
    }

    public void addRoleToUser(String id, RoleEnum role) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        log.info("Adding role {} to the User {}", role, user.getName());

        user.getRoles().add(role);
        userRepository.save(user);
    }

    public User getUserById(String id) {
        log.info("Fetching user with the uuid: {}", id);
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User getUserByUsername(String username) {
        log.info("Fetching user with the username: {}", username);
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User with username: " + username + ", was not found"));
    }

    public List<UserDto> getUsers() {
        log.info("Fetching all users");
        List<User> users = userRepository.findAll();

        return userMapper.userListToDto(users);
    }
}
