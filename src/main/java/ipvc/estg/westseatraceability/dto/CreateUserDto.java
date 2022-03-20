package ipvc.estg.westseatraceability.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ipvc.estg.westseatraceability.enumeration.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class CreateUserDto {

    @Schema(description = "name of the new user", example = "Jose Almeida")
    private String name;

    @Schema(description = "username of the new user", example = "jalmeida92")
    private String username;

    @Schema(description = "password of the new user", example = "jose123")
    private String password;

    @Builder.Default
    private Set<RoleEnum> roles = new HashSet<>();
}
