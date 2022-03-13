package ipvc.estg.westseatraceability.dto;

import ipvc.estg.westseatraceability.model.RoleEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {
    private String name;
    private String username;
    private List<RoleEnum> roles = new ArrayList<>();
}
