package ipvc.estg.westseatraceability.mapper;

import ipvc.estg.westseatraceability.dto.CreateUserDto;
import ipvc.estg.westseatraceability.dto.UpdateUserDto;
import ipvc.estg.westseatraceability.dto.UserDto;
import ipvc.estg.westseatraceability.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMapper {

    public User createDtoToUser(CreateUserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRoles(dto.getRoles());

        return user;
    }

    public UserDto userToDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }

    public List<UserDto> userListToDto(List<User> users) {
        return users.stream().map(this::userToDto).toList();
    }

    public User updateDtoToUser(User user, UpdateUserDto dto) {
        user.setName(dto.getName() != null && !dto.getName().equals("") ? dto.getName() : user.getName());
        user.setRoles(dto.getRoles() != null && !dto.getRoles().isEmpty() ? dto.getRoles() : user.getRoles());

        return user;
    }
}
