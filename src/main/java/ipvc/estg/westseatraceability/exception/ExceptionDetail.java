package ipvc.estg.westseatraceability.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExceptionDetail {

    private String reason;
    private String message;
}
