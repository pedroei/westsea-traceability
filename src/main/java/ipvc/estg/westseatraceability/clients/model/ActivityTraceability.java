package ipvc.estg.westseatraceability.clients.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class ActivityTraceability {

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

    @Schema(description = "list of productLots used as inputs for this activity")
    private List<ProductTraceability> inputProductLots;
}
