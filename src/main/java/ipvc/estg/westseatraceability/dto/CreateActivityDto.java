package ipvc.estg.westseatraceability.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
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

    @Schema(description = "unique identifier of the product or lot, it can be the serial number of a unique product or a lot number", example = "346512657461286741")
    private String outputReferenceNumber;

    @Schema(description = "when this flag is true, the reference number represents a serial number, otherwise it represents a lot number", example = "false")
    private Boolean outputIsSerialNumber;

    @Schema(description = "designation of a product lot", example = "Steel bar")
    private String outputDesignation;

    @Schema(description = "type of a product lot (Block, Component, Part)", example = "Part")
    private String outputProductType;

    @Schema(description = "the initial amount of a productLot, if isSerialNumber==true, this will always be true", example = "500")
    private Float outputInitialAmount;

    @Schema(description = "list of documents")
    private List<MultipartFile> outputDocuments;
}
