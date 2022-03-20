package ipvc.estg.westseatraceability.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ActivityDesignationDto {

    @Schema(description = "id of an activity designation", example = "123")
    private String id;

    @Schema(description = "designation for an activity", example = "soldagem")
    private String designation;
}
