package ipvc.estg.westseatraceability.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CreateProductLotDto {
    //FIXME: Swagger

    private String referenceNumber;
    private Boolean isSerialNumber;
    private String designation;
    private String productType;
    private Float initialAmount;
    private List<String> documentKeys;
}
