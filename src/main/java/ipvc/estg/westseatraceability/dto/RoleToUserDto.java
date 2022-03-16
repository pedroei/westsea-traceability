package ipvc.estg.westseatraceability.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ipvc.estg.westseatraceability.enumeration.RoleEnum;
import lombok.Data;

@Data
public class RoleToUserDto {

    @Schema(description = "id of the user to receive the new role", example = "123456789")
    private String userId;

    @Schema(description = "new role to be added", example = "ROLE_CLIENT")
    private RoleEnum role;
}
