package ipvc.estg.westseatraceability.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateActivityDesignationDto {

    @Schema(description = "designation for an activity", example = "soldagem")
    private String designation;
}
