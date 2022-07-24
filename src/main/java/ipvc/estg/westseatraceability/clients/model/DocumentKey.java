package ipvc.estg.westseatraceability.clients.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DocumentKey {

    @Schema(description = "key that represents a document on the cloud", example = "key132")
    private String documentKey;

    @Schema(description = "fingerprint of an uploaded document", example = "238168267g1s671gs1f2t3gj1")
    private String fileFingerPrint;
}
