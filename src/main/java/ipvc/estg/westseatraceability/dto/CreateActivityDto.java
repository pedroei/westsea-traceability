package ipvc.estg.westseatraceability.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class CreateActivityDto {

    @Schema(description = "map with the input product lots, the string represents the id of the reference product lot and the float is quantity is of that product lot",
            example = "{\"12345\": \"10\"}")
    Map<String, Float> inputProductLots;
    @Schema(description = "designation of an activity", example = "Cut")
    private String designation;
    @Schema(description = "output resulting from the input product lots", implementation = CreateProductLotDto.class)
    private CreateProductLotDto outputProductLot;
}
