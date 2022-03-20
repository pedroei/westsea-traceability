package ipvc.estg.westseatraceability.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductDesignationDto {

    @Schema(description = "id of a product designation", example = "789")
    private String id;

    @Schema(description = "designation for a product", example = "chapa de aço")
    private String designation;
}
