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
public class UpdateUserDto {

    @Schema(description = "name of the new user", example = "João Barros")
    private String name;

    @Builder.Default
    private Set<RoleEnum> roles = new HashSet<>();
}
