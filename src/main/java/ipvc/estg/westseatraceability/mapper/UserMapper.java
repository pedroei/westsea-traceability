package ipvc.estg.westseatraceability.mapper;

import ipvc.estg.westseatraceability.dto.CreateUserDto;
import ipvc.estg.westseatraceability.dto.UserDto;
import ipvc.estg.westseatraceability.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        UserDto dto = new UserDto();
        dto.setName(user.getName());
        dto.setUsername(user.getUsername());
        dto.setRoles(user.getRoles());

        return dto;
    }

    public List<UserDto> userListToDto(List<User> users) {
        return users.stream().map(this::userToDto).collect(Collectors.toList());
    }
}
