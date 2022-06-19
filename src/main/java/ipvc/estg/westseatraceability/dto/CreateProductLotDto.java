package ipvc.estg.westseatraceability.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CreateProductLotDto {

    @Schema(description = "unique identifier of the product or lot, it can be the serial number of a unique product or a lot number", example = "346512657461286741")
    private String referenceNumber;

    @Schema(description = "when this flag is true, the reference number represents a serial number, otherwise it represents a lot number", example = "false")
    private Boolean isSerialNumber;

    @Schema(description = "designation of a product lot", example = "Steel bar")
    private String designation;

    @Schema(description = "type of a product lot (Block, Component, Part)", example = "Part")
    private String productType;

    @Schema(description = "the initial amount of a productLot, if isSerialNumber==true, this will always be true", example = "500")
    private Float initialAmount;

    @Schema(description = "list of documents")
    private List<MultipartFile> documents;
}
