package ipvc.estg.westseatraceability.clients.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductLot {
    //FIXME: Swagger

    private String docType;

    @JsonProperty("ID")
    private String id;

    private String referenceNumber;

    private Boolean isSerialNumber;

    private String designation;

    private String productType;

    private Float initialQuantity;

    private Float availableQuantity;

    private List<String> documentKeys;
}
