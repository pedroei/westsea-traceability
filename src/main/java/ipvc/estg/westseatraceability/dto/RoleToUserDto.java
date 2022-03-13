package ipvc.estg.westseatraceability.dto;

import ipvc.estg.westseatraceability.model.RoleEnum;
import lombok.Data;

@Data
public class RoleToUserDto {
    private String userId;
    private RoleEnum role;
}
