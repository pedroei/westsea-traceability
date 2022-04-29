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

    Map<String, Float> inputProductLots;
    private String activityID;
    private String designation;
    private String userId;
    private CreateProductLotDto outputProductLot;
}
