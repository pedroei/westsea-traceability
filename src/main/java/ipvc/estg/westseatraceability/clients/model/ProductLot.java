package ipvc.estg.westseatraceability.clients.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductLot {

    @Schema(description = "doc type used to represent this type on the blockchain", example = "productLot")
    private String docType;

    @Schema(description = "id of this productLot", example = "1234")
    @JsonProperty("ID")
    private String id;

    @Schema(description = "unique identifier of the product or lot, it can be the serial number of a unique product or a lot number", example = "346512657461286741")
    private String referenceNumber;

    @Schema(description = "when this flag is true, the reference number represents a serial number, otherwise it represents a lot number", example = "false")
    private Boolean isSerialNumber;

    @Schema(description = "designation of a product lot", example = "Steel bar")
    private String designation;

    @Schema(description = "type of a product lot (Block, Component, Part)", example = "Part")
    private String productType;

    @Schema(description = "the initial amount of a productLot, if isSerialNumber==true, this will always be true", example = "500")
    private Float initialQuantity;

    @Schema(description = "the current amount available of a productLot", example = "256")
    private Float availableQuantity;

    @Schema(description = "list of keys that represent different documents", example = "[\"key1\", \"key2\", \"key33\"]")
    private List<DocumentKey> documentKeys;
}
