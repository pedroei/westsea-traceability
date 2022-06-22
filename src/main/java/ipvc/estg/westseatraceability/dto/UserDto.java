package ipvc.estg.westseatraceability.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ipvc.estg.westseatraceability.enumeration.RoleEnum;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class UserDto {

    @Schema(description = "id of the new user", example = "12345abcde")
    private String id;

    @Schema(description = "name of the new user", example = "Jose Almeida")
    private String name;

    @Schema(description = "username of the new user", example = "jalmeida92")
    private String username;

    @Builder.Default
    private Set<RoleEnum> roles = new HashSet<>();
}
