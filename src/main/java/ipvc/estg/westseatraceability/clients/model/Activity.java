package ipvc.estg.westseatraceability.clients.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Map;

@Getter
public class Activity {

    @Schema(description = "doc type used to represent this type on the blockchain", example = "activity")
    private String docType;

    @Schema(description = "id of this activity", example = "123")
    @JsonProperty("ID")
    private String id;

    @Schema(description = "designation of an activity", example = "Cut")
    private String designation;

    @Schema(description = "id of the user that performed the activity", example = "eb7c0e61-706d-4ab6-9756-f777b1f902c9")
    private String userId;

    @Schema(description = "date and time when the activity was performed", example = "2022-05-01T19:07:51.216946")
    @JsonProperty("dateTime")
    private String date;

    @Schema(description = "map with the input product lots, the string represents the id of the reference product lot and the float is quantity is of that product lot",
            example = "{\"12345\": \"10\"}")
    private Map<String, Float> inputProductLots;

    @Schema(description = "output resulting from the input product lots", implementation = ProductLot.class)
    private ProductLot outputProductLot;
}
