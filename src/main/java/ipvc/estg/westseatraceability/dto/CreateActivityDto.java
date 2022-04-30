package ipvc.estg.westseatraceability.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class CreateActivityDto {
    //FIXME: Swagger

    private String designation;
    Map<String, Float> inputProductLots;
    private CreateProductLotDto outputProductLot;
}
