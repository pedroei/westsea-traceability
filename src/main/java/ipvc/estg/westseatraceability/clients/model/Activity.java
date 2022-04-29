package ipvc.estg.westseatraceability.clients.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;

@Getter
public class Activity {
    //FIXME: Swagger

    private String docType;

    @JsonProperty("ID")
    private String id;

    private String designation;

    private String userId;

    @JsonProperty("dateTime")
    private String date;

    private Map<String, Float> inputProductLots;

    private ProductLot outputProductLot;
}
